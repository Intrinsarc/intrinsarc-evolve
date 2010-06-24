package com.hopstepjump.backbone.nodes;

import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBConnector extends DEConnector implements INode
{
  private DEObject parent;
  private String name;
  private String uuid = BBUidGenerator.newUuid(getClass());
  private LazyObject<DEPort> fromPort;
  private LazyObject<DEPart> fromPart;
  private String fromIndex;
  private Boolean fromTakeNext;
  private LazyObject<DEPort> toPort;
  private LazyObject<DEPart> toPart;
  private String toIndex;
  private Boolean toTakeNext;
  private Boolean delegate;
	private List<DEAppliedStereotype> appliedStereotypes;
	private transient boolean synthetic;
  
  public BBConnector(UuidReference reference)
  {
  	this(reference.getUuid());
  	this.name = reference.getName();
  }

  public BBConnector(String uuid)
  {
  	this.uuid = uuid;
  	this.name = uuid;
  	GlobalNodeRegistry.registry.addNode(this);
  }

  public BBConnector(String uuid, boolean synthetic)
	{
  	this.uuid = uuid;
		this.synthetic = synthetic;
  	resolveLazyReferences();
	}

  public void setParent(DEObject parent)
  {
    this.parent = parent;
  }

	public void setFromPort(DEPort port)
  {
		fromPort = new LazyObject<DEPort>(DEPort.class, port);
  }

	public void setLazyFromPort(UuidReference reference)
  {
		fromPort = new LazyObject<DEPort>(DEPort.class, reference);
  }

	public void setFromPart(DEPart part)
	{
		fromPart = new LazyObject<DEPart>(DEPart.class, part);
	} 

	public void setLazyFromPart(UuidReference reference)
	{
		fromPart = new LazyObject<DEPart>(DEPart.class, reference);
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
		toPort = new LazyObject<DEPort>(DEPort.class, port);
  }

  public void setLazyToPort(UuidReference reference)
  {
		toPort = new LazyObject<DEPort>(DEPort.class, reference);
  }

	public void setToPart(DEPart part)
	{
		toPart = new LazyObject<DEPart>(DEPart.class, part);
	} 

	public void setLazyToPart(UuidReference reference)
	{
		toPart = new LazyObject<DEPart>(DEPart.class, reference);
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
  	return index == 0 ? toPort.getObject() : fromPort.getObject();
  }

  public DEPart getOriginalPart(int index)
  {
  	return index == 0 ? (toPart == null ? null : toPart.getObject()) : (fromPart == null ? null : fromPart.getObject());
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

	@Override
	public void resolveLazyReferences()
	{
		if (fromPort != null)
			fromPort.resolve();
		if (toPort != null)
			toPort.resolve();
		if (fromPart != null)
			fromPart.resolve();
		if (toPart != null)
			toPart.resolve();
		resolve(appliedStereotypes);
	}
	
	private void resolve(List<? extends DEObject> objects)
	{
		if (objects != null)
			for (DEObject obj : objects)
				obj.resolveLazyReferences();
	}
}
