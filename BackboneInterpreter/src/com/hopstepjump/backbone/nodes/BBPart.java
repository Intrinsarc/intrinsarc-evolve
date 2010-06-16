package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;

public class BBPart extends DEPart implements INode, Serializable
{
  private DEObject parent;
  private String name;
  private String uuid = BBUidGenerator.newUuid(getClass());
  private DEComponent[] type = new DEComponent[1];
  private List<DESlot> slots;
  private List<BBPortRemap> remaps;
	private List<DEAppliedStereotype> appliedStereotypes;
	private transient boolean synthetic;
	private transient boolean pullUp;

  public BBPart(String uuid)
  {
  	this.uuid = uuid;
  	GlobalNodeRegistry.registry.addNode(this);
  }

  public BBPart(String uuid, boolean synthetic, boolean pullUp)
  {
  	this.uuid = uuid;
		this.synthetic = synthetic;
		this.pullUp = pullUp;
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

  public DEComponent getType()
  {
    return type[0];
  }

  public void setType(DEComponent type)
  {
    this.type[0] = type;
  }

  public void setUuid(String uuid)
  {
    this.uuid = uuid;
  }

  public void setName(String name)
  {
    this.name = name;
  }
  
  public List<DESlot> getSlots()
  {
  	if (slots == null)
  		return new ArrayList<DESlot>();
  	return slots;
  }

  public List<DESlot> settable_getSlots()
  {
    if (slots == null)
      slots = new ArrayList<DESlot>();
    return slots;
  }

	@Override
	public Set<DeltaPair> getPortRemaps()
	{
		if (remaps == null)
			return new HashSet<DeltaPair>();
		Set<DeltaPair> pairs = new HashSet<DeltaPair>();
		for (BBPortRemap remap : remaps)
			pairs.add(new DeltaPair(remap.getUuid(), remap.getPort()));
		return pairs;
	}

	public List<BBPortRemap> settable_getPortRemaps()
	{
		if (remaps == null)
			remaps = new ArrayList<BBPortRemap>();
		return remaps;
	}
	
	@Override
	public boolean isSynthetic()
	{
		return synthetic;
	}
	
	@Override
	public boolean isPullUp()
	{
		return pullUp;
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
