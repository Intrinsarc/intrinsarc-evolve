package com.hopstepjump.uml2deltaengine.converters;


/*public class UML2PartConverter implements Converter
{
	public UML2PartConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Part ui = (UML2Part) value;
		context.convertAnother(makePart(ui.getUuid(), ui));
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Part.class;
	}

	public static DEPart makePart(String uuid, UML2Part ui)
	{
		BBPart i = new BBPart(uuid);

		if (ui.getName().length() != 0)
			i.setName(ui.getName());
		i.setType(ui.getType());
		for (DESlot slot : ui.getSlots())		
		{
			if (slot.isAliased())
				i.settable_getSlots().add(new BBSlot(slot.getAttribute(), slot.getEnvironmentAlias()));
			else
			{
				// convert over the parameters
				List<DEParameter> params = new ArrayList<DEParameter>();
				for (DEParameter p : slot.getValue())
				{
					if (p.getAttribute() != null)
						params.add(new BBParameter(p.getAttribute()));
					else
						params.add(new BBParameter(p.getLiteral()));
				}
				i.settable_getSlots().add(new BBSlot(slot.getAttribute(), params));					
			}
		}
		if (!ui.getPortRemaps().isEmpty())
		for (DeltaPair pair : ui.getPortRemaps())
			i.settable_getPortRemaps().add(new BBPortRemap(pair.getUuid(), pair.getConstituent().asPort()));
		i.setAppliedStereotypes(UML2PortConverter.filterOutVisualElements(ui));
		return i;
	}
}
*/