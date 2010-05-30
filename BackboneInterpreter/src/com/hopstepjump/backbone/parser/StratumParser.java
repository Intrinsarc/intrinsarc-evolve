package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.parserbase.*;

public class StratumParser
{
	private Expect ex;
	String stratumName[] = {null};
	private List<BBComponent> components = new ArrayList<BBComponent>();
	private List<BBInterface> interfaces = new ArrayList<BBInterface>();
	
	public StratumParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBStratum parse()
	{
		String uuid[] = {null};
		final String parentUuid[] = {null};
		final String parentName[] = {null};
		boolean relaxed[] = {false};
		boolean destructive[] = {false};
		final List<String> dependsOn = new ArrayList<String>();
		
		// the parser
		ex.
			literal("stratum").
			uuid(uuid, stratumName).
			guard("parent-stratum",
					new IAction() { public void act() { ex.uuid(parentUuid, parentName); } }). 
			optionalLiteral("is-relaxed", relaxed).
			optionalLiteral("is-destructive", destructive).
			guard("depends-on",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, dependsOn); } }).
			literal("{").
			inside(
					new IAction() { public void act() { parseElements(); } }).
			literal("}");
		return null;
	}
	
	protected void parseElements()
	{
		ex.zeroOrMore(
				new LiteralMatch("component",
						new IAction() { public void act() { new ComponentParser(ex).parse(); } }),
				new LiteralMatch("interface",
						new IAction() { public void act() { new InterfaceParser(ex).parse(); } }));
	}
}
