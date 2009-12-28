package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Operation")
public class BBOperation extends DEOperation implements INode, Serializable
{
  private transient DEObject parent;
  @XStreamAsAttribute
  private String name;
  @XStreamAsAttribute
  private String uuid = BBUidGenerator.newUuid(getClass());
	private List<DEAppliedStereotype> appliedStereotypes;
  
  public BBOperation()
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

  public void setName(String name)
  {
    this.name = name;
  }
  
  @Override
  public DEPort asPort()
  {
    return null;
  }

  @Override
  public String getName()
  {
    return name;
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
}
