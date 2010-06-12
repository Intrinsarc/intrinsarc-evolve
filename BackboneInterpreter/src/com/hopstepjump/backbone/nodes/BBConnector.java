package com.hopstepjump.backbone.nodes;

import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;

public class BBConnector extends DEConnector implements INode
{
  private DEObject parent;
  private String name;
  private String uuid = BBUidGenerator.newUuid(getClass());
  private DEPort fromPort;
  private DEPart fromPart;
  private String fromIndex;
  private Boolean fromTakeNext;
  private DEPort toPort;
  private DEPart toPart;
  private String toIndex;
  private Boolean toTakeNext;
  private Boolean delegate;
	private List<DEAppliedStereotype> appliedStereotypes;
	private transient boolean synthetic;
  
	public BBConnector() {}
	
  public BBConnector(String uuid)
  {
  	this.uuid = uuid;
  }

  public BBConnector(String uuid, boolean synthetic)
	{
  	this.uuid = uuid;
		this.synthetic = synthetic;
	}

	private Object readResolve()
  {
  	GlobalNodeRegistry.registry.addNode(this);
  	return this;
  }
  
  public void setParent(DEObject parent)
  {
    this.parent = parent;
  }

	public void setFromPort(DEPort port)
  {
		fromPort = port;
  }

	public void setFromPart(DEPart part)
	{
		fromPart = part;
	} 

  public void setFromIndex(String fromIndex)
  {
    this.fromIndex = fromIndex;
  }

  public void setFromTakeNext(boolean fromTakeNext)
  {
  	if (!fromTakeNext)
  		this.fromTakeNext = null;
  	else
  		this.fromTakeNext = true;
  }

  public void setToPort(DEPort port)
  {
		toPort = port;
  }

	public void setToPart(DEPart part)
	{
		toPart = part;
	} 

  public void setToIndex(String toIndex)
  {
    this.toIndex = toIndex;
  }

  public void setToTakeNext(boolean toTakeNext)
  {
  	if (!toTakeNext)
  		this.toTakeNext = null;
  	else
  		this.toTakeNext = true;
  }

  public void setUuid(String uuid)
  {
    this.uuid = uuid;
  }

  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setDelegate(boolean delegate)
  {
  	if (!delegate)
  		this.delegate = null;
  	else
  		this.delegate = true;
  }

  ///////////////////////// contract //////////////////////////
  
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

  public DEPort getOriginalPort(int index)
  {
  	return index == 0 ? toPort : fromPort;
  }

  public DEPart getOriginalPart(int index)
  {
  	return index == 0 ? toPart : fromPart;
  }

	@Override
	public String getIndex(int ind)
	{
		if (ind == 0)
			return toIndex;
		return fromIndex;
	}

	@Override
	public boolean getTakeNext(int ind)
	{
		if (ind == 0)
			return toTakeNext != null;
		return fromTakeNext != null;
	}

	@Override
	public boolean isDelegate()
	{
		return delegate == null ? false : delegate;
	}

	@Override
	public boolean isSynthetic()
	{
		return synthetic;
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
