package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.converters.BBXStreamConverters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Connector")
public class BBConnector extends DEConnector implements INode, Serializable
{
  private transient DEObject parent;
  @XStreamAsAttribute
  private String name;
  @XStreamAsAttribute
  private String uuid = BBUidGenerator.newUuid(getClass());
  @XStreamConverter(PortReferenceConverter.class)
  private DEPort fromPort[];
  @XStreamConverter(PartReferenceConverter.class)
  private DEPart fromPart[];
  private String fromIndex;
  private Boolean fromTakeNext;
  @XStreamConverter(PortReferenceConverter.class)
  private DEPort toPort[] = new DEPort[1];
  @XStreamConverter(PartReferenceConverter.class)
  private DEPart toPart[] = new DEPart[1];
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
		if (port == null)
			fromPort = null;
		else
			fromPort = new DEPort[]{port};
  }

	public void setFromPart(DEPart part)
	{
		if (part == null)
			fromPart = null;
		else
			fromPart = new DEPart[]{part};
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
  	if (port == null)
  		toPort = null;
  	else
  		toPort = new DEPort[]{port};
  }

	public void setToPart(DEPart part)
	{
		if (part == null)
			toPart = null;
		else
			toPart = new DEPart[]{part};
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
  	if (index == 0)
  		return toPort == null ? null : toPort[0];
  	return fromPort == null ? null : fromPort[0];
  }

  public DEPart getOriginalPart(int index)
  {
  	if (index == 0)
  		return toPart == null ? null : toPart[0];
  	return fromPart == null ? null : fromPart[0];
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
