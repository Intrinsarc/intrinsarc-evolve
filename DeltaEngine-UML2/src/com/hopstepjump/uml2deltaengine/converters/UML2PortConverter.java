package com.hopstepjump.uml2deltaengine.converters;


/*public class UML2PortConverter implements Converter
{
	public UML2PortConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Port port = (UML2Port) value;
		context.convertAnother(makePort(port.getUuid(), port));
	}
	
	public static BBPort makePort(String uuid, UML2Port ui)
	{
		BBPort i = new BBPort(uuid);
		
		// don't set a lower and upper if this is 1:1
		if (ui.getLowerBound() != 1 || ui.getUpperBound() != 1)
		{
			i.setLowerBound(ui.getLowerBound());
			i.setUpperBound(ui.getUpperBound());
		}
		if (ui.getName().length() != 0)
			i.setName(ui.getName());
		
		Set<DEInterface> provided = ui.getSetProvidedInterfaces();
		if (!provided.isEmpty())
		{
			i.settable_getSetProvidedInterfaces().addAll(provided);
			sort(i.settable_getSetProvidedInterfaces());
		}
		Set<DEInterface> required = ui.getSetRequiredInterfaces();
		if (!required.isEmpty())
		{
			i.settable_getSetRequiredInterfaces().addAll(required);
			sort(i.settable_getSetRequiredInterfaces());
		}
		i.setSuppressGeneration(ui.isSuppressGeneration());
		i.setAppliedStereotypes(filterOutVisualElements(ui));
		i.setBeanMain(ui.isBeanMain());
		i.setBeanNoName(ui.isBeanNoName());
		i.setPortKind(ui.getPortKind());
		i.setOrdered(ui.isOrdered());
		return i;
	}

	public static List<DEAppliedStereotype> filterOutVisualElements(DEObject ui)
	{
		List<DEAppliedStereotype> applied = new ArrayList<DEAppliedStereotype>();
		DEStratum home = ui.getHomeStratum();
		for (DEAppliedStereotype appl : ui.getAppliedStereotypes())
		{
			// is this descended from visual-effect?
			if (!isVisual(home, appl.getStereotype()))
				applied.add(appl);
		}
		return applied;
	}

	public static boolean isVisual(DEStratum perspective, DEComponent stereo)
	{
		if (stereo.getUuid().equals("visual-effect"))
			return true;
		for (DEElement el : stereo.getSuperElementClosure(perspective, false))
		{
			if (el.getUuid().equals("visual-effect"))
				return true;
		}
		return false;
	}

	private static void sort(List<DEInterface> ifaces)
	{
		Collections.sort(ifaces, new Comparator<DEInterface>()
				{
					public int compare(DEInterface o1, DEInterface o2)
					{
						return o1.getUuid().compareTo(o2.getUuid());
					}			
				});
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Port.class;
	}
}
*/