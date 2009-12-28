package com.hopstepjump.backbone.printer;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;

interface ConstituentFilter
{
	ArrayList<DeltaPair> sort(Set<DeltaPair> pairs);
}

public class BackbonePrinter
{
	private DEStratum perspective;
	private DEObject object;

	public BackbonePrinter(DEStratum perspective, DEObject object)
	{
		this.perspective = perspective;
		this.object = object;
	}
	
	public String makePrintString(String indent)
	{
		StringBuilder b = new StringBuilder();

		// if this is an package show everything
		if (object.asStratum() != null)
		{
			DEStratum pkg = object.asStratum();
			b.append(indent + "stratum " + name(pkg));
			if (pkg.isRelaxed())
				b.append(" is-relaxed");
			if (pkg.isDestructive())
				b.append(" is-destructive");
			int dSize = pkg.getRawDependsOn().size();
			if (dSize != 0)
			{
				b.append("\n\tdepends-on ");
				int lp = 0;
				for (DEStratum d : pkg.getRawDependsOn())
					b.append(name(d) + (++lp != dSize ? ", " : ""));
			}
			
			// list the children
			int nSize = pkg.getDirectlyNestedPackages().size(); 
			if (nSize != 0)
			{
				b.append("\n\tnests ");
				int lp = 0;
				for (DEStratum n : pkg.getDirectlyNestedPackages())
					b.append(name(n) + (++lp != nSize ? ", " : ""));
			}
			b.append("\n" + indent + "{\n");
			
			// elements
			for (DEElement elem : pkg.getChildElements())
				if (elem.asInterface() != null)
					makeElementString(indent, b, elem);
			for (DEElement elem : pkg.getChildElements())
				if (elem.asComponent() != null)
					makeElementString(indent, b, elem);
			b.append(indent + "}\n\n");
		}
		else
	  // if this is a component or interface, just show that...
		if (object.asElement() != null)
		{
			makeElementString(indent, b, object.asElement());
		}
		
		return b.toString().replace("\t", "    ");
	}

	private void makeElementString(String indent, StringBuilder b, DEElement elem)
	{
		if (elem.asInterface() != null)
		{
			DEInterface iface = elem.asInterface();
			b.append(indent + "\tinterface " + name(iface, true));
		}
		else
		if (elem.asComponent() != null)
		{
			DEComponent c = elem.asComponent();
			b.append(indent + "\tcomponent " + name(c) +
					(isRawFactory(c) ? " is-factory" : "") +
					(isRawPlaceholder(c) ? " is-placeholder" : "") +
					(c.getComponentKind() == ComponentKindEnum.PRIMITIVE ? " is-primitive" :
					  (c.getComponentKind() == ComponentKindEnum.STEREOTYPE ? " is-stereotype" : "")));
		}

		String implClass = elem.getImplementationClass(null);
		String impl = implClass != null ? " implementation-class " + implClass : "";
		b.append(impl);
		if (elem.asComponent() != null && isRawBean(elem.asComponent()))
			b.append(" is-bean");
		b.append("\n" + makeRString(indent, elem) + indent + "\t{\n");
		
		for (DeltaPair pair : elem.getDeltas(ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE).getReplaceObjects())
		{
			if (showStereotype(pair))
				b.append(indent + "\t\t" + makeAppliedStereotypeString(pair.getConstituent().asAppliedStereotype()) + "\n");
		}
		appendAll(b, indent, "operations", elem, ConstituentTypeEnum.DELTA_OPERATION, null);
		appendAll(b, indent, "attributes", elem, ConstituentTypeEnum.DELTA_ATTRIBUTE, new Comparator<DeltaPair>()
		{
			public int compare(DeltaPair o1, DeltaPair o2)
			{
				DEAttribute a1 = o1.getConstituent().asAttribute();
				DEAttribute a2 = o2.getConstituent().asAttribute();
				if (a1.isReadOnly() != a2.isReadOnly() || a1.isWriteOnly() != a2.isWriteOnly())
				{
					if (a1.isReadOnly())
						return -1;
					if (a2.isReadOnly())
						return 1;
					if (a1.isWriteOnly())
						return -1;
					if (a2.isWriteOnly())
						return 1;
				}
				return a1.getName().compareTo(a2.getName());
			}	
		});
		appendAll(b, indent, "ports", elem, ConstituentTypeEnum.DELTA_PORT, null);
		appendAll(b, indent, "parts", elem, ConstituentTypeEnum.DELTA_PART, null);
		appendAll(b, indent, "connectors", elem, ConstituentTypeEnum.DELTA_CONNECTOR, null);
		appendAll(b, indent, "port-links", elem, ConstituentTypeEnum.DELTA_PORT_LINK, null);
		
		b.append(indent + "\t}\n\n");
	}

