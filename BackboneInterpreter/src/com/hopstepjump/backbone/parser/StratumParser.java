package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.parserbase.*;

public class StratumParser
{
	private Expect ex;
	private List<BBComponent> components = new ArrayList<BBComponent>();
	private List<BBInterface> interfaces = new ArrayList<BBInterface>();
	
	public StratumParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBStratum parse()
	{
		String uuid[] = {null};
		String name[] = {null};
		final String parentUuid[] = {null};
		final String parentName[] = {null};
		boolean relaxed[] = {false};
		boolean destructive[] = {false};
		final List<String> dependsOn = new ArrayList<String>();
		
		// the parser
		ex.
			literal("stratum").
			name(uuid, name).
			guard("parent-stratum",
					new IAction() { public void act(Expect e, Token t) { ex.name(parentUuid, parentName); } }). 
			optionalLiteral("is-relaxed", relaxed).
			optionalLiteral("is-destructive", destructive).
			guard("depends-on",
					new IAction() { public void act(Expect e, Token t) { parseNames(dependsOn); } }).
			literal("{").
			inside(
					new IAction() { public void act(Expect e, Token t) { parseElements(); } }).
			literal("}");
		return null;
	}
	
	private void parseNames(final List<String> dependsOn)
	{
		ex.oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{ public void act(Expect e, Token t) { dependsOn.add(e.makeName("global", t.getText())); } }));		
	}
	
	protected void parseElements()
	{
		ex.zeroOrMore(
				new LiteralMatch("component",
						new IAction() { public void act(Expect e, Token t) { parseComponent(); } }),
				new LiteralMatch("interface",
						new IAction() { public void act(Expect e, Token t) { parseInterface(); } }));
	}

	protected void parseComponent()
	{
		String uuid[] = {null};
		String name[] = {null};
		boolean factory[] = {false};
		ex.
			name(uuid, name).
			optionalLiteral("is-factory", factory).
			guard("resembles", null).
			guard("replaces", null).
			literal("{").
			zeroOrMore(
					new LiteralMatch("delete-attributes", null),
					new LiteralMatch("replace-attributes", null),
					new LiteralMatch("attributes", null),
					new LiteralMatch("delete-parts", null),
					new LiteralMatch("replace-parts", null),
					new LiteralMatch("parts", null),
					new LiteralMatch("delete-ports", null),
					new LiteralMatch("replace-ports", null),
					new LiteralMatch("ports", null),
					new LiteralMatch("delete-connectors", null),
					new LiteralMatch("replace-connectors", null),
					new LiteralMatch("connectors", null),
					new LiteralMatch("delete-links", null),
					new LiteralMatch("replace-links", null),
					new LiteralMatch("links", null)).
			literal("}");
		
	}

	protected void parseInterface()
	{
	}
}
