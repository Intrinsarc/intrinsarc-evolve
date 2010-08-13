package com.intrinsarc.backbone.printers;

import java.util.*;

import com.intrinsarc.deltaengine.base.*;

public class ElementPrinter
{
	private DEStratum perspective;
	private DEElement element;
	/** how do we want to see the output? */
	private ReferenceHelper ref;

	public ElementPrinter(DEStratum perspective, DEElement object, BackbonePrinterMode mode)
	{
		this.perspective = perspective;
		this.element = object;
		ref = new ReferenceHelper(element.getHomeStratum(), mode);
	}
	
	public String makePrintString(String indent)
	{
		StringBuilder b = new StringBuilder();
		makeElementString(indent, b, element.asElement());
		return b.toString().replace("\t", "    ");
	}

	private void makeElementString(String indent, StringBuilder b, DEElement elem)
	{
		if (elem.asRequirementsFeature() != null)
		{
			DERequirementsFeature feature = elem.asRequirementsFeature();
			b.append(indent + "\tfeature " + ref.name(feature));
		}
		else
		if (elem.asInterface() != null)
		{
			DEInterface iface = elem.asInterface();
			b.append(indent + "\tinterface " + ref.name(iface));
		}
		else
		if (elem.asComponent() != null)
		{
			DEComponent c = elem.asComponent();
			b.append(indent + "\tcomponent " + ref.name(c) +
					(isNormal(c) ? " is-normal" : "") +
					(isRawFactory(c) ? " is-factory" : "") +
					(isRawPlaceholder(c) ? " is-placeholder" : "") +
					(isRawBean(c) ? " is-bean" : "") +
					(hasLifecycleCallbacks(c) ? " has-lifecycle-callbacks" : "") +
					(c.getComponentKind() == ComponentKindEnum.PRIMITIVE ? " is-primitive" :
					  (c.getComponentKind() == ComponentKindEnum.STEREOTYPE ? " is-stereotype" : "")));
		}

		String implClass = elem.getImplementationClass(null);
		String impl = implClass != null ? " implementation-class " + implClass : "";
		b.append(impl);
		b.append("\n" + makeRString(indent, elem) + indent + "\t{\n");
		
		for (DeltaPair pair : elem.getDeltas(ConstituentTypeEnum.DELTA_APPLIED_STEREOTYPE).getReplaceObjects())
		{
			if (showStereotype(pair.getConstituent().asAppliedStereotype()))
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
		appendAll(b, indent, "subfeatures", elem, ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK, null);
		
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
				ret += ref.reference(e) + (++lp != size ? ", " : "");
		}
		size = c.getSubstitutes().size();
		if (size > 0)
		{
			ret += " replaces ";
			int lp = 0;
			for (DEElement e : c.getSubstitutes())
				ret += ref.reference(e) + (++lp != size ? ", " : "");
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
			int size = deletes.size();
			int lp = 0;
			for (String uuid : deletes)
			{
				b.append(indent + "\t\t\t" + ref.reference(GlobalDeltaEngine.engine.locateObjectForStereotype(uuid)));
				if (++lp == size)
					b.append(";\n");
				else
					b.append(",\n");
			}
		}
	}

	private boolean append(StringBuilder b, String indent, String preamble, DEElement element, ConstituentTypeEnum type, boolean replace, Comparator<DeltaPair> sorter)
	{
		IDeltas deltas = element.getDeltas(type);
		
		// get all the possible constituents so we can find the original to translate replacement
		Set<DeltaPair> all = deltas.getConstituents(perspective);
		Map<String, DEConstituent> originals = new HashMap<String, DEConstituent>();
		for (DeltaPair p : all)
			originals.put(p.getUuid(), p.getOriginal());
		
		Set<DeltaPair> pairs = replace ? deltas.getReplaceObjects() : deltas.getAddObjects();
		if (!pairs.isEmpty())
		{
			b.append(indent + "\t\t" + (replace ? "replace-" : "") + preamble + ":\n");
			List<DeltaPair> sorted = new ArrayList<DeltaPair>(pairs);
			if (sorter != null)
				Collections.sort(sorted, sorter);
			DeltaPair last = sorted.isEmpty() ? null : sorted.get(sorted.size() - 1);
			for (DeltaPair p : sorted)
			{
				DeltaPair pair = new DeltaPair(p.getUuid(), originals.get(p.getUuid()), p.getConstituent());
				
				DEObject obj = pair.getConstituent();
				if (obj instanceof DEOperation)
					appendOperationString(indent, b, pair, replace, p == last);
				else
				if (obj instanceof DEAttribute)
					appendAttributeString(indent, b, element, pair, replace, p == last);
				else
				if (obj instanceof DEPort)
					appendPortString(indent, b, pair, replace, p == last);
				else
				if (obj instanceof DEConnector)
					appendConnectorString(indent, b, pair, replace, p == last);
				else
				if (obj instanceof DEPart)
					appendPartString(indent, b, element, pair, replace, p == last);
				else
				if (obj instanceof DERequirementsFeatureLink)
					appendRequirementsFeatureLinkString(indent, b, pair, replace, p == last);
			}
			return true;
		}		
		return false;
	}

