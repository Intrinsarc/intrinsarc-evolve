package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;

public class ComponentParser
{
	private Expect ex;
	
	public ComponentParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBComponent parse()
	{
		final BBComponent c = new BBComponent();
		
		final String uuid[] = {null};
		boolean factory[] = {false};
		boolean placeholder[] = {false};
		boolean primitive[] = {false};
		boolean stereotype[] = {false};
		final List<String> resembles = new ArrayList<String>();
		final String replaces[] = {null};
		final String implementation[] = {null};
		ex.literal();
		ex.
			uuid(uuid).
			optionalLiteral("is-factory", factory).
			optionalLiteral("is-placeholder", placeholder).
			optionalLiteral("is-primitive", primitive).
			optionalLiteral("is-stereotype", stereotype).
			guard("implementation-class",
					new IAction() { public void act() { ex.literal(implementation); } }).
			guard("resembles",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, resembles); } }).
			guard("replaces",
					new IAction() { public void act() { ex.uuid(replaces); } }).
			literal("{");
		ParserUtilities.parseAppliedStereotype(ex);
		ex.
			zeroOrMore(
					new LiteralMatch("delete-attributes",
							new IAction() { public void act() { parseDeletions(c.settable_getDeletedAttributes()); } }),
					new LiteralMatch("replace-attributes",
							new IAction() { public void act() { parseReplacedAttributes(c.settable_getReplacedAttributes()); } }),
					new LiteralMatch("attributes",
						new IAction() { public void act() { parseAddedAttributes(c.settable_getAddedAttributes()); } }),
						
					new LiteralMatch("delete-parts",
						new IAction() { public void act() { parseDeletions(c.settable_getDeletedParts()); } }),
					new LiteralMatch("replace-parts",
							new IAction() { public void act() { parseReplacedParts(c.settable_getReplacedParts()); } }),
					new LiteralMatch("parts",
							new IAction() { public void act() { parseAddedParts(c.settable_getAddedParts()); } }),
					
					new LiteralMatch("delete-ports",
						new IAction() { public void act() { parseDeletions(c.settable_getDeletedPorts()); } }),
					new LiteralMatch("replace-ports",
							new IAction() { public void act() { parseReplacedPorts(c.settable_getReplacedPorts()); } }),
					new LiteralMatch("ports",
							new IAction() { public void act() { parseAddedPorts(c.settable_getAddedPorts()); } }),
					
					new LiteralMatch("delete-connectors",
						new IAction() { public void act() { parseDeletions(c.settable_getDeletedConnectors()); } }),
					new LiteralMatch("replace-connectors",
							new IAction() { public void act() { parseReplacedConnectors(c.settable_getReplacedConnectors()); } }),
					new LiteralMatch("connectors",
							new IAction() { public void act() { parseAddedConnectors(c.settable_getAddedConnectors()); } }),
					
					new LiteralMatch("delete-links",
						new IAction() { public void act() { parseDeletions(c.settable_getDeletedPortLinks()); } }),
					new LiteralMatch("replace-links",
							new IAction() { public void act() { parseReplacedLinks(c.settable_getReplacedPortLinks()); } }),
					new LiteralMatch("links",
							new IAction() { public void act() { parseAddedLinks(c.settable_getAddedPortLinks()); } })).
			literal("}");

		return c;
	}
	
	private void parseReplacedLinks(List<BBReplacedConnector> replacedLinks)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
							ParserUtilities.parseAppliedStereotype(ex);
							parseLink();
						}
					})).
		literal(";");	}

	private void parseAddedLinks(List<BBConnector> addedLinks)
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
								parseConnector();
							}
						})).
			literal(";");
	}
	
	private BBConnector parseLink()
	{
		final BBConnector connector = new BBConnector();
		String uuid[] = {""};
		String end1[] = {""};
		String end2[] = {""};
		ex.
			uuid(uuid).
			literal("joins").
			uuid(end1).
			literal("to").
			uuid(end2);
		return connector;
	}
	
	private void parseReplacedConnectors(List<BBReplacedConnector> replacedConnectors)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
							ParserUtilities.parseAppliedStereotype(ex);
							parseConnector();
						}
					})).
		literal(";");	}

	private void parseAddedConnectors(List<BBConnector> addedConnectors)
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
								parseConnector();
							}
						})).
			literal(";");
	}
	
	private BBConnector parseConnector()
	{
		final BBConnector connector = new BBConnector();
		String uuid[] = {""};
		ex.
			uuid(uuid).
			literal("joins");
		end();
		ex.literal("to");
		end();
		connector.setUuid(uuid[0]);
		return connector;
	}
	
	private void end()
	{
		String portUUID[] = {""};		
		final String partUUID[] = {""};
		ex.
			uuid(portUUID).
			guard("[",
				new IAction()
				{
					public void act()
					{
						ex.oneOf(
								new IntegerMatch(
										new IAction() { public void act() { ex.next(); } }),
								new LiteralMatch("+",
										new IAction() { public void act() { ex.literal(); } }));
						ex.literal("]");
					}
				}).
			guard("@",
				new IAction()
				{
					public void act()
					{
						ex.uuid(partUUID);						
					}
				});					
	}

	private void parseReplacedParts(List<BBReplacedPart> replacedParts)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
							ParserUtilities.parseAppliedStereotype(ex);
							parsePart();
						}
					})).
		literal(";");	}

	private void parseAddedParts(List<BBPart> addedParts)
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
								parsePart();
							}
						})).
			literal(";");
	}
	
	private BBPart parsePart()
	{
		final BBPart part= new BBPart();
		String uuid[] = {""};
		String type[] = {""};
		ex.
			uuid(uuid).
			literal(":").
			uuid(type).
			zeroOrMore(
					new NonSemiColonOrCommaMatch(
						new IAction() {
							public void act()
							{
								String partUUID[] = {""};
								ex.
									uuid(partUUID).
									guard("=",
										new IAction()
										{
											public void act()
											{
												parseParameters(new ArrayList<String>());
											}
										},
										// aliased
										new IAction()
										{
											public void act()
											{
												String aliasUUID[] = {""};
												ex.literal("(").uuid(aliasUUID).literal(")");
											}
										});										
							}
						}));
		part.setUuid(uuid[0]);
		return part;
	}
	
	private void parseReplacedPorts(List<BBReplacedPort> replacedPorts)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
							ParserUtilities.parseAppliedStereotype(ex);
							parsePort();
						}
					})).
		literal(";");	}

	private void parseAddedPorts(List<BBPort> addedPorts)
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
								parsePort();
							}
						})).
			literal(";");
	}
	
	private BBPort parsePort()
	{
		final BBPort port = new BBPort();
		String uuid[] = {""};
		final List<String> provides = new ArrayList<String>();
		final List<String> requires = new ArrayList<String>();
		
		ex.
			uuid(uuid).
			guard("provides",
					new IAction() {
						public void act()
						{
							parseInterfaces(provides);
						}
					}).
			guard("requires",
					new IAction() {
						public void act()
						{
							parseInterfaces(requires);
						}
					});
		
		port.setUuid(uuid[0]);
		return port;
	}
	

	private void parseInterfaces(final List<String> ifaces)
	{
		ex.
			oneOrMore(
				"&",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								String uuid[] = {""};
								ex.uuid(uuid);
								ifaces.add(uuid[0]);
							}
						}));
	}

	private void parseReplacedAttributes(List<BBReplacedAttribute> replacedAttributes)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							String uuid[] = {""};
							ex.uuid(uuid).literal("becomes");
							ParserUtilities.parseAppliedStereotype(ex);
							parseAttribute();
						}
					})).
		literal(";");	}

	private void parseAddedAttributes(List<BBAttribute> addedAttributes)
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
								parseAttribute();
							}
						})).
			literal(";");
	}
	
	private BBAttribute parseAttribute()
	{
		final BBAttribute attr = new BBAttribute();
		String uuid[] = {""};
		String type[] = {""};
		final boolean readOnly[] = {false};
		final boolean writeOnly[] = {false};
		ex.
			optionalLiteral("read-only", readOnly).
			optionalLiteral("write-only", writeOnly).
			uuid(uuid).
			literal(":").
			uuid(type).
			guard("=",
					new IAction() {
						public void act()
						{
							parseParameters(new ArrayList<String>());
						}
					});
		attr.setUuid(uuid[0]);
		return attr;
	}
	
	private void parseParameters(final List<String> parameters)
	{
		ex.
			guard("(",
				new IAction() {
					public void act()
					{
						ex.oneOrMore(
								",",
								new ParameterMatch(
										new IAction()
										{
											public void act()
											{
												parameters.add(ex.next().getText());
											}
										})).literal(")");
					}
				},
				new IAction() {
					public void act()
					{
						ex.oneOf(
								new ParameterMatch(
										new IAction()
										{
											public void act()
											{
												parameters.add(ex.next().getText());
											}
										}));
					}
				});
	}

	private void parseDeletions(List<String> deletedUUIDs)
	{
		ex.literal().literal(":");
		ParserUtilities.parseUUIDs(ex, deletedUUIDs);
		ex.literal(";");
	}		
}
