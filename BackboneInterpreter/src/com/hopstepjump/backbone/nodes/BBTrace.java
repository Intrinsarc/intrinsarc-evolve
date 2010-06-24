package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.deltaengine.base.*;

public class BBTrace extends DETrace implements INode, Serializable
{
  private transient DEObject parent;
  private String uuid = BBUidGenerator.newUuid(getClass());
	private DERequirementsFeature target;
	private List<DEAppliedStereotype> appliedStereotypes;
  
  public BBTrace(String uuid)
  {
  	this.uuid = uuid;
  	GlobalNodeRegistry.registry.addNode(this);
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
	public DERequirementsFeature getTarget()
	{
		return target;
	}

	@Override
	public String getName()
	{
		return null;
	}	
}
