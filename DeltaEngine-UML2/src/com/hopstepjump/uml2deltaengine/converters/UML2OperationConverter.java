package com.hopstepjump.uml2deltaengine.converters;


/*public class UML2OperationConverter implements Converter
{
	public UML2OperationConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Operation ui = (UML2Operation) value;
		BBOperation i = new BBOperation();

		if (ui.getName().length() != 0)
			i.setName(ui.getName());
		i.setUuid(ui.getUuid());
		i.setAppliedStereotypes(UML2PortConverter.filterOutVisualElements(ui));
		
		context.convertAnother(i);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Operation.class;
	}
}
*/