	private void appendRequirementsFeatureLinkString(String indent, StringBuilder b, DeltaPair pair, boolean replace, boolean last)
	{
		DERequirementsFeatureLink link = pair.getConstituent().asRequirementsFeatureLink();		
		b.append(indent + "\t\t\t" + makeReplace(pair, replace) + makeAppliedStereotypeString(pair.getConstituent()) + ref.reference(pair.getConstituent()) + " " + translateLinkKind(link.getKind()) + (last ? ";\n" : ",\n"));
	}

	private String translateLinkKind(SubfeatureKindEnum kind)
	{
		switch (kind)
		{
		case MANDATORY:
			return "is-mandatory";
		case ONE_OF:
			return "is-one-of";
		case ONE_OR_MORE:
			return "is-one-or-more";
		case OPTIONAL:
			return "is-optional";
		}
		throw new IllegalStateException("Cannot translate subfeature link kind: " + kind);
	}

	private void appendOperationString(String indent, StringBuilder b, DeltaPair pair, boolean replace, boolean last)
	{
		b.append(indent + "\t\t\t" + makeReplace(pair, replace) + makeAppliedStereotypeString(pair.getConstituent()) + ref.reference(pair.getConstituent()) + (last ? ";\n" : ",\n"));
	}

	private boolean showStereotype(DEAppliedStereotype applied)
	{
		// if this is non-standard, we must show
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
		String uuid = stereo.getUuid();
		return
		  isVisual(stereo) ||
			uuid.equals("component") ||
			uuid.equals("interface") ||
			uuid.equals("primitive-type") ||
			uuid.equals("port") ||
			uuid.equals("part") ||
			uuid.equals("attribute");

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
		DEComponent.BEAN_STEREOTYPE_PROPERTY,
		DEComponent.PLACEHOLDER_STEREOTYPE_PROPERTY,
		DEComponent.LIFECYCLE_CALLBACKS_PROPERTY}
	;
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
			if (showStereotype(applied))
				str += makeAppliedStereotypeString(applied);
		return str + (str.length() > 0 ? " " : "");
	}
	
	private String makeAppliedStereotypeString(DEAppliedStereotype appl)
	{
		String name = ref.reference(appl.getStereotype());
		String str = "\u00ab" + name;
		Map<DEAttribute, String> props = appl.getProperties();
		int lp = 0;
		if (props != null)
		{
			boolean first = true;
			for (DEAttribute prop : props.keySet())
			{
				if (!ignoreProperty(prop))
				{
					if (first)
					{
						str += ": ";
						first = false;
					}
					String val = props.get(prop);
					if (val.equals("false"))
						;  // show nothing for false
					else
					if (val.equals("true"))
						str += (lp++ == 0 ? "" : ", ") + ref.reference(prop);
					else
						str += (lp++ == 0 ? "" : ", ") + ref.reference(prop) + " = \"" + props.get(prop) + "\"";
				}
			}
		}
		return str + "\u00bb ";
	}

	private void appendAttributeString(String indent, StringBuilder b, DEElement element, DeltaPair pair, boolean replace, boolean last)
	{
		DEAttribute attr = pair.getConstituent().asAttribute();
		b.append(indent + "\t\t\t");
		b.append(makeReplace(pair, replace) + makeAppliedStereotypeString(attr));
		if (attr.isWriteOnly())
			b.append("write-only ");
		if (attr.isReadOnly())
			b.append("read-only ");

		b.append(ref.name(attr));
		if (attr.getType() != null)
			b.append(": " + ref.reference(attr.getType()));
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
					b.append(ref.reference(p.getAttribute(perspective, element)));
				else
				if (p.getLiteral() != null)
					b.append(p.getLiteral());
				b.append(++lp != dvSize ? ", " : "");
			}
			if (dvSize > 1)
				b.append(")");
		}
		
		if (last)
			b.append(";\n");
		else
			b.append(",\n");
	}

	private void appendConnectorString(String indent, StringBuilder b, DeltaPair pair, boolean replace, boolean last)
	{
		DEConnector conn = pair.getConstituent().asConnector();
		b.append(indent + "\t\t\t" + makeReplace(pair, replace) + makeAppliedStereotypeString(conn) + ref.name(conn) +
				(conn.isDelegate() ? " delegates-from " : " joins ") +
				makeConnectorEndString(conn, 0) + " to " + makeConnectorEndString(conn, 1) + (last ? ";\n" : ",\n"));
	}

	private String makeReplace(DeltaPair pair, boolean replace)
	{
		if (replace && pair.getOriginal() != null)
			return replace ? (ref.reference(pair.getOriginal()) +  " becomes ") : "";
		return "";
	}

	private String makeConnectorEndString(DEConnector conn, int index)
	{
		DEPart part = conn.getOriginalPart(index);
		String ind = conn.getIndex(index) != null ? "[" + conn.getIndex(index) + "]" : "";
		if (part == null)
			return ref.reference(conn.getOriginalPort(index)) + ind;
		return ref.name(conn.getOriginalPort(index)) + ind + "@" + ref.reference(part);
	}

	private void appendPartString(String indent, StringBuilder b, DEElement element, DeltaPair pair, boolean replace, boolean last)
	{
		DEPart part = pair.getConstituent().asPart();
		b.append(indent + "\t\t\t" + makeReplace(pair, replace) + makeAppliedStereotypeString(part) + ref.name(part));
		List<DESlot> slots = part.getSlots();
		int size = slots.size();
		if (part.getType() != null)
			b.append(": " + ref.reference(part.getType()));

		// slots
		int slp = 0;
		if (size > 0)
			b.append("\n" + indent + "\t\t\t\tslots:\n");			
		for (DESlot slot : slots)
		{
			if (slot.isAliased())
			{
				DEAttribute attr = slot.getAttribute(perspective, part.getType());
				b.append(indent + "\t\t\t\t\t" + ref.reference(attr) + " (" + ref.reference(slot.getEnvironmentAlias(perspective, element)) + ")");
			}
			else
			{
				int dvSize = slot.getValue().size();
				if (dvSize != 0)
				{
					DEAttribute attr = slot.getAttribute(perspective, part.getType());
					b.append(indent + "\t\t\t\t\t" + ref.reference(attr) + " = ");
					if (dvSize > 1)
						b.append("(");
					int lp = 0;
					for (DEParameter p : slot.getValue())
					{
						if (p.getAttribute() != null)
							b.append(ref.reference(p.getAttribute()));
						else
						if (p.getLiteral() != null)
							b.append(p.getLiteral());
						b.append(++lp != dvSize ? ", " : "");
					}
					if (dvSize > 1)
						b.append(")");
				}
			}
			b.append(++slp != size ? "\n" : "");
		}
		
		// any remaps?
		Set<DeltaPair> remaps = part.getPortRemaps();
		if (!remaps.isEmpty())
		{
			b.append("\n" + indent + "\t\t\t\tport-remaps:\n");
			for (DeltaPair remap : remaps)
				b.append(indent + "\t\t\t\t\t" + ref.reference(remap.getConstituent()) + " maps-onto " + remap.getUuid());
		}
		b.append(last ? ";\n" : ",\n");
	}

	private void appendPortString(String indent, StringBuilder b, DeltaPair pair, boolean replace, boolean last)
	{
		DEPort port = pair.getConstituent().asPort();
		b.append(indent + "\t\t\t" + makeReplace(pair, replace) + makeAppliedStereotypeString(port) + ref.name(port));
		if (port.getLowerBound() != 1 || port.getUpperBound() != 1)
			b.append("[" + port.getLowerBound() + " upto " + (port.getUpperBound() == -1 ? "*" : port.getUpperBound()) + "]");
		if (port.getPortKind() == PortKindEnum.CREATE)
			b.append(" is-create-port");
		if (port.getPortKind() == PortKindEnum.HYPERPORT_START)
			b.append(" is-hyperport-start");
		if (port.getPortKind() == PortKindEnum.HYPERPORT_END)
			b.append(" is-hyperport-end");
		if (port.getPortKind() == PortKindEnum.AUTOCONNECT)
			b.append(" is-autoconnect");
		if (port.isOrdered())
			b.append(" is-ordered");
		if (port.isBeanMain())
			b.append(" is-bean-main");
		if (port.isBeanNoName())
			b.append(" is-bean-noname");
		b.append(makeInterfacesString(" provides", port.getSetProvidedInterfaces()));
		b.append(makeInterfacesString(" requires", port.getSetRequiredInterfaces()));
		if (last)
			b.append(";\n");
		else
			b.append(",\n");
	}

	private String makeInterfacesString(String preamble, Set<? extends DEInterface> interfaces)
	{
		int size = interfaces.size();
		if (size == 0)
			return "";
		int lp = 0;
		preamble += " ";
		for (DEInterface i : interfaces)
			preamble += ref.reference(i) + (++lp != size ? " & " : "");
		return preamble;
	}

	private boolean isNormal(DEComponent c)
	{
    DEAppliedStereotype applied = c.getRawAppliedStereotype();
    return
    	applied != null && !c.getRawResembles().isEmpty() &&
    	!isRawPlaceholder(c) && !isRawFactory(c) && !isRawBean(c);
	}
	
	private boolean isRawPlaceholder(DEComponent c)
  {
    DEAppliedStereotype applied = c.getRawAppliedStereotype();
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.PLACEHOLDER_STEREOTYPE_PROPERTY);
  }

	private boolean isRawBean(DEComponent c)
  {
    DEAppliedStereotype applied = c.getRawAppliedStereotype();
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.BEAN_STEREOTYPE_PROPERTY);
  }

	private boolean hasLifecycleCallbacks(DEComponent c)
  {
    DEAppliedStereotype applied = c.getRawAppliedStereotype();
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.LIFECYCLE_CALLBACKS_PROPERTY);
  }

  private boolean isRawFactory(DEComponent c)
  {
    DEAppliedStereotype applied = c.getRawAppliedStereotype();
    if (applied == null)
      return false;
    return applied.getBooleanProperty(DEComponent.FACTORY_STEREOTYPE_PROPERTY);
  }
}
