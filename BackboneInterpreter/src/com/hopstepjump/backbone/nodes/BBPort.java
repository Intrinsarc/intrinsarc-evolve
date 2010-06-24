package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.insides.*;
import com.hopstepjump.backbone.nodes.lazy.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBPort extends DEPort implements INode, Serializable
{
  private transient DEObject parent;
  private String name;
  private String uuid = BBUidGenerator.newUuid(getClass());
  private Integer lowerBound;
  private Integer upperBound;
  private LazyObjects<DEInterface> setLazyProvides;
  private LazyObjects<DEInterface> setLazyRequires;
  private Set<DEInterface> setProvides;
  private Set<DEInterface> setRequires;
  private Boolean suppressGeneration;
	private List<DEAppliedStereotype> appliedStereotypes;
	private boolean createPort;
	private boolean beanMain;
	private boolean beanNoName;
	private PortKindEnum portKind;
	private Boolean ordered;
 
  public BBPort(UuidReference reference)
  {
  	this.uuid = reference.getUuid();
  	this.name = reference.getName();
  	GlobalNodeRegistry.registry.addNode(this);
  }
  
  public BBPort(String uuid, String name)
  {
  	this.uuid = uuid;
  	this.name = name;
  	GlobalNodeRegistry.registry.addNode(this);
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
  
  public LazyObjects<DEInterface> settable_getLazySetProvidedInterfaces()
  {
  	if (setLazyProvides == null)
  		setLazyProvides = new LazyObjects<DEInterface>(DEInterface.class);
  	return setLazyProvides;
  }

  public LazyObjects<DEInterface> settable_getLazySetRequiredInterfaces()
  {
  	if (setLazyRequires == null)
  		setLazyRequires = new LazyObjects<DEInterface>(DEInterface.class);
  	return setLazyRequires;
  }

  public Set<DEInterface> settable_getSetProvidedInterfaces()
  {
  	if (setProvides == null)
  		setProvides = new HashSet<DEInterface>();
  	return setProvides;
  }

  public Set<DEInterface> settable_getSetRequiredInterfaces()
  {
  	if (setRequires == null)
  		setRequires = new HashSet<DEInterface>();
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
		createPort = create;
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
		this.beanMain = beanMain;
	}
	
	public void setBeanNoName(boolean beanNoName)
	{
		this.beanNoName = beanNoName;
	}
	
	@Override
	public boolean isBeanMain()
	{
		return beanMain;
	}

	@Override
	public boolean isBeanNoName()
	{
		return beanNoName;
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
		this.ordered = ordered;
	}
	
	@Override
	public boolean isOrdered()
	{
		return ordered != null ? ordered : false;
	}

	@Override
	public void resolveLazyReferences()
	{
		if (setLazyProvides != null)
		{
			setLazyProvides.resolve();
			settable_getSetProvidedInterfaces().addAll(setLazyProvides.asObjectsSet());
			setLazyProvides = null;
		}
		if (setLazyRequires != null)
		{
			setLazyRequires.resolve();
			settable_getSetRequiredInterfaces().addAll(setLazyRequires.asObjectsSet());
			setLazyRequires = null;
		}
		
		if (appliedStereotypes != null)
			for (DEObject obj : appliedStereotypes)
				obj.resolveLazyReferences();
	}	
}