  private void appendAll(StringBuilder b, String indent, String name, DEElement elem, ConstituentTypeEnum type, Comparator<DeltaPair> sorter)
	{
		appendDeletes(b, indent, name, elem, type);
		append(b, indent, name, elem, type, false, sorter);
		append(b, indent, name, elem, type, true, sorter);
	}

	private String makeRString(String indent, DEElement c)
	{
		String ret = "";
		int size = c.getRawResembles().size();
		if (size > 0)
		{
			ret += " resembles ";
			int lp = 0;
			for (DEElement e : c.getRawResembles())
				ret += name(e, true) + (++lp != size ? ", " : "");
		}
		size = c.getSubstitutes().size();
		if (size > 0)
		{
			ret += " replaces ";
			int lp = 0;
			for (DEElement e : c.getSubstitutes())
				ret += name(e, true) + (++lp != size ? ", " : "");
		}
		if (ret.length() == 0)
			return "";
		return indent + "\t\t" + ret + "\n";
	}

	private void appendDeletes(StringBuilder b, String indent, String preamble, DEElement elem, ConstituentTypeEnum type)
	{
		IDeltas deltas = elem.getDeltas(type);
		Set<String> deletes = deltas.getDeleteObjects();
		if (!deletes.isEmpty())
		{
			b.append(indent + "\t\tdelete-" + preamble + ":\n");
			for (String uuid : deletes)
				b.append(indent + "\t\t\t" + name(GlobalDeltaEngine.engine.locateObjectForStereotype(uuid)) + ";\n");
		}
	}

	private boolean append(StringBuilder b, String indent, String preamble, DEElement element, ConstituentTypeEnum type, boolean replace, Comparator<DeltaPair> sorter)
	{
		IDeltas deltas = element.getDeltas(type);
		Set<DeltaPair> pairs = replace ? deltas.getReplaceObjects() : deltas.getAddObjects();
		if (!pairs.isEmpty())
		{
			b.append(indent + "\t\t" + (replace ? "replace-" : "") + preamble + ":\n");
			List<DeltaPair> sorted = new ArrayList<DeltaPair>(pairs);
			if (sorter != null)
				Collections.sort(sorted, sorter);
			for (DeltaPair pair : sorted)
			{
				DEObject obj = pair.getConstituent();
				if (obj instanceof DEOperation)
					appendOperationString(indent, b, pair);
				else
				if (obj instanceof DEAttribute)
					appendAttributeString(indent, b, element, pair);
				else
				if (obj instanceof DEPort)
					appendPortString(indent, b, pair);
				else
				if (obj instanceof DEConnector)
					appendConnectorString(indent, b, pair);
				else
				if (obj instanceof DEPart)
					appendPartString(indent, b, element, pair);
			}
			return true;
		}		
		return false;
	}

	private void appendOperationString(String indent, StringBuilder b, DeltaPair pair)
	{
		b.append(indent + "\t\t\t" + makeReplace(pair) + makeAppliedStereotypeString(pair.getConstituent()) + name(pair.getConstituent()) + ";\n");
	}

	private boolean showStereotype(DeltaPair pair)
	{
		// if this only has the expected set of properties, then ignore it
		DEAppliedStereotype applied = pair.getConstituent().asAppliedStereotype();
		if (!ignoreStereotype(applied.getStereotype()))
			return true;
		
		// all the properties must be ignorable
		if (applied.getProperties() != null)
		{
			for (DEAttribute prop : applied.getProperties().keySet())
				if (!ignoreProperty(prop))
					return true;
		}
		return false;
	}
	
