package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.runtime.api.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Slot")
public class BBSimpleSlot extends BBSimpleObject
{
	@XStreamConverter(SimpleReferenceConverter.class)
	private BBSimpleAttribute attribute;
	@XStreamConverter(SimpleReferenceConverter.class)
	private BBSimpleAttribute environmentAlias;
	private List<BBSimpleParameter> value;
	private transient Object valueObject;
	private transient boolean resolved;
	private transient ReflectiveAttribute reflectiveField;
	
	public BBSimpleSlot(BBSimpleElementRegistry registry, BBSimpleComponent component, DEComponent partType, DESlot complex)
	{
		// the environment alias must be in the component
		if (complex.getEnvironmentAlias() != null)
		{
			for (BBSimpleAttribute attr : component.getAttributes())
				if (attr.getComplex() == complex.getEnvironmentAlias(registry.getPerspective(), component.getComplex()))
				{
					environmentAlias = attr;
					break;
				}
		}
		else
		{
			value = new ArrayList<BBSimpleParameter>();
			for (DEParameter p : complex.getValue())
				if (p.getAttribute() != null)
				{
					BBSimpleAttribute simple =
						translateAttribute(component.getAttributes(), p.getAttribute(registry.getPerspective(), component.getComplex()));
					if (simple != null)
						value.add(new BBSimpleParameter(simple));
				}
				else
					value.add(new BBSimpleParameter(p.getLiteral()));					
		}

		// the attribute must be in the type of the part
		BBSimpleComponent partComp = registry.retrieveComponent(partType);
		for (BBSimpleAttribute attr : partComp.getAttributes())
		{
			if (attr.getComplex() == complex.getAttribute())
			{
				attribute = attr;
				break;
			}
		}
	}

	public BBSimpleSlot(BBSimpleElementRegistry registry, BBSimpleAttribute attribute, BBSimpleAttribute environmentAlias)
	{
		this.attribute = attribute;
		this.environmentAlias = environmentAlias;
	}

	public BBSimpleSlot(BBSimpleElementRegistry registry, BBSimpleSlot slot, Map<BBSimpleAttribute, BBSimpleAttribute> copied)
	{
		attribute = slot.attribute;
		if (slot.environmentAlias != null)
			environmentAlias = copied.get(slot.environmentAlias);
		if (slot.value != null)
		{
			value = new ArrayList<BBSimpleParameter>();
			for (BBSimpleParameter p : slot.value)
			{
				if (p.getLiteral() != null)
					value.add(new BBSimpleParameter(p.getLiteral()));
				else
					value.add(new BBSimpleParameter(copied.get(p.getAttribute())));
			}
		}
	}

	private BBSimpleAttribute translateAttribute(List<BBSimpleAttribute> attributes, DEAttribute attribute)
	{
		for (BBSimpleAttribute attr : attributes)
			if (attr.getComplex() == attribute)
				return attr;
		return null;
	}
	
	public void resolveImplementation(BBSimpleElementRegistry registry, Class<?> implClass, BBSimpleComponent element) throws BBImplementationInstantiationException
	{
		if (resolved)
			return;
		resolved = true;

		// get the "attribute" of the implementation class via reflection
		reflectiveField = new ReflectiveAttribute(attribute);
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public String getRawName()
	{
		return "";
	}

	public BBSimpleAttribute getAttribute()
	{
		return attribute;
	}

	public List<BBSimpleParameter> getValue()
	{
		return value;
	}

	public BBSimpleAttribute getEnvironmentAlias()
	{
		return environmentAlias;
	}

	public void remapAttributes(Map<BBSimpleAttribute, BBSimpleAttribute> redundant)
	{
		// only need to do this for the environment alias, as by now the values would have been pushed
		// into attributes at the top level
		if (environmentAlias != null && redundant.containsKey(environmentAlias))
			environmentAlias = redundant.get(environmentAlias);
	}

	public void setValue(Object obj, Attribute<? extends Object> value, BBSimpleElement element) throws BBRuntimeException
	{
	  reflectiveField.set(obj, value);
	}
}
