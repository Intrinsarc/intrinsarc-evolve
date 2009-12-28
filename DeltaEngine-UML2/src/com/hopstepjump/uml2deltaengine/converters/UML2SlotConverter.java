package com.hopstepjump.uml2deltaengine.converters;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.uml2deltaengine.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class UML2SlotConverter implements Converter
{
	public UML2SlotConverter()
	{
	}
	
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Slot us = (UML2Slot) value;
		BBSlot s;
		if (us.isAliased())
			s = new BBSlot(us.getAttribute(), us.getEnvironmentAlias());
		else
			s = new BBSlot(us.getAttribute(), us.getValue());
		s.setAppliedStereotypes(UML2PortConverter.filterOutVisualElements(us));
		
		context.convertAnother(s);
	}
	
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}
	
	public boolean canConvert(Class cls)
	{
		return cls == UML2Slot.class;
	}
}
