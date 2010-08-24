package com.intrinsarc.backbone.generator;

import java.io.*;
import java.util.*;

import static com.intrinsarc.backbone.generator.hardcoded.common.WriterHelper.*;

import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.idraw.environment.*;

public class LeafImplementationRefresher
{
	private static final String START_GENERATED_CODE = "// start generated code";
	private static final String END_GENERATED_CODE   = "// end generated code";
	private File generationDir;
	private DEStratum perspective;
	private DEComponent leaf;
	private String fullClassName;

	public LeafImplementationRefresher(File generationDir, DEStratum perspective, DEComponent leaf)
	{
		this.generationDir = generationDir;
		this.perspective = perspective;
		this.leaf = leaf;
		// guaranteed to be a single impl class, by the well-formedness rules
		fullClassName = leaf.getImplementationClass(perspective);
	}
	
	/**
	 * generate or refresh the leaf code
	 * @return true if the file was changed
	 * @throws BackboneGenerationException
	 */
	public boolean refreshLeafCode() throws BackboneGenerationException
	{
		// turn the class name into a directory
		String directory = fullClassName.replace('.', '/');
		File realFile = new File(generationDir, directory + ".java");
		
		// make sure the directory is there
		realFile.getParentFile().mkdirs();
		
		File tempFile = new File(generationDir, directory + ".java.temp");
		File backupFile = new File(generationDir, directory + ".java.bak");		
		
		// if this doesn't exist, generate the preface
		boolean exists = realFile.exists();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		StringWriter complexWriter = new StringWriter();
		boolean deleteTemp = false;
		try
		{
			tempFile.getParentFile().mkdirs();
			writer = new BufferedWriter(new FileWriter(tempFile));
			if (exists)
				reader = new BufferedReader(new FileReader(realFile));

			// if we can, copy the preamble
			if (!exists)
			{
				makePreamble(writer);
				Map<String, String> vars = makeGeneratedCode(writer);
				writer.append(complexWriter.toString());				
				makePostamble(writer, vars);
			}
			else
			{
				if (!copyPreamble(reader, writer))
				{
//					System.out.println("$$ skipping file " + realFile + ", as start generator marker not found...");
					deleteTemp = true;
					return false;
				}
				makeGeneratedCode(writer);
				writer.append(complexWriter.toString());
				if (!copyPostamble(reader, writer))
				{
//					System.out.println("$$ skipping file " + realFile + ", as end generator marker not found...");
					deleteTemp = true;
					return false;
				}
			}			
		}
		catch (IOException ex)
		{
			throw new BackboneGenerationException("Problem writing to file " + tempFile + " when refreshing leaves", null);
		}
		finally
		{
			try
			{
				if (writer != null)
					writer.close();
				if (reader != null)
					reader.close();
				if (deleteTemp)
					tempFile.delete();
			}
			catch (IOException ex)
			{
				throw new BackboneGenerationException("Problem closing file " + tempFile + " when writing hardcoded factory", ex);
			}
		}
		
		// move the files over now to complete the generation
		if (exists && identicalContents(realFile, tempFile))
		{
			tempFile.delete();
			return false;
		}
		else
		{
			if (GlobalPreferences.preferences.getRawPreference(BackboneWriter.BB_WRITE_BACKUPS).asBoolean())
				moveFile(realFile, backupFile);
			moveFile(tempFile, realFile);
			return true;
		}
	}

	public static boolean identicalContents(File file1, File file2) throws BackboneGenerationException
	{
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;
		try
		{
			reader1 = new BufferedReader(new FileReader(file1));
			reader2 = new BufferedReader(new FileReader(file2));
			
			for (;;)
			{
				String line1 = reader1.readLine();
				String line2 = reader2.readLine();
				
				if (line1 == null && line2 != null)
					return false;
				if (line1 != null && line2 == null)
					return false;
				// have we got to the end of the file?
				if (line1 == null)
					break;
				// are the lines identical?
				if (!line1.equals(line2))
					return false;
			}
		}
		catch (IOException ex)
		{
			throw new BackboneGenerationException("Problem comparing file " + file1 + " and " + file2, ex);
		}
		finally
		{
			try
			{
				if (reader1 != null)
					reader1.close();
				if (reader2 != null)
					reader2.close();
			}
			catch (IOException ex)
			{
				throw new BackboneGenerationException("Problem closing file", ex);
			}
		}
			
		// if we got here there is no change...
		return true;
	}

	/** copy the file from into the location to
	 * 
	 * @param realFile
	 * @param backupFile
	 */
	public static void moveFile(File from, File to)
	{
		to.delete();
		from.renameTo(to);
	}

	private boolean copyPreamble(BufferedReader reader, BufferedWriter writer) throws IOException
	{
		// copy lines until we get to the end of the file or to the start marker
		String line = null;
		boolean matched = false;
		while ((line = reader.readLine()) != null && !(matched = line.contains(START_GENERATED_CODE)))
		{
			writer.write(line);
			writer.newLine();
		}

		return matched;
	}

	private boolean copyPostamble(BufferedReader reader, BufferedWriter writer) throws IOException
	{
		// copy lines until we get to the end of the file or to the start marker
		String line = null;
		boolean matched = false;
		while ((line = reader.readLine()) != null && !(matched = line.contains(END_GENERATED_CODE)))
			;
		
		while ((line = reader.readLine()) != null)
		{
			writer.write(line);
			writer.newLine();
		}
		return matched;
	}

