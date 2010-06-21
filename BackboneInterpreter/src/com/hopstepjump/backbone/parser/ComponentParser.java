package com.hopstepjump.backbone.parser;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class ComponentParser
{
	private Expect ex;
	
	public ComponentParser(Expect ex)
	{
		this.ex = ex;
	}
	
	public BBComponent parse()
	{
		final UUIDReference uuid = new UUIDReference();
		boolean normal[] = {false};
		boolean factory[] = {false};
		boolean placeholder[] = {false};
		boolean primitive[] = {false};
		boolean stereotype[] = {false};
		boolean bean[] = {false};
		final String implementation[] = {null};
		ex.literal("component");
		ex.
			uuid(uuid);
		
		final BBComponent c = new BBComponent(uuid);
		
		ex.
			optionalLiteral("is-normal", normal).
			optionalLiteral("is-factory", factory).
			optionalLiteral("is-placeholder", placeholder).
			optionalLiteral("is-primitive", primitive).
			optionalLiteral("is-stereotype", stereotype).
			optionalLiteral("is-bean", bean).
			guard("implementation-class",
					new IAction() { public void act() { ex.literal(implementation); } }).
			guard("resembles",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, c.settable_getRawResembles()); } }).
			guard("replaces",
					new IAction() { public void act() { ParserUtilities.parseUUIDs(ex, c.settable_getReplaces()); } }).
			literal("{");
		
		// handle stereotypes
		List<BBAppliedStereotype> stereos = ParserUtilities.parseAppliedStereotype(ex);
		c.settable_getReplacedAppliedStereotypes().addAll(stereos);
		
		c.setComponentKind(ComponentKindEnum.NORMAL);
		if (primitive[0])
			c.setComponentKind(ComponentKindEnum.PRIMITIVE);
		if (stereotype[0])
			c.setComponentKind(ComponentKindEnum.STEREOTYPE);

		// possibly add a stereotype
		if (factory[0] || normal[0] || placeholder[0] || bean[0] || c.settable_getRawResembles().isEmpty() || implementation[0] != null)
		{
			BBAppliedStereotype stereo;
			if (stereos.isEmpty())
			{
				stereo = new BBAppliedStereotype();
				stereo.setStereotype(new UUIDReference("component"));
				c.settable_getReplacedAppliedStereotypes().add(stereo);
			}
			else
				stereo = stereos.get(0);

			if (factory[0])
				addBooleanStereotypeProperty(stereo, DEComponent.FACTORY_STEREOTYPE_PROPERTY, true);
			if (placeholder[0])
				addBooleanStereotypeProperty(stereo, DEComponent.PLACEHOLDER_STEREOTYPE_PROPERTY, true);
			if (factory[0])
				addBooleanStereotypeProperty(stereo, DEComponent.BEAN_STEREOTYPE_PROPERTY, true);
			if (implementation[0] != null)
				addStringStereotypeProperty(stereo, DEComponent.IMPLEMENTATION_STEREOTYPE_PROPERTY, implementation[0]);			
		}
		
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
					
					new LiteralMatch("delete-port-links",
						new IAction() { public void act() { parseDeletions(c.settable_getDeletedPortLinks()); } }),
					new LiteralMatch("replace-port-links",
							new IAction() { public void act() { parseReplacedLinks(c.settable_getReplacedPortLinks()); } }),
					new LiteralMatch("port-links",
							new IAction() { public void act() { parseAddedLinks(c.settable_getAddedPortLinks()); } })).
			literal("}");

		return c;
	}
	
	private void addBooleanStereotypeProperty(BBAppliedStereotype stereo, String attrUUID, boolean value)
	{
		stereo.settable_getProperties().put(
				GlobalNodeRegistry.registry.getNode(attrUUID, DEAttribute.class),
				value ? "true" : "false");
	}

	private void addStringStereotypeProperty(BBAppliedStereotype stereo, String attrUUID, String value)
	{
		stereo.settable_getProperties().put(
				GlobalNodeRegistry.registry.getNode(attrUUID, DEAttribute.class),
				value);
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
							UUIDReference reference = new UUIDReference();
							ex.uuid(reference).literal("becomes");
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
		UUIDReference reference = new UUIDReference();
		UUIDReference refEnd1 = new UUIDReference();
		UUIDReference refEnd2 = new UUIDReference();
		ex.
			uuid(reference);
		
		ex.
			literal("joins").
			uuid(refEnd1).
			literal("to").
			uuid(refEnd2);
		return null;
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
							UUIDReference reference = new UUIDReference();
							ex.uuid(reference).literal("becomes");
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
		UUIDReference reference = new UUIDReference();
		ex.
			uuid(reference);
		
		ex.
			literal("joins");
		end();
		ex.literal("to");
		end();
		return null;
	}
	
	private void end()
	{
		UUIDReference portRef = new UUIDReference();
		final UUIDReference partRef = new UUIDReference();
		ex.
			uuid(portRef).
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
						ex.uuid(partRef);						
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
							UUIDReference reference = new UUIDReference();
							ex.uuid(reference).literal("becomes");
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
		UUIDReference reference = new UUIDReference();
		UUIDReference typeRef = new UUIDReference();
		ex.
			uuid(reference);
		
		ex.
			literal(":").
			uuid(typeRef).
			zeroOrMore(
					new NonSemiColonOrCommaMatch(
						new IAction() {
							public void act()
							{
								UUIDReference partRef = new UUIDReference();
								ex.
									uuid(partRef).
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
												UUIDReference aliasRef = new UUIDReference();
												ex.literal("(").uuid(aliasRef).literal(")");
											}
										});										
							}
						}));
		return null;
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
							UUIDReference reference = new UUIDReference();
							ex.uuid(reference).literal("becomes");
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
		UUIDReference reference = new UUIDReference();
		final LazyObjects<DEInterface> provides = new LazyObjects<DEInterface>(DEInterface.class);
		final LazyObjects<DEInterface> requires = new LazyObjects<DEInterface>(DEInterface.class);
		
		boolean create[] = {false};
		ex.
			uuid(reference);

		ex.
			optionalLiteral("is-create-port", create).
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
		
		return null;
	}
	

	private void parseInterfaces(final LazyObjects<DEInterface> ifaces)
	{
		ex.
			oneOrMore(
				"&",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								UUIDReference reference = new UUIDReference();
								ex.uuid(reference);
								ifaces.addReference(reference);
							}
						}));
	}

	private void parseReplacedAttributes(final List<BBReplacedAttribute> replacedAttributes)
	{
		ex.literal().literal(":").
		oneOrMore(
			",",
			new LiteralMatch(
					new IAction()
					{
						public void act()
						{
							UUIDReference ref = new UUIDReference();
							ex.uuid(ref).literal("becomes");
							replacedAttributes.add(new BBReplacedAttribute(ref, parseAttribute()));
						}
					})).
		literal(";");
	}

	private void parseAddedAttributes(final List<BBAttribute> addedAttributes)
	{
		ex.literal().literal(":").
			oneOrMore(
				",",
				new LiteralMatch(
						new IAction()
						{
							public void act()
							{
								addedAttributes.add(parseAttribute());
							}
						})).
			literal(";");
	}
	
	private BBAttribute parseAttribute()
	{
		UUIDReference ref = new UUIDReference();
		UUIDReference typeRef = new UUIDReference();
		final boolean readOnly[] = {false};
		final boolean writeOnly[] = {false};
		ex.
			optionalLiteral("read-only", readOnly).
			optionalLiteral("write-only", writeOnly).
			uuid(ref);
		
		final BBAttribute attr = new BBAttribute(ref.getUUID());
		
		attr.setAppliedStereotypes(ParserUtilities.parseAppliedStereotype(ex));
		ex.
			literal(":").
			uuid(typeRef).
			guard("=",
					new IAction() {
						public void act()
						{
							attr.setDefaultValue(parseParameters(new ArrayList<String>()));
						}
					});
		attr.setType(typeRef);
		
		return attr;
	}
	
	private List<DEParameter> parseParameters(final List<String> parameters)
	{
		final List<DEParameter> params = new ArrayList<DEParameter>();
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
		return params;
	}

	private void parseDeletions(List<String> deletedUUIDs)
	{
		ex.literal().literal(":");
		ParserUtilities.parseUUIDs(ex, deletedUUIDs);
		ex.literal(";");
	}		
}
