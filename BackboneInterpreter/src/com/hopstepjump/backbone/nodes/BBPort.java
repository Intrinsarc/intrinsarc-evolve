package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.deltaengine.base.*;

public class BBPort extends DEPort implements INode, Serializable
{
  private transient DEObject parent;
  private String name;
  private String uuid = BBUidGenerator.newUuid(getClass());
  private Integer lowerBound;
  private Integer upperBound;
  private List<DEInterface> setProvides;
  private List<DEInterface> setRequires;
  private Boolean suppressGeneration;
	private List<DEAppliedStereotype> appliedStereotypes;
	private Boolean createPort;
	private Boolean beanMain;
	private Boolean beanNoName;
	private PortKindEnum portKind;
	private Boolean ordered;
 
	public BBPort() {}
	
  public BBPort(String uuid)
  {
  	this.uuid = uuid;
  }
  
  public BBPort(String uuid, String name)
  {
  	this.uuid = uuid;
  	this.name = name;
  }
  
  private Object readResolve()
  {
  	GlobalNodeRegistry.registry.addNode(this);
  	return this;
  }
  
  @Override
  public int getLowerBound()
  {
    return lowerBound == null ? 1 : lowerBound;
  }
  
	public void setLowerBound(int bound)
	{
		lowerBound = bound;
	}

	@Override
  public int getUpperBound()
  {
    return upperBound == null ? 1 : upperBound;
  }

	public void setUpperBound(int bound)
	{
		upperBound = bound;
	}

	@Override
  public DEPort asPort()
  {
    return this;
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
  
  public List<DEInterface> settable_getSetProvidedInterfaces()
  {
  	if (setProvides == null)
  		setProvides = new ArrayList<DEInterface>();
  	return setProvides;
  }

  public List<DEInterface> settable_getSetRequiredInterfaces()
  {
  	if (setRequires == null)
  		setRequires = new ArrayList<DEInterface>();
  	return setRequires;
  }

	@Override
	public Set<? extends DEInterface> getSetProvidedInterfaces()
	{
    return setProvides == null ? new HashSet<DEInterface>() : new HashSet<DEInterface>(setProvides);
	}
	
	@Override
	public Set<? extends DEInterface> getSetRequiredInterfaces()
	{
    return setRequires == null ? new HashSet<DEInterface>() : new HashSet<DEInterface>(setRequires);
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public void setName(String name)
	{
		this.name = name;
	}


	public void setSuppressGeneration(boolean suppress)
	{
		this.suppressGeneration = suppress ? true : null;
	}
	
	@Override
	public boolean isSuppressGeneration()
	{
		return suppressGeneration == null ? false : true;
	}

	public void setCreatePort(boolean create)
	{
		createPort = create ? true : null;
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

	public void setBeanMain(boolean beanMain)
	{
		this.beanMain = beanMain ? true : null;
	}
	
	public void setBeanNoName(boolean beanNoName)
	{
		this.beanNoName = beanNoName;
	}
	
	@Override
	public boolean isBeanMain()
	{
		return beanMain != null && beanMain;
	}

	@Override
	public boolean isBeanNoName()
	{
		return beanNoName != null && beanNoName;
	}

	public void setPortKind(PortKindEnum portKind)
	{
		this.portKind = portKind;
	}
	
	@Override
	public PortKindEnum getPortKind()
	{
		return portKind;
	}

	public void setOrdered(boolean ordered)
	{
		this.ordered = ordered ? true : null;
	}
	
	@Override
	public boolean isOrdered()
	{
		return ordered != null ? ordered : false;
	}	
}
