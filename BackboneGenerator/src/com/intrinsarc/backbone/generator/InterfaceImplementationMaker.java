package com.intrinsarc.backbone.generator;

import java.io.*;

import com.intrinsarc.deltaengine.base.*;

public class InterfaceImplementationMaker
{

	private File generationDir;
	private DEStratum perspective;
	private DEInterface iface;

	public InterfaceImplementationMaker(File generationDir, DEStratum perspective, DEInterface iface)
	{
		this.generationDir = generationDir;
		this.perspective = perspective;
		this.iface = iface;
	}

	public boolean possiblyMakeDefinition() throws BackboneGenerationException
	{
		// turn the class name into a directory
		// NOTE: the well-formedness rules guarantee there will be only 1 impl class
		String fullInterfaceName = iface.getImplementationClass(perspective);
		String directory = fullInterfaceName.replace('.', '/');
		File realFile = new File(generationDir, directory + ".java");
		
		// if this exists, go no further
		if (realFile.exists())
			return false;
		
		// make sure the directory is there
		realFile.getParentFile().mkdirs();
		
		// if this doesn't exist, generate the preface
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(realFile));

			// create the interface definition
			int index = fullInterfaceName.lastIndexOf('.');
			if (index != -1) // i.e. if it isn't the default package
			{
				writer.write("package " + fullInterfaceName.substring(0, index) + ";");
				writer.newLine();
				writer.newLine();
			}

			// write the interface definition
			String interfaceName = index == -1 ? fullInterfaceName : fullInterfaceName.substring(index + 1);
			
			writer.write("public interface " + interfaceName);
			writer.newLine();
			writer.write("{");
			writer.newLine();
			writer.write("\t//@todo add in the methods");
			writer.newLine();
			writer.write("}");
			writer.newLine();
		}
		catch (IOException ex)
		{
			throw new BackboneGenerationException(
					"Problem writing to file " + realFile + " when creating interface implementation definition", null);
		}
		finally
		{
			if (writer != null)
				try
				{
					writer.close();
				}
				catch (IOException ex)
				{
					throw new BackboneGenerationException("Problem closing file " + realFile, ex);
				}
		}
		return true;
	}
}
