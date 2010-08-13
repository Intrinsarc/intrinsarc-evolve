package com.intrinsarc.backbone.printers;

import java.util.*;

import com.intrinsarc.backbone.nodes.*;
import com.intrinsarc.backbone.nodes.insides.*;

public class LoadListPrinter
{
	private BBLoadList list;
	
	public LoadListPrinter(BBLoadList list, BackbonePrinterMode mode)
	{
		this.list = list;
	}
	
	public String makePrintString(String indent)
	{
		StringBuilder b = new StringBuilder();

		b.append(indent + "load-list\n");
		b.append(indent + "{\n");
		List<BBVariableDefinition> vars = list.settable_getVariables();
		if (!vars.isEmpty())
		{
			b.append(indent + "\tvariables:\n");
			int lp = 0;
			int last = vars.size() - 1;
			for (BBVariableDefinition var : vars)
			{
				b.append(indent + "\t\t\"" + var.getName() + "\" = \"" + fwdSlash(var.getValue()) + (lp == last ? "\";" : "\",") + "\n");
				lp++;
			}
		}
		
		List<BBStratumDirectory> dirs = list.settable_getStrataDirectories();
		if (!vars.isEmpty())
		{
			b.append(indent + "\tdirectories:\n");
			int lp = 0;
			int last = dirs.size() - 1;
			for (BBStratumDirectory dir : dirs)
			{
				b.append(indent + "\t\t\"" + dir.getStratumName() + "\" = \"" + fwdSlash(dir.getPath()) + (lp == last ? "\";" : "\",") + "\n");
				lp++;
			}
		}
		b.append(indent + "}\n");
		
		return b.toString().replace("\t", "    ");
	}

	private String fwdSlash(String path)
	{
		return path.replace('\\', '/');
	}
}