	private void makePreamble(BufferedWriter writer) throws IOException
	{
		int index = fullClassName.lastIndexOf('.');
		if (index != -1) // i.e. if it isn't the default package
		{
			writer.write("package " + fullClassName.substring(0, index) + ";");
			writer.newLine();
			writer.newLine();
		}
		
		String className = index == -1 ? fullClassName : fullClassName.substring(index + 1);
		writer.write("public class " + className);
		writer.newLine();
	}
	
	private Map<String, String> makeGeneratedCode(BufferedWriter writer) throws IOException
	{
		StringWriter complex = new StringWriter();
		BufferedWriter complexWriter = new BufferedWriter(complex);
		// write the start marker
		writer.write(START_GENERATED_CODE);
		writer.newLine();
		
		// get the main port
		List<DEPort> mains = leaf.getBeanMainPorts(perspective);
		List<DEPort> nonames = leaf.getBeanNoNamePorts(perspective);
		if (mains.size() == 1)
		{
			writer.write("  // main port");		
			writer.newLine();
			Set<? extends DEInterface> provided = mains.get(0).getSetProvidedInterfaces();
			int lp = 0;
			writer.write("  implements ");
			for (DEInterface iface : provided)
			{
				if (lp++ != 0)
					writer.write(", ");
				writer.write(iface.getImplementationClass(perspective));
			}
			writer.newLine();
		}
		writer.write("{");
		writer.newLine();

		Set<DeltaPair> attrPairs = leaf.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective);
		if (!attrPairs.isEmpty())
		{
			writer.write("  // attributes");				
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
			writer.append("  // attribute setters and getters");
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
		for (int lp = 0; lp < 2; lp++)
		{
			if (lp == 0)
				writer.write("  // required ports");
			else
				writer.write("  // provided ports");				
			writer.newLine();

			for (DeltaPair pair : leaf.getDeltas(ConstituentTypeEnum.DELTA_PORT).getConstituents(perspective))
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
						String iname = required.getImplementationClass(perspective);
						String tname = getAfterLastDot(iname);
						String pname = port.getName();
						String vname = first ? pname : pname + "_" + tname + "Required";
						if (many)
						{
							String mname = "set" + makeSingular(upper(pname));
							String aname = "add" + (nonames.contains(port) ? "" : makeSingular(upper(pname)));
							
							String mname2 = "remove" + makeSingular(upper(pname));
							writer.write("\tprivate java.util.List<" + iname + "> " + vname + " = new java.util.ArrayList<" + iname + ">();");
							writer.newLine();
							complexWriter.write("\tpublic void " + mname + "(" + iname + " " + vname + ", int index) { PortHelper.fill(this." + vname + ", " + vname + ", index); }");					
							complexWriter.newLine();
							complexWriter.write("\tpublic void " + aname + "(" + iname + " " + vname + ") { PortHelper.fill(this." + vname + ", " + vname + ", -1); }");					
							complexWriter.newLine();
							complexWriter.write("\tpublic void " + mname2 + "(" + iname + " " + vname + ") { PortHelper.remove(this." + vname + ", " + vname + "); }");					
						}
						else
						{
							String mname = "set" + upper(pname);
							writer.write("\tprivate " + iname + " " + vname + ";");
							writer.newLine();
							complexWriter.write("\tpublic void " + mname + "(" + iname + " " + vname + ") { this." + vname + " = " + vname + "; }");
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
						String iname = provided.getImplementationClass(perspective);
						String tname = getAfterLastDot(iname);
						String pname = port.getName();
						String vname = pname + (complexPort ? tname : "") + "_Provided";
						String mname = "get" + upper(pname) + "_Provided";
						String rmname = "remove" + makeSingular(upper(pname));
						String newTypeName = tname + upper(pname) + "Impl";
						newTypes.put(newTypeName, iname);
						
						if (many)
						{
							writer.write("\tprivate java.util.List<" + newTypeName + "> " + " " + vname + " = new java.util.ArrayList<" + newTypeName + ">();");
							writer.newLine();
							complexWriter.write("\tpublic " + iname + " " + mname + "(int index) { return PortHelper.fill(" + vname + ", new " + newTypeName + "(), index); }");
							complexWriter.newLine();
							complexWriter.write("\tpublic void " + rmname + "(" + iname + "  provided) { PortHelper.remove(" + vname + ", provided); }");
						}
						else
						{
							writer.write("\tprivate " + newTypeName + " " + vname + " = new " + newTypeName + "();");
							writer.newLine();
							complexWriter.write("\tpublic " + iname + " " + mname + "() { return " + vname + "; }");					
						}
						complexWriter.newLine();
					}
				}
			}
		}

		writer.newLine();
		writer.append("  // port setters and getters");
		writer.newLine();
		complexWriter.flush();
		writer.append(complex.toString());
		
		
		// write the end marker
		writer.write(END_GENERATED_CODE);
		writer.newLine();
		return newTypes;
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
	
	private static final String BB_API = "com.intrinsarc.backbone.runtime.api.";
	public static String removeRedundantPrefixes(String impl)
	{    
    if (impl.startsWith(BB_API))
    	impl = impl.substring(BB_API.length());
    if (impl.startsWith("java.lang."))
    	impl = impl.substring("java.lang.".length());
    return impl;
	}

	private void makePostamble(BufferedWriter writer, Map<String, String> newTypes) throws IOException
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
