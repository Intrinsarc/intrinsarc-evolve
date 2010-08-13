package com.intrinsarc.backbone.nodes;

import java.io.*;
import java.util.*;

import com.intrinsarc.backbone.nodes.lazy.*;
import com.intrinsarc.deltaengine.base.*;

public class BBRequirementsFeatureLink extends DERequirementsFeatureLink implements INode, Serializable
{
  private transient DEObject parent;
  private String name;
  private String uuid;
	private SubfeatureKindEnum kind;
	private LazyObject<DERequirementsFeature> subfeature = new LazyObject<DERequirementsFeature>(DERequirementsFeature.class);
	private List<? extends DEAppliedStereotype> appliedStereotypes;
  
  public BBRequirementsFeatureLink(UuidReference reference)
  {
  	this.uuid = reference.getUuid();
  	this.name = reference.getName();
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

	public void setAppliedStereotypes(List<BBAppliedStereotype> appliedStereotypes)
	{
		this.appliedStereotypes = appliedStereotypes.isEmpty() ? null : appliedStereotypes;
	}

	@Override
	public List<? extends DEAppliedStereotype> getAppliedStereotypes()
	{
		return appliedStereotypes == null ? new ArrayList<DEAppliedStereotype>() : appliedStereotypes;
	}

	@Override
	public SubfeatureKindEnum getKind()
	{
		return kind;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSubfeature(UuidReference reference)
	{
		this.subfeature.setReference(reference);
	}
	
	@Override
	public DERequirementsFeature getSubfeature()
	{
		return subfeature.getObject();
	}
}
