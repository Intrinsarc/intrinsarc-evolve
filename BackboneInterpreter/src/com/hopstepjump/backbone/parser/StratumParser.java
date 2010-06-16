package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.parserbase.*;

public class StratumParser
{
	private Expect ex;
	
	public StratumParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBStratum parse()
	{
		String uuid[] = {null};
		String name[] = {null};
		final String parentUuid[] = {null};
		boolean relaxed[] = {false};
		boolean destructive[] = {false};
		final List<String> dependsOn = new ArrayList<String>();
		
		// the parser
		ex.
			literal("stratum").
			uuid(uuid, name);
		
		BBStratum bb = new BBStratum(uuid[0], name[0]);
		final BBStratum stratum[]= new BBStratum[]{bb};

		ex.
			guard("parent",
					new IAction() { public void act() { ex.uuid(parentUuid); } }). 
			optionalLiteral("is-relaxed", relaxed).
			optionalLiteral("is-destructive", destructive).
			guard("depends-on",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, dependsOn); } }).
			literal("{").
			inside(
					new IAction() { public void act() { parseElements(stratum); } }).
			literal("}");
		
		// save away any values
		bb.setDestructive(destructive[0]);
		bb.setRelaxed(relaxed[0]);
		for (String dep : dependsOn)
			bb.settable_getRawDependsOn().add(GlobalNodeRegistry.registry.getNode(dep).asStratum());
		bb.setParentUuid(parentUuid[0]);
		
		return bb;
	}
	
	protected void parseElements(final BBStratum[] stratum)
	{
		ex.zeroOrMore(
				new LiteralMatch("feature",
						new IAction() { public void act() { stratum[0].settable_getElements().add(new FeatureParser(ex).parse()); } }),
				new LiteralMatch("component",
						new IAction() { public void act() { stratum[0].settable_getElements().add(new ComponentParser(ex).parse()); } }),
				new LiteralMatch("interface",
						new IAction() { public void act() { stratum[0].settable_getElements().add(new InterfaceParser(ex).parse()); } }));
	}
}
