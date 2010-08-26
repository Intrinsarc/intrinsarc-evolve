package com.intrinsarc.backbone.generator;

import java.io.*;
import java.util.*;

import com.intrinsarc.deltaengine.base.*;

public class InterfaceDefinitionRefresher extends ImplementationRefresher
{
	private DEStratum perspective;
	private DEInterface iface;

	public InterfaceDefinitionRefresher(File generationDir, DEStratum perspective, DEInterface iface)
	{
		super(generationDir, iface.getImplementationClass(perspective));
		this.perspective = perspective;
		this.iface = iface;
	}

	@Override
	public void makePreamble(BufferedWriter writer) throws IOException
	{
		String fullInterfaceName = getFullClassName();
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
	}

	@Override
	public Map<String, String> makeGeneratedCode(BufferedWriter writer) throws IOException
	{
		boolean start = true;
		for (String extend : iface.getImplementationInheritances(perspective))
		{
			if (start)
				writer.write("\textends ");
			else
				writer.write(", ");
			writer.write(removeRedundantPrefixes(extend));
			start = false;
		}
		writer.write("{");
		writer.newLine();
		return new HashMap<String, String>();
	}

	@Override
	public void makePostamble(BufferedWriter writer, Map<String, String> newTypes) throws IOException
	{
		writer.write("\t//@todo add in the methods");
		writer.newLine();
		writer.write("}");
		writer.newLine();
	}
}
