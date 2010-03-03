package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
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
		final BBComponent c = new BBComponent();
		
		String uuid[] = {null};
		String name[] = {null};
		boolean factory[] = {false};
		boolean placeholder[] = {false};
		boolean primitive[] = {false};
		boolean stereotype[] = {false};
		ex.
			name(uuid, name).
			optionalLiteral("is-factory", factory).
			optionalLiteral("is-placeholder", placeholder).
			optionalLiteral("is-primitive", primitive).
			optionalLiteral("is-stereotype", stereotype).
			guard("implementation-class",
					new IAction() { public void act(Expect e, Token t) { } }).
			guard("resembles", null).
			guard("replaces", null).
			literal("{").
			zeroOrMore(
					new LiteralMatch("delete-attributes",
							new IAction() { public void act(Expect e, Token t) { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-attributes",
							new IAction() { public void act(Expect e, Token t) { parseReplacedAttributes(c.settable_getReplacedAttributes()); } }),
					new LiteralMatch("attributes",
						new IAction() { public void act(Expect e, Token t) { parseAddedAttributes(c.settable_getAddedAttributes()); } }),
						
					new LiteralMatch("delete-parts",
						new IAction() { public void act(Expect e, Token t) { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-parts", null),
					new LiteralMatch("parts", null),
					
					new LiteralMatch("delete-ports",
						new IAction() { public void act(Expect e, Token t) { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-ports", null),
					new LiteralMatch("ports", null),
					
					new LiteralMatch("delete-connectors",
						new IAction() { public void act(Expect e, Token t) { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-connectors", null),
					new LiteralMatch("connectors", null),
					
					new LiteralMatch("delete-applied-stereotypes",
						new IAction() { public void act(Expect e, Token t) { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-applied-stereotypes", null),
					new LiteralMatch("applied-stereotypes", null),
					
					new LiteralMatch("delete-links",
						new IAction() { public void act(Expect e, Token t) { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-links", null),
					new LiteralMatch("links", null)).
			literal("}");
		
	}

	protected void parseReplacedAttributes(List<BBReplacedAttribute> replacedAttributes)
	{
		BBReplacedAttribute attr = new BBReplacedAttribute();
	}

	protected void parseAddedAttributes(List<BBAttribute> addedAttributes)
	{
		ex.
			literal(":").
			oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{
							public void act(Expect e, Token t)
							{
								
							}})).
			literal(";");
	}

	private void parseDeletions(List<String> deletedUUIDs)
	{
		// TODO Auto-generated method stub
		
	}	
	
	protected void parseInterface()
	{
	}
}
