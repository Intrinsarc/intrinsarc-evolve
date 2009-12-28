package com.hopstepjump.uml2deltaengine.converters;

import java.util.*;

import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.uml2deltaengine.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class UML2AttributeConverter implements Converter
{
	public UML2AttributeConverter()
	{
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context)
	{
		UML2Attribute ui = (UML2Attribute) value;
		context.convertAnother(makeAttribute(ui.getUuid(), ui));
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
	{
		return null;
	}

	public boolean canConvert(Class cls)
	{
		return cls == UML2Attribute.class;
	}

	public static DEAttribute makeAttribute(String uuid, UML2Attribute ui)
	{
		BBAttribute i = new BBAttribute(uuid);

		if (ui.getName().length() != 0)
			i.setName(ui.getName());
		i.setType(ui.getType());
		
		if (!ui.getDefaultValue().isEmpty())
		{
			// convert over the parameters
			List<DEParameter> params = new ArrayList<DEParameter>();
			for (DEParameter p : ui.getDefaultValue())
			{
				if (p.getAttribute() != null)
					params.add(new BBParameter(p.getAttribute()));
				else
					params.add(new BBParameter(p.getLiteral()));
			}
			i.setDefaultValue(params);
		}
		i.setAppliedStereotypes(UML2PortConverter.filterOutVisualElements(ui));
		i.setWriteOnly(ui.isWriteOnly());
		i.setReadOnly(ui.isReadOnly());
		i.setSuppressGeneration(ui.isSuppressGeneration());
		return i;
	}
}
