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
		final BBRequirementsFeature f = new BBRequirementsFeature();
		
		final String uuid[] = {null};
		final List<String> resembles = new ArrayList<String>();
		final String replaces[] = {null};
		ex.literal();
		ex.
			uuid(uuid).
			guard("resembles",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, resembles); } }).
			guard("replaces",
					new IAction() { public void act() { ex.uuid(replaces); } }).
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
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
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
		final BBRequirementsFeatureLink operation = new BBRequirementsFeatureLink();
		String uuid[] = {""};
		final SubfeatureKindEnum kind[] = {null};
		ex.
			uuid(uuid).
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