	private boolean ignoreStereotype(DEComponent stereo)
	{
		return stereo.getUuid().equals("component") || stereo.getUuid().equals("interface") || stereo.getUuid().equals("primitive-type");
	}
	
	private boolean isVisual(DEComponent stereo)
	{
		if (stereo.getUuid().equals("visual-effect"))
			return true;
		Set<DEElement> elems = stereo.getSuperElementClosure(perspective, false);
		for (DEElement st : elems)
			if (st.getUuid().equals("visual-effect"))
				return true;		
		return false;
	}
	
	private static final String[] UUIDs = {
		DEElement.IMPLEMENTATION_STEREOTYPE_PROPERTY,
		DEComponent.FACTORY_STEREOTYPE_PROPERTY,
		DEComponent.PLACEHOLDER_STEREOTYPE_PROPERTY,
		DEComponent.BEAN_STEREOTYPE_PROPERTY};
	private boolean ignoreProperty(DEAttribute prop)
	{
		for (String name : UUIDs)
			if (prop.getUuid().equals(name))
				return true;
		return false;
	}

	private String makeAppliedStereotypeString(DEConstituent obj)
	{
		if (obj.getAppliedStereotypes() == null)
			return "";
		String str = "";
		for (DEAppliedStereotype applied : obj.getAppliedStereotypes())
			str += makeAppliedStereotypeString(applied);
		return str;
	}
	
	private String makeAppliedStereotypeString(DEAppliedStereotype appl)
	{
		if (isVisual(appl.getStereotype()))
			return "";

		String name = name(appl.getStereotype(), true);
		boolean standard = ignoreStereotype(appl.getStereotype());
		String str = "\u00ab" + name;
		Map<DEAttribute, String> props = appl.getProperties();
		int lp = 0;
		if (props != null)
		{
			str += ": ";
			for (DEAttribute prop : props.keySet())
			{
				if (!standard || !ignoreProperty(prop))
				{
					String val = props.get(prop);
					if (val.equals("false"))
						;  // show nothing for false
					else
					if (val.equals("true"))
						str += (lp++ == 0 ? "" : ", ") + prop;
					else
						str += (lp++ == 0 ? "" : ", ") + prop + " = " + props.get(prop);
				}
			}
		}
		return str + "\u00bb ";
	}

	private void appendAttributeString(String indent, StringBuilder b, DEElement element, DeltaPair pair)
	{
		DEAttribute attr = pair.getConstituent().asAttribute();
		b.append(indent + "\t\t\t");
		b.append(makeReplace(pair) + makeAppliedStereotypeString(attr));
		if (attr.isWriteOnly())
			b.append("write-only ");
		if (attr.isReadOnly())
			b.append("read-only ");

		b.append(name(attr));
		if (attr.getType() != null)
			b.append(": " + name(attr.getType(), true));
		int dvSize = attr.getDefaultValue().size();
		if (dvSize != 0)
		{
			b.append(" = ");
			if (dvSize > 1)
				b.append("(");
			int lp = 0;
			for (DEParameter p : attr.getDefaultValue())
			{
				if (p.getAttribute() != null)
					b.append(name(p.getAttribute(perspective, element)));
				else
				if (p.getLiteral() != null)
					b.append(p.getLiteral());
				b.append(++lp != dvSize ? ", " : "");
			}
			if (dvSize > 1)
				b.append(")");
		}
		
		b.append(";\n");
	}

	private void appendConnectorString(String indent, StringBuilder b, DeltaPair pair)
	{
		DEConnector conn = pair.getConstituent().asConnector();
		b.append(indent + "\t\t\t" + makeReplace(pair) + makeAppliedStereotypeString(conn) + name(conn) +
				(conn.isDelegate() ? " delegates-from " : " joins ") +
				makeConnectorEndString(conn, 0) + " to " + makeConnectorEndString(conn, 1) + ";\n");
	}

	private String makeReplace(DeltaPair pair)
	{
		if (pair.getOriginal() != null)
			return pair.getOriginal() != pair.getConstituent() ? (name(pair.getOriginal()) +  " becomes ") : "";
		return "";
	}

