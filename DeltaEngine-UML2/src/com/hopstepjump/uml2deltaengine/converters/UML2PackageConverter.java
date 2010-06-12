package com.hopstepjump.uml2deltaengine.converters;


/*public class UML2PackageConverter implements Converter
{
	public UML2PackageConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Stratum upkg = (UML2Stratum) value;
		BBStratum pkg = new BBStratum(upkg.getUuid());
		if (upkg.getName().length() != 0)
			pkg.setName(upkg.getName());

		// depends on
		if (!upkg.getRawDependsOn().isEmpty())
			pkg.settable_getRawDependsOn().addAll(upkg.getRawDependsOn());
		
		// sub-elements
		if (!upkg.getChildElements().isEmpty())
			pkg.settable_getElements().addAll(upkg.getChildElementsInDependencyOrder());
		
		pkg.setParentUuid(upkg.getHomeStratum().getUuid());
		pkg.setDestructive(upkg.isDestructive());
		pkg.setRelaxed(upkg.isRelaxed());

		context.convertAnother(pkg);
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Stratum.class;
	}
}
*/