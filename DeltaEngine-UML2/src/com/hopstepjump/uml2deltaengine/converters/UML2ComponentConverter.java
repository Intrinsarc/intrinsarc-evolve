package com.hopstepjump.uml2deltaengine.converters;


/*public class UML2ComponentConverter implements Converter
{
	public UML2ComponentConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Component uc = (UML2Component) value;
		BBComponent c = new BBComponent(uc.getUuid());

		c.setComponentKind(uc.getComponentKind());
		if (uc.getName().length() != 0)
			c.setRawName(uc.getName());
		
		if (!uc.getRawResembles().isEmpty())
			c.settable_getRawResembles().addAll(uc.getRawResembles());
		
		if (!uc.getSubstitutes().isEmpty())
			c.settable_getSubstitutes().addAll(uc.getSubstitutes());
		
		// handle the deltas
//		translateAttributes(uc, c);
		translatePorts(uc, c);
		translateParts(uc, c);
		translateConnectors(uc, c);
		translatePortLinks(uc, c);
		c.setRawRetired(uc.isRawRetired());
		c.setRawAbstract(uc.isRawAbstract());
		
		for (DEAppliedStereotype appl : uc.getReplacedAppliedStereotypes())
			if (!UML2PortConverter.isVisual(uc.getHomeStratum(), appl.getStereotype()))
				c.settable_getReplacedAppliedStereotypes().add(appl);
		
		context.convertAnother(c);
	}

	private void translateAttributes(UML2Component uc, BBComponent c)
	{
		Set<String> deleted = uc.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getDeleteObjects();
		if (!deleted.isEmpty())
			c.settable_getDeletedAttributes().addAll(deleted);
		Set<DeltaPair> pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getAddObjects();
		if (!pairs.isEmpty())
			c.settable_getAddedAttributes().addAll(translateAttributes(pairs));
		pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getReplaceObjects();
		if (!pairs.isEmpty())
			for (DeltaPair pair : pairs)
				c.settable_getReplacedAttributes().add(new BBReplacedAttribute(pair.getUuid(), pair.getConstituent()));
	}

	private void translateParts(UML2Component uc, BBComponent c)
	{
		Set<String> deleted = uc.getDeltas(ConstituentTypeEnum.DELTA_PART).getDeleteObjects();
		if (!deleted.isEmpty())
			c.settable_getDeletedParts().addAll(deleted);
		Set<DeltaPair> pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_PART).getAddObjects();
		if (!pairs.isEmpty())
			c.settable_getAddedParts().addAll(translateParts(pairs));
		pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_PART).getReplaceObjects();
		if (!pairs.isEmpty())
			for (DeltaPair pair : pairs)
				c.settable_getReplacedParts().add(new BBReplacedPart(pair.getUuid(), pair.getConstituent()));
	}

	private void translatePorts(UML2Component uc, BBComponent c)
	{
		Set<String> deleted = uc.getDeltas(ConstituentTypeEnum.DELTA_PORT).getDeleteObjects();
		if (!deleted.isEmpty())
			c.settable_getDeletedPorts().addAll(deleted);
		Set<DeltaPair> pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_PORT).getAddObjects();
		if (!pairs.isEmpty())
			c.settable_getAddedPorts().addAll(translatePorts(pairs));
		pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_PORT).getReplaceObjects();
		if (!pairs.isEmpty())
			for (DeltaPair pair : pairs)
				c.settable_getReplacedPorts().add(new BBReplacedPort(pair.getUuid(), pair.getConstituent()));
	}

	private void translateConnectors(UML2Component uc, BBComponent c)
	{
		Set<String> deleted = uc.getDeltas(ConstituentTypeEnum.DELTA_CONNECTOR).getDeleteObjects();
		if (!deleted.isEmpty())
			c.settable_getDeletedConnectors().addAll(deleted);
		Set<DeltaPair> pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_CONNECTOR).getAddObjects();
		if (!pairs.isEmpty())
			c.settable_getAddedConnectors().addAll(translateConnectors(pairs));
		pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_CONNECTOR).getReplaceObjects();
		if (!pairs.isEmpty())
			for (DeltaPair pair : pairs)
				c.settable_getReplacedConnectors().add(new BBReplacedConnector(pair.getUuid(), pair.getConstituent()));
	}

	private void translatePortLinks(UML2Component uc, BBComponent c)
	{
		Set<String> deleted = uc.getDeltas(ConstituentTypeEnum.DELTA_PORT_LINK).getDeleteObjects();
		if (!deleted.isEmpty())
			c.settable_getDeletedPortLinks().addAll(deleted);
		Set<DeltaPair> pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_PORT_LINK).getAddObjects();
		if (!pairs.isEmpty())
			c.settable_getAddedPortLinks().addAll(translateConnectors(pairs));
		pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_PORT_LINK).getReplaceObjects();
		if (!pairs.isEmpty())
			for (DeltaPair pair : pairs)
				c.settable_getReplacedPortLinks().add(new BBReplacedConnector(pair.getUuid(), pair.getConstituent()));
	}

	private Collection<DEAttribute> translateAttributes(Set<DeltaPair> attributes)
	{
		List<DEAttribute> translated = new ArrayList<DEAttribute>();
		for (DeltaPair pair : attributes)
			translated.add(pair.getConstituent().asAttribute());
		return translated;
	}

	private Collection<DEPort> translatePorts(Set<DeltaPair> ports)
	{
		List<DEPort> translated = new ArrayList<DEPort>();
		for (DeltaPair pair : ports)
			translated.add(pair.getConstituent().asPort());
		return translated;
	}

	private Collection<DEPart> translateParts(Set<DeltaPair> parts)
	{
		List<DEPart> translated = new ArrayList<DEPart>();
		for (DeltaPair pair : parts)
			translated.add(pair.getConstituent().asPart());
		return translated;
	}

	private Collection<DEConnector> translateConnectors(Set<DeltaPair> connectors)
	{
		List<DEConnector> translated = new ArrayList<DEConnector>();
		for (DeltaPair pair : connectors)
			translated.add(pair.getConstituent().asConnector());
		return translated;
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Component.class;
	}
}
*/