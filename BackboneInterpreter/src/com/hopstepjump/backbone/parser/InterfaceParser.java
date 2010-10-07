package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class InterfaceParser
{
	private Expect ex;
	
	public InterfaceParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBInterface parse()
	{
		UuidReference ref = new UuidReference();
		final String implementation[] = {null};
		ex.literal();
		ex.
			uuid(ref);
		
		final BBInterface i = new BBInterface(ref);
		
		ex.
			guard("implementation-class",
					new IAction() { public void act() { ex.literal(implementation); } }).
			guard("resembles",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, i.settable_getRawResembles()); } }).
			guard("replaces",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, i.settable_getRawSubstitutes()); } }).
			literal("{");
		
		// handle stereotypes
		List<BBAppliedStereotype> stereos = ParserUtilities.parseAppliedStereotype(ex);
		i.settable_getReplacedAppliedStereotypes().addAll(stereos);
		
		ex.
			zeroOrMore(
					new LiteralMatch("delete-attributes",
							new IAction() { public void act() { parseDeletions(i.settable_getDeletedOperations()); } }),
					new LiteralMatch("replace-attributes",
							new IAction() { public void act() { parseReplacedOperations(i.settable_getReplacedOperations()); } }),
					new LiteralMatch("attributes",
						new IAction() { public void act() { parseAddedOperations(i.settable_getAddedOperations()); } })).
		  literal("}");
		
		// possibly add a stereotype
		if (i.settable_getRawResembles().isEmpty() || implementation[0] != null)
		{
			BBAppliedStereotype stereo;
			if (stereos.isEmpty())
			{
				stereo = new BBAppliedStereotype();
				stereo.setStereotype(new UuidReference("interface"));
				i.settable_getReplacedAppliedStereotypes().add(stereo);
			}
			else
				stereo = stereos.get(0);

			if (implementation[0] != null)
				addStringStereotypeProperty(stereo, DEComponent.IMPLEMENTATION_STEREOTYPE_PROPERTY, implementation[0]);			
		}

		return i;
	}
	
	private void addStringStereotypeProperty(BBAppliedStereotype stereo, String attrUUID, String value)
	{
		stereo.settable_getLazyProperties().put(
				new LazyObject<DEAttribute>(DEAttribute.class, new UuidReference(attrUUID)),
				value);
	}

	private void parseReplacedOperations(List<BBReplacedOperation> replacedOperations)
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
							parseOperation();
						}
					})).
		literal(";");	}

	private void parseAddedOperations(List<BBOperation> addedOperations)
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
								parseOperation();
							}
						})).
			literal(";");
	}
	
	private BBOperation parseOperation()
	{
		UuidReference ref = new UuidReference();
		ex.
			uuid(ref);
		final BBOperation operation = new BBOperation(ref);
		return operation;
	}

	private void parseDeletions(List<String> deletedUUIDs)
	{
		ex.literal().literal(":");
		ParserUtilities.parseUUIDs(ex, deletedUUIDs);
		ex.literal(";");
	}
}
