package com.hopstepjump.uml2deltaengine.converters;


/*public class UML2InterfaceConverter implements Converter
{
	public UML2InterfaceConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Interface ui = (UML2Interface) value;
		BBInterface i = new BBInterface(ui.getUuid());

		if (ui.getName().length() != 0)
			i.setRawName(ui.getName());
		
		if (!ui.getRawResembles().isEmpty())
			i.settable_getRawResembles().addAll(ui.getRawResembles());
		
		if (!ui.getSubstitutes().isEmpty())
			i.settable_getRawSubstitutes().addAll(ui.getSubstitutes());
		
		translateOperations(ui, i);

		for (DEAppliedStereotype appl : ui.getReplacedAppliedStereotypes())
			if (!UML2PortConverter.isVisual(ui.getHomeStratum(), appl.getStereotype()))
				i.settable_getReplacedAppliedStereotypes().add(appl);
		i.setRawRetired(ui.isRawRetired());
		
		context.convertAnother(i);
	}
	
	private void translateOperations(UML2Interface uc, BBInterface c)
	{
		Set<String> deleted = uc.getDeltas(ConstituentTypeEnum.DELTA_OPERATION).getDeleteObjects();
		if (!deleted.isEmpty())
			c.settable_getDeletedOperations().addAll(deleted);
		Set<DeltaPair> pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_OPERATION).getAddObjects();
		if (!pairs.isEmpty())
			c.settable_getAddedOperations().addAll(translateOperations(pairs));
		pairs = uc.getDeltas(ConstituentTypeEnum.DELTA_OPERATION).getReplaceObjects();
		if (!pairs.isEmpty())
			for (DeltaPair pair : pairs)
				c.settable_getReplacedOperations().add(new BBReplacedOperation(pair.getUuid(), pair.getConstituent()));
	}

	private Collection<DEOperation> translateOperations(Set<DeltaPair> operations)
	{
		List<DEOperation> translated = new ArrayList<DEOperation>();
		for (DeltaPair pair : operations)
			translated.add(pair.getConstituent().asOperation());
		return translated;
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Interface.class;
	}
}
*/