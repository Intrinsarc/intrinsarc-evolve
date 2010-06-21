package com.hopstepjump.backbone.nodes;

import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBAttribute extends DEAttribute implements INode
{
  private transient DEObject parent;
  private String name;
  private String uuid = BBUidGenerator.newUuid(getClass());
  private LazyObject<DEElement> type = new LazyObject<DEElement>(DEElement.class);
  private List<DEParameter> defaultValue;
  private List<BBAppliedStereotype> appliedStereotypes;
	private Boolean readOnly;
	private Boolean writeOnly;
	private Boolean suppressGeneration;
	private transient boolean synthetic;
	private transient boolean pullUp;
  
	public BBAttribute() {}
	
  public BBAttribute(String uuid)
  {
  	this(uuid, false, false);
  	GlobalNodeRegistry.registry.addNode(this);
  }

  public BBAttribute(String uuid, boolean synthetic, boolean pullUp)
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

  public DEElement getType()
  {
    return type.getObject();
  }

  public void setType(DEElement type)
  {
		this.type.setObject(type);
  }

  public void setType(UUIDReference reference)
  {
		this.type.setReference(reference);
  }

	public List<DEParameter> getDefaultValue()
  {
  	if (defaultValue == null)
  		return new ArrayList<DEParameter>();
    return defaultValue;
  }

  public void setDefaultValue(List<DEParameter> parameters)
  {
    this.defaultValue = parameters;
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
	public boolean isSynthetic()
	{
		return synthetic;
	}
	
	@Override
	public boolean isPullUp()
	{
		return pullUp;
	}

	public boolean isReadOnly()
	{
		return readOnly != null && readOnly;
	}

	public void setReadOnly(boolean readOnly)
	{
		this.readOnly = readOnly ? true : null;
	}

	public boolean isWriteOnly()
	{
		return writeOnly != null && writeOnly;
	}

	public void setWriteOnly(boolean writeOnly)
	{
		this.writeOnly = writeOnly ? true : null;
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
	public boolean isSuppressGeneration()
	{
		return suppressGeneration != null && suppressGeneration;
	}	
	
	public void setSuppressGeneration(boolean suppress)
	{
		this.suppressGeneration = suppress ? true : null;
	}
	
	@Override
	public void resolveLazyReferences()
	{
		type.resolve();
		resolve(appliedStereotypes);
	}
	
	private void resolve(List<? extends DEObject> objects)
	{
		if (objects != null)
			for (DEObject obj : objects)
				obj.resolveLazyReferences();
	}
}
