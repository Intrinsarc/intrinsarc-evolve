package com.intrinsarc.backbone.generator;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import java.io.*;
import java.util.*;

import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.repositorybase.*;

public class LeafImplementationRefresher extends ImplementationRefresher
{
	private DEStratum perspective;
	private DEComponent leaf;

	public LeafImplementationRefresher(File generationDir, DEStratum perspective, DEComponent leaf)
	{
		super(generationDir, leaf.getImplementationClass(perspective));
		this.perspective = perspective;
		this.leaf = leaf;
		
	}
	
	@Override
	public void makePreamble(BufferedWriter writer) throws IOException
	{
		String fullClassName = getFullClassName();
		int index = fullClassName.lastIndexOf('.');
		if (index != -1) // i.e. if it isn't the default package
		{
			writer.write("package " + fullClassName.substring(0, index) + ";");
			writer.newLine();
			writer.newLine();
			writer.write("import com.intrinsarc.backbone.runtime.api.*;");
			writer.newLine();
			writer.newLine();			
		}
		
		String className = index == -1 ? fullClassName : fullClassName.substring(index + 1);
		writer.write("public class " + className);
		writer.newLine();
	}
	
	@Override
	public Map<String, String> makeGeneratedCode(BufferedWriter writer) throws IOException
	{
		boolean useInheritance = !leaf.extractBooleanAppliedStereotypeProperty(
				perspective, 
				CommonRepositoryFunctions.COMPONENT, 
				CommonRepositoryFunctions.SUPPRESS_INHERITANCE);
		Set<String> inherits = leaf.getImplementationInheritances(perspective);
		String inherit = inherits.isEmpty() ? null : inherits.iterator().next();
		// don't use inheritance if there is nothing to inherit from
		useInheritance &= inherit != null;
		
		StringWriter complex = new StringWriter();
		BufferedWriter complexWriter = new BufferedWriter(complex);
		
		// get the main port
		List<DEPort> mains = leaf.getBeanMainPorts(perspective);
		boolean lifecycle = leaf.hasLifecycleCallbacks(perspective);
		if (mains.size() == 1 || lifecycle)
		{
			writer.write("\t// main port");		
			writer.newLine();
			
			// if we are inheriting, get the super classes to inherit the implementation from
			if (useInheritance)
			{
				if (inherit != null && !leaf.getImplementationClass(perspective).equals(inherit))
					writer.write(" extends " + removeRedundantPrefixes(inherit));
			}
			
			Set<? extends DEInterface> provided = mains.get(0).getSetProvidedInterfaces();
			int lp = 0;
			writer.write(" implements ");
			for (DEInterface iface : provided)
			{
				if (lp++ != 0)
					writer.write(", ");
				writer.write(removeRedundantPrefixes(iface.getImplementationClass(perspective)));
			}
			
			// do we want lifecycle callbacks?
			if (lifecycle)
			{
				if (lp++ != 0)
					writer.write(", ");
				writer.write("ILifecycle");
			}
			writer.newLine();
		}
		writer.write("{");
		writer.newLine();

		Set<DeltaPair> attrPairs = getAttributePairs(useInheritance);
		if (!attrPairs.isEmpty())
		{
			writer.write("\t// attributes");				
			writer.newLine();
			
			// write all the attributes
			for (DeltaPair pair : attrPairs)
			{
				DEAttribute property = pair.getConstituent().asAttribute();
				if (property.isSuppressGeneration())
					continue;
				
		    // for low level attributes, take the implementation class name in preference
		    DEElement type = property.getType();
		    String preImpl = removeRedundantPrefixes(type.getImplementationClass(perspective));
		    String impl = PrimitiveHelper.translateLongToShortPrimitive(preImpl);
	
				String name = property.getName();
				writer.write("\tprivate " + impl + " " + name);
				writer.write(makeInitializer(leaf, property, preImpl, preImpl.length() != impl.length()));
				writer.write(";");
				writer.newLine();
				// add a method to either get or set
				String up = upper(name);
				if (!property.isWriteOnly())
				{
					if ("boolean".equals(impl))
						complexWriter.write("\tpublic " + impl + " is" + up + "() { return " + name + "; }");
					else
						complexWriter.write("\tpublic " + impl + " get" + up + "() { return " + name + "; }");
					complexWriter.newLine();
				}
				if (!property.isReadOnly())
				{
					complexWriter.write("\tpublic void set" + up + "(" + impl + " " + name + ") { this." + name + " = " + name + ";}");
					complexWriter.newLine();
				}
			}
			
			writer.newLine();
			writer.append("\t// attribute setters and getters");
			writer.newLine();
			complexWriter.flush();
			writer.append(complex.toString());
			writer.newLine();
		}
		
		// start complex again
		complex = new StringWriter();
		complexWriter = new BufferedWriter(complex);
		
		// keep track of any new type names
		Map<String, String> newTypes = new HashMap<String, String>();
		
		// write all the interface implementations
		boolean output[] = {false, false};
		for (int lp = 0; lp < 2; lp++)
		{
			for (DeltaPair pair : getPortPairs(useInheritance))
			{
				DEPort port = pair.getConstituent().asPort();
				if (port.getPortKind() == PortKindEnum.CREATE || port.isSuppressGeneration())
					continue;
				
				// does this have multiplicty
				boolean many = port.getUpperBound() != 1;
	
				// for required, generate an instance var of the correct multiplicity
				if (lp == 0)
				{
					boolean first = true;
					for (DEInterface required : port.getSetRequiredInterfaces())
					{
						if (!output[lp])
						{
							writer.write("\t// required ports");
							writer.newLine();
						}
						output[lp] = true;
						
						PortMethodHelper ph = new PortMethodHelper(perspective, leaf, port, required);
						ph.resolveSetMethodNames();
						
						String iname = removeRedundantPrefixes(required.getImplementationClass(perspective));
						String tname = getAfterLastDot(iname);
						String pname = port.getName();
						String vname = first ? pname : pname + "_" + tname + "Required";
						if (many)
						{
							writer.write("\tprivate java.util.List<" + iname + "> " + vname + " = new java.util.ArrayList<" + iname + ">();");
							writer.newLine();
							complexWriter.write("\tpublic void " + ph.getAddIndexedName() + "(" + iname + " " + vname + ", int index) { PortHelper.fill(this." + vname + ", " + vname + ", index); }");					
							complexWriter.newLine();
							complexWriter.write("\tpublic void " + ph.getAddNoIndexedName() + "(" + iname + " " + vname + ") { PortHelper.fill(this." + vname + ", " + vname + ", -1); }");					
							complexWriter.newLine();
							complexWriter.write("\tpublic void " + ph.getRemoveManyName() + "(" + iname + " " + vname + ") { PortHelper.remove(this." + vname + ", " + vname + "); }");					
						}
						else
						{
							writer.write("\tprivate " + iname + " " + vname + ";");
							writer.newLine();
							complexWriter.write("\tpublic void " + ph.getSetSingleName() + "(" + iname + " " + vname + ") { this." + vname + " = " + vname + "; }");
						}
						
						complexWriter.newLine();
						first = false;
					}
				}
	
				// for provided, generate an instance var of the correct multiplicity
				if (lp == 1 && !mains.contains(port))
				{
					boolean complexPort = port.getSetProvidedInterfaces().size() > 1;
					for (DEInterface provided : port.getSetProvidedInterfaces())
					{
						if (!output[lp])
						{
							writer.write("\t// provided ports");
							writer.newLine();
						}
						output[lp] = true;

						PortMethodHelper ph = new PortMethodHelper(perspective, leaf, port, provided);
						ph.resolveGetMethodNames();
						
						String iname = removeRedundantPrefixes(provided.getImplementationClass(perspective));
						String tname = getAfterLastDot(iname);
						String pname = port.getName();
						String vname = pname + (complexPort ? tname : "") + "_Provided";
						String newTypeName = tname + upper(pname) + "Impl";
						newTypes.put(newTypeName, iname);
						
						if (many)
						{
							writer.write("\tprivate java.util.List<" + newTypeName + "> " + " " + vname + " = new java.util.ArrayList<" + newTypeName + ">();");
							writer.newLine();
							complexWriter.write("\tpublic " + iname + " " + ph.getGetIndexedName() + "(int index) { return PortHelper.fill(" + vname + ", new " + newTypeName + "(), index); }");
							complexWriter.newLine();
							complexWriter.write("\tpublic void " + ph.getRemoveManyName() + "(" + iname + "  provided) { PortHelper.remove(" + vname + ", provided); }");
						}
						else
						{
							writer.write("\tprivate " + newTypeName + " " + vname + " = new " + newTypeName + "();");
							writer.newLine();
							complexWriter.write("\tpublic " + iname + " " + ph.getGetSingleName() + "() { return " + vname + "; }");					
						}
						complexWriter.newLine();
					}
				}
			}
		}

		if (output[0] || output[1])
		{
			writer.newLine();
			writer.append("\t// port setters and getters");
			writer.newLine();
			complexWriter.flush();
			writer.append(complex.toString());
			writer.newLine();
		}
		return newTypes;
	}
	
