package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

public class BBTrace extends DETrace implements INode, Serializable
{
  private transient DEObject parent;
  @XStreamAsAttribute
  private String uuid = BBUidGenerator.newUuid(getClass());
	private DEElement target;
	private List<DEAppliedStereotype> appliedStereotypes;
  
  public BBTrace()
  {
  }
  
  private Object readResolve()
  {
  	GlobalNodeRegistry.registry.addNode(this);
  	return this;
  }
  
  public void setUuid(String uuid)
  {
    this.uuid = uuid;
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
    return uuid;
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

	@Override
	public DEElement getTarget()
	{
		return target;
	}

	@Override
	public String getName()
	{
		return null;
	}	
}