	private String makeConnectorEndString(DEConnector conn, int index)
	{
		DEPart part = conn.getOriginalPart(index);
		if (part == null)
			return name(conn.getOriginalPort(index));
		return name(conn.getOriginalPort(index)) + "@" + name(part);
	}

	private void appendPartString(String indent, StringBuilder b, DEElement element, DeltaPair pair)
	{
		DEPart part = pair.getConstituent().asPart();
		b.append(indent + "\t\t\t" + makeReplace(pair) + makeAppliedStereotypeString(part) + name(part));
		int sSize = part.getSlots().size();
		if (part.getType() != null)
			b.append(": " + name(part.getType(), true) + (sSize == 0 ? ";\n" : "\n"));
		int slp = 0;
		for (DESlot slot : part.getSlots())
		{
			if (slot.isAliased())
			{
				DEAttribute attr = slot.getAttribute(perspective, part.getType());
				b.append(indent + "\t\t\t\t" + name(attr) + " (" + name(slot.getEnvironmentAlias(perspective, element)) + ");\n");
			}
			else
			{
				int dvSize = slot.getValue().size();
				if (dvSize != 0)
				{
					DEAttribute attr = slot.getAttribute(perspective, part.getType());
					b.append(indent + "\t\t\t\t" + name(attr) + " = ");
					if (dvSize > 1)
						b.append("(");
					int lp = 0;
					for (DEParameter p : slot.getValue())
					{
						if (p.getAttribute() != null)
							b.append(name(p.getAttribute()));
						else
						if (p.getLiteral() != null)
							b.append(p.getLiteral());
						b.append(++lp != dvSize ? ", " : "");
					}
					if (dvSize > 1)
						b.append(")");
					b.append(++slp != sSize ? ",\n" : ";\n");
				}
			}
		}
	}

	private void appendPortString(String indent, StringBuilder b, DeltaPair pair)
	{
		DEPort port = pair.getConstituent().asPort();
		b.append(indent + "\t\t\t" + makeReplace(pair) + makeAppliedStereotypeString(port) + name(port));
		if (port.getPortKind() == PortKindEnum.CREATE)
			b.append(" is-create-port");
		if (port.getPortKind() == PortKindEnum.HYPERPORT_START)
			b.append(" is-hyperport-start");
		if (port.getPortKind() == PortKindEnum.HYPERPORT_END)
			b.append(" is-hyperport-end");
		b.append(makeInterfacesString(" provides ", port.getSetProvidedInterfaces()));
		b.append(makeInterfacesString(" requires ", port.getSetRequiredInterfaces()));
		b.append(";\n");
	}

	private String makeInterfacesString(String preamble, Set<? extends DEInterface> interfaces)
	{
		int size = interfaces.size();
		if (size == 0)
			return "";
		int lp = 0;
		preamble += " ";
		for (DEInterface i : interfaces)
			preamble += name(i, true) + (++lp != size ? ", " : "");
		return preamble;
	}

	private String name(DEObject obj)
	{
		if (obj == null)
			return "???";
		if (obj.getName() == null || obj.getName().length() == 0)
			return obj.getUuid();
		else
			return obj.getName().replace(' ', '_');
	}

	private String name(DEElement obj, boolean applyPerspective)
	{
		String name = applyPerspective ? obj.getName(perspective) : obj.getName();
		if (name == null || name.length() == 0)
			return obj.getUuid();
		else
			return name.replace(' ', '_');
	}
	
	private boolean isRawPlaceholder(DEComponent c)
  {
    DEAppliedStereotype applied = c.getAppliedStereotype(c.getHomeStratum());
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.PLACEHOLDER_STEREOTYPE_PROPERTY);
  }

	private boolean isRawBean(DEComponent c)
  {
    DEAppliedStereotype applied = c.getAppliedStereotype(c.getHomeStratum());
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.BEAN_STEREOTYPE_PROPERTY);
  }

  private boolean isRawFactory(DEComponent c)
  {
    DEAppliedStereotype applied = c.getAppliedStereotype(c.getHomeStratum());
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.FACTORY_STEREOTYPE_PROPERTY);
  }
}
