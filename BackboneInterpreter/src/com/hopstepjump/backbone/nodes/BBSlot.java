package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.converters.BBXStreamConverters.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Slot")
public class BBSlot extends DESlot implements INode, Serializable
{
	private transient DEObject parent;
  @XStreamConverter(AttributeReferenceConverter.class)
	private DEAttribute attribute[];
  @XStreamConverter(AttributeReferenceConverter.class)
	private DEAttribute environmentAlias[];
	private List<DEParameter> value;
	private List<DEAppliedStereotype> appliedStereotypes;

	public BBSlot() {}
	
	public BBSlot(DEAttribute attribute, List<DEParameter> value)
	{
		this.attribute = new DEAttribute[]{attribute};
		this.value = value;
	}
	
	public BBSlot(DEAttribute attribute, DEAttribute environmentAlias)
	{
		this.attribute = new DEAttribute[]{attribute};
		this.environmentAlias = new DEAttribute[]{environmentAlias};
	}
	
  private Object readResolve()
  {
  	GlobalNodeRegistry.registry.addNode(this);
  	return this;
  }
  
  @Override
	public DEAttribute getAttribute()
	{
		if (attribute == null)
			return null;
		return attribute[0];
	}

	@Override
	public DEAttribute getEnvironmentAlias()
	{
		if (environmentAlias == null)
			return null;
		return environmentAlias[0];
	}

	@Override
	public List<DEParameter> getValue()
	{
		return value;
	}

	@Override
	public boolean isAliased()
	{
		return getEnvironmentAlias() != null;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@Override
	public DEObject getParent()
	{
		return parent;
	}

	@Override
	public Object getRepositoryObject()
	{
		return this;
	}

	@Override
	public String getUuid()
	{
		return "";
	}

	public void setParent(DEObject parent)
	{
		this.parent = parent;
	}

	public void setAppliedStereotypes(List<DEAppliedStereotype> appliedStereotypes)
	{
		this.appliedStereotypes = appliedStereotypes.isEmpty() ? null : appliedStereotypes;
	}

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes == null ? new ArrayList<DEAppliedStereotype>() : appliedStereotypes;
	}	
}