	private Set<DeltaPair> getAttributePairs(boolean useInheritance)
	{
		if (useInheritance)
		{
			Set<DeltaPair> pairs = new LinkedHashSet<DeltaPair>();
			pairs.addAll(leaf.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getAddObjects());
			pairs.addAll(leaf.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getReplaceObjects());
			return pairs;
		}
		return leaf.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective);
	}

	private Set<DeltaPair> getPortPairs(boolean useInheritance)
	{
		if (useInheritance)
		{
			Set<DeltaPair> pairs = new LinkedHashSet<DeltaPair>();
			pairs.addAll(leaf.getDeltas(ConstituentTypeEnum.DELTA_PORT).getAddObjects());
			pairs.addAll(leaf.getDeltas(ConstituentTypeEnum.DELTA_PORT).getReplaceObjects());
			return pairs;
		}
		return leaf.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective);
	}

	private String makeInitializer(DEComponent owner, DEAttribute attr, String implName, boolean primitive)
	{
		List<DEParameter> params = attr.getDefaultValue();
		int size = params.size();
		if (size == 0)
			return "";
		
		String s = " = ";
		boolean single = size == 1;
		if (single && params.get(0).getAttribute() != null)
			return s + translateParameter(owner, params.get(0));
		
		// handle default
		if (single && "default".equals(params.get(0).getLiteral()))
		{
			if (primitive)
				return "";
			if (implName.equals("String"))
				return s + "\"\"";
			return s + "new " + implName + "()";
		}

		// is this primitive?
		if (primitive && size == 0)
			return "";
		if (size == 1 && (primitive || isNull(params.get(0)) || implName.equals("String")))
			return s + translateParameter(owner, params.get(0));
		else
		{
			s += "new " + implName + "(";
			boolean start = true;
			for (DEParameter p : params)
				if (start)
				{
					s += translateParameter(owner, p);
					start = false;
				} else
					s += ", " + translateParameter(owner, p);
			return s + ")";
		}
	}

	private boolean isNull(DEParameter p)
	{
		return p.getLiteral() != null && p.getLiteral().equals("null");
	}

	private String translateParameter(DEComponent owner, DEParameter parameter)
	{
		if (parameter.getLiteral() != null)
			return parameter.getLiteral();
		// find the correct attribute
		for (DeltaPair pair : owner.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective))
			if (pair.getOriginal() == parameter.getAttribute())
				return pair.getConstituent().getName();
		return null;
	}

	@Override
	public void makePostamble(BufferedWriter writer, Map<String, String> newTypes) throws IOException
	{
		writer.newLine();
		writer.newLine();

		// generate any provided classes
		for (String newName : newTypes.keySet())
		{
			String iface = newTypes.get(newName);
			writer.write("\tprivate class " + newName + " implements " + removeRedundantPrefixes(iface));
			writer.newLine();
			writer.write("\t{");
			writer.newLine();
			
			writer.write("\t\t//@todo add interface methods");
			writer.newLine();
			
			writer.write("\t}");
			writer.newLine();
			writer.newLine();
		}
		
		// write all the properties -- NOTE that since a leaf cannot be resembled or substituted, we can use add objects
		for (DeltaPair pair : leaf.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective))
		{
			DEAttribute property = pair.getConstituent().asAttribute();
			if (!property.isSuppressGeneration())
				continue;
			
			if (!property.isReadOnly())
			{
				writer.write("\tpublic void set" + upper(property.getName()) + "(" + property.getType().getImplementationClass(perspective) + " set)");
				writer.newLine();
				writer.write("\t{");
				writer.newLine();
				writer.write("\t\t//@todo add set code");
				writer.newLine();
				writer.write("\t}");
				writer.newLine();
				writer.newLine();
			}
			if (!property.isWriteOnly())
			{
				writer.write("\tpublic " + property.getType().getImplementationClass(perspective) + " get" + upper(property.getName()) + "()");
				writer.newLine();
				writer.write("\t{");
				writer.newLine();
				writer.write("\t\t//@todo add get code");
				writer.newLine();
				writer.write("\t\treturn null;");
				writer.newLine();
				writer.write("\t}");
				writer.newLine();
				writer.newLine();				
			}
		}
		
		// write all the interface implementations
		for (DeltaPair pair : leaf.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective))
		{
			DEPort port = pair.getConstituent().asPort();
			if (port.getPortKind() == PortKindEnum.CREATE || !port.isSuppressGeneration())
				continue;
			
			// does this have multiplicty
			boolean many = port.getUpperBound() != 1;

			// for required, generate the appropriate method
			for (DEInterface required : port.getSetRequiredInterfaces())
			{
				String iname = required.getImplementationClass(perspective);
				String tname = getAfterLastDot(iname);
				String pname = port.getName();
				String vname = upper(pname) + "_" + tname;
				
				writer.newLine();
				if (many)
				{
					writer.write("\tpublic void add" + vname + "(" + iname + " set, int index)");
					writer.newLine();
					writer.write("\t{");
					writer.newLine();
					writer.write("\t\t//@todo add 'add' code");
				}
				else
				{
					writer.write("\tpublic void set" + vname + "(" + iname + " set)");
					writer.newLine();
					writer.write("\t{");
					writer.newLine();
					writer.write("\t\t//@todo add 'set' code");
				}
				writer.newLine();
				writer.write("\t}");
				writer.newLine();
				writer.newLine();					
			}

			// for provided, generate the appropriate method
			for (DEInterface provided : port.getSetProvidedInterfaces())
			{
				String iname = provided.getImplementationClass(perspective);
				String tname = getAfterLastDot(iname);
				String pname = port.getName();
				String vname = upper(pname) + "_" + tname;
				
				writer.newLine();
				if (many)
				{
					writer.write("\tpublic " + iname  + " get" + vname + "(Class<?> required, int index)");
					writer.newLine();
					writer.write("\t{");
					writer.newLine();
				}
				else
				{
					writer.write("\tpublic " + iname + " get" + vname + "(Class<?> required)");
					writer.newLine();
					writer.write("\t{");
					writer.newLine();
				}
				writer.write("\t\t//@todo add 'get' code");
				writer.newLine();
				writer.write("\t\treturn null;");
				writer.newLine();
				writer.write("\t}");
				writer.newLine();
				writer.newLine();					
			}
		}		
		
		// finish the class
		writer.write("}");
		writer.newLine();
	}
}
