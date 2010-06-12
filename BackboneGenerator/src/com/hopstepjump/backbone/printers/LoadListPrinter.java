package com.hopstepjump.backbone.printers;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;

public class LoadListPrinter
{
	private BBLoadList list;
	/** how do we want to see the output? */
	private ReferenceHelper ref;
	
	public LoadListPrinter(BBLoadList list, BackbonePrinterMode mode)
	{
		this.list = list;
		this.ref = new ReferenceHelper(mode);
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