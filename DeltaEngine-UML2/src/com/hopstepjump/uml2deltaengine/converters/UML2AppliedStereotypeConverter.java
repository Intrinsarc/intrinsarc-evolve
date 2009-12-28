package com.hopstepjump.uml2deltaengine.converters;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.uml2deltaengine.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class UML2AppliedStereotypeConverter implements Converter
{
	public UML2AppliedStereotypeConverter()
	{
	}
	
	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2AppliedStereotype us = (UML2AppliedStereotype) value;
		context.convertAnother(makeAppliedStereotype(us));
	}
	
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}
	
	public boolean canConvert(Class cls)
	{
		return cls == UML2AppliedStereotype.class;
	}

	public static DEAppliedStereotype makeAppliedStereotype(UML2AppliedStereotype ui)
	{
		BBAppliedStereotype s = new BBAppliedStereotype(ui);
		return s;
	}
}
