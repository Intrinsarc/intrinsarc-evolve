package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.deltaengine.base.*;

public class BBSlot extends DESlot implements INode, Serializable
{
	private transient DEObject parent;
	private DEAttribute attribute;
	private DEAttribute environmentAlias;
	private List<DEParameter> value;
	private List<DEAppliedStereotype> appliedStereotypes;

	public BBSlot() {}
	
	public BBSlot(DEAttribute attribute, List<DEParameter> value)
	{
		this.attribute = attribute;
		this.value = value;
	}
	
	public BBSlot(DEAttribute attribute, DEAttribute environmentAlias)
	{
		this.attribute = attribute;
		this.environmentAlias = environmentAlias;
	}
	
  private Object readResolve()
  {
  	GlobalNodeRegistry.registry.addNode(this);
  	return this;
  }
  
  @Override
	public DEAttribute getAttribute()
	{
		return attribute;
	}

	@Override
	public DEAttribute getEnvironmentAlias()
	{
		return environmentAlias;
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
