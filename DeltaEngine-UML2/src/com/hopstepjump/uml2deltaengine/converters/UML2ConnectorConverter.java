package com.hopstepjump.uml2deltaengine.converters;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.uml2deltaengine.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

/*public class UML2ConnectorConverter implements Converter
{
	public UML2ConnectorConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Connector ui = (UML2Connector) value;		
		context.convertAnother(makeConnector(ui.getUuid(), ui));
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Connector.class;
	}

	public static DEConnector makeConnector(String uuid, UML2Connector ui)
	{
		BBConnector i = new BBConnector(uuid);

		if (ui.getName().length() != 0)
			i.setName(ui.getName());
		
		i.setFromPort(ui.getOriginalPort(0));
		i.setFromPart(ui.getOriginalPart(0));
		i.setToPort(ui.getOriginalPort(1));
		i.setToPart(ui.getOriginalPart(1));
		
		i.setFromTakeNext(ui.getTakeNext(0));
		i.setToTakeNext(ui.getTakeNext(1));
		i.setFromIndex(ui.getIndex(0));
		i.setToIndex(ui.getIndex(1));
		
		i.setDelegate(ui.isDelegate());
		i.setAppliedStereotypes(UML2PortConverter.filterOutVisualElements(ui));
		return i;
	}
}
*/