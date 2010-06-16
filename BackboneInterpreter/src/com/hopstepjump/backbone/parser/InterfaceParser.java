package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;

public class InterfaceParser
{
	private Expect ex;
	
	public InterfaceParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBInterface parse()
	{
		final String uuid[] = {null};
		final String name[] = {null};
		final List<String> resembles = new ArrayList<String>();
		final String replaces[] = {null};
		final String implementation[] = {null};
		ex.literal();
		ex.
			uuid(uuid, name);
		
		final BBInterface i = new BBInterface(uuid[0], name[0]);
		
		ex.
			oneOf(new LiteralMatch("implementation-class",
					new IAction() { public void act() { ex.literal().literal(implementation); } })).
			guard("resembles",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, resembles); } }).
			guard("replaces",
					new IAction() { public void act() { ex.uuid(replaces); } }).
			literal("{");
		ParserUtilities.parseAppliedStereotype(ex);
		ex.
			zeroOrMore(
					new LiteralMatch("delete-attributes",
							new IAction() { public void act() { parseDeletions(i.settable_getDeletedOperations()); } }),
					new LiteralMatch("replace-attributes",
							new IAction() { public void act() { parseReplacedOperations(i.settable_getReplacedOperations()); } }),
					new LiteralMatch("attributes",
						new IAction() { public void act() { parseAddedOperations(i.settable_getAddedOperations()); } })).
		  literal("}");
		
		return i;
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
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
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
		String uuid[] = {""};
		String name[] = {""};
		ex.
			uuid(uuid, name);
		final BBOperation operation = new BBOperation(uuid[0], name[0]);
		return operation;
	}

	private void parseDeletions(List<String> deletedUUIDs)
	{
		ex.literal().literal(":");
		ParserUtilities.parseUUIDs(ex, deletedUUIDs);
		ex.literal(";");
	}
}
