package com.hopstepjump.backbone.parser;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;

public class LoadListParser
{
	private Expect ex;

	public LoadListParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBLoadList parse()
	{
		final BBLoadList list = new BBLoadList();
		ex.
			literal("load-list").
			literal("{").
			guard("variables", new IAction() { public void act() { parseVariables(list); } }).
			guard("directories", new IAction() { public void act() { parseDirectories(list); } }).
			literal("}");
		return list;
	}
	
	private void parseVariables(final BBLoadList list)
	{
		ex.literal(":").oneOrMore(",",
				new StringMatch(
						new IAction()
						{
							public void act()
							{
								String name[] = {""};
								String value[] = {""};
								ex.string(name).literal("=").string(value);
								list.settable_getVariables().add(new BBVariableDefinition(name[0], value[0]));
							}
						})).literal(";");
	}

	private void parseDirectories(final BBLoadList list)
	{
		ex.literal(":").oneOrMore(",",
				new StringMatch(
						new IAction()
						{
							public void act()
							{
								String name[] = {""};
								String path[] = {""};
								ex.string(name).literal("=").string(path);
								list.settable_getStrataDirectories().add(new BBStratumDirectory(path[0], name[0]));
							}
						})).literal(";");
	}
}
