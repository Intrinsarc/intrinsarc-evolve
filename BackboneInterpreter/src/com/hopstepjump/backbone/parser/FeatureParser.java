package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class FeatureParser
{
	private Expect ex;
	
	public FeatureParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBRequirementsFeature parse()
	{
		UuidReference ref = new UuidReference();

		ex.literal();
		ex.
			uuid(ref);
		
		final BBRequirementsFeature f = new BBRequirementsFeature(ref);

		ex.
			guard("resembles",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, f.settable_getRawResembles()); } }).
			guard("replaces",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, f.settable_getRawReplaces()); } }).
			literal("{");
		ParserUtilities.parseAppliedStereotype(ex);
		ex.
			zeroOrMore(
					new LiteralMatch("delete-subfeatures",
							new IAction() { public void act() { parseDeletions(f.settable_getDeletedSubfeatures()); } }),
					new LiteralMatch("replace-subfeatures",
							new IAction() { public void act() { parseReplacedSubfeatures(f.settable_getReplacedSubfeatures()); } }),
					new LiteralMatch("subfeatures",
						new IAction() { public void act() { parseAddedSubfeatures(f.settable_getAddedSubfeatures()); } })).
		  literal("}");
		
		return f;
	}

	private void parseReplacedSubfeatures(List<BBReplacedRequirementsFeatureLink> replacedSubfeatures)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							ParserUtilities.parseAppliedStereotype(ex);
							UuidReference ref = new UuidReference();
							ex.uuid(ref).literal("becomes");
							parseSubfeature();
						}
					})).
		literal(";");	}

	private void parseAddedSubfeatures(List<DERequirementsFeatureLink> addedSubfeatures)
	{
		ex.literal().literal(":").
			oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								ParserUtilities.parseAppliedStereotype(ex);
								parseSubfeature();
							}
						})).
			literal(";");
	}
	
	private BBRequirementsFeatureLink parseSubfeature()
	{
		UuidReference ref = new UuidReference();
		final SubfeatureKindEnum kind[] = {null};
		ex.
			uuid(ref);
		
		final BBRequirementsFeatureLink operation = new BBRequirementsFeatureLink(ref);
		
		ex.
			oneOf(
					new LiteralMatch("is-mandatory",
							new IAction() { public void act() { kind[0] = SubfeatureKindEnum.MANDATORY; } }),
					new LiteralMatch("is-optional",
							new IAction() { public void act() { kind[0] = SubfeatureKindEnum.OPTIONAL; } }),
					new LiteralMatch("is-one-of",
							new IAction() { public void act() { kind[0] = SubfeatureKindEnum.ONE_OF; } }),
					new LiteralMatch("is-one-or-more",
							new IAction() { public void act() { kind[0] = SubfeatureKindEnum.ONE_OR_MORE; } })							
					).literal();

		return operation;
	}

	private void parseDeletions(List<String> deletedUUIDs)
	{
		ex.literal().literal(":");
		ParserUtilities.parseUUIDs(ex, deletedUUIDs);
		ex.literal(";");
	}
}
