package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBStratum extends DEStratum implements Serializable
{
	private DEStratum parent;
  private String name;
  private String uuid;
  private String parentUuid;
  private Boolean destructive;
  private Boolean relaxed;
  private List<DEStratum> rawDependsOn;
  private List<DEElement> elements;
  private List<DEStratum> childPackages;

  public BBStratum(UUIDReference reference)
  {
  	this.uuid = reference.getUUID();
  	this.name = reference.getName();
  	GlobalNodeRegistry.registry.addNode(this);
  }

  public BBStratum(String uuid)
  {
  	this.uuid = uuid;
  	this.name = uuid;
  	GlobalNodeRegistry.registry.addNode(this);
  }

  @Override
  public String getName()
  {
    return name == null ? "" : name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setUuid(String uuid)
  {
    this.uuid = uuid;
  }   

  public List<DEStratum> settable_getChildPackages()
  {
    if (childPackages == null)
      childPackages = new ArrayList<DEStratum>();
    return childPackages;
  }
  
  public void setDestructive(Boolean destructive)
  {
  	this.destructive = destructive ? true : null;
  }

  public List<DEElement> settable_getElements()
  {
    if (elements == null)
      elements = new ArrayList<DEElement>();
    return elements;
  }
  
  public List<DEStratum> settable_getRawDependsOn()
  {
    if (rawDependsOn == null)
      rawDependsOn = new ArrayList<DEStratum>();
    return rawDependsOn;
  }

  public void setRelaxed(boolean relaxed)
  {
  	this.relaxed = relaxed ? true : null;
  }
  
  public void setParentUuid(String parentUuid)
  {
		this.parentUuid = parentUuid;
  }

  public void setParentAndTellChildren(BBStratum parent)
  {
  	this.parent = parent;
  	if (parent != null)
  		parent.addDirectlyNestedPackage(this);
    
    // tell each child
    if (elements != null)
      for (DEElement element : elements)
      {
        // unfortunate cast...
        if (element instanceof INode)
          ((INode) element).setParent(this);
      }
  }

  private void addDirectlyNestedPackage(BBStratum pkg)
	{
  	if (childPackages == null)
  		childPackages = new ArrayList<DEStratum>();
  	childPackages.add(pkg);
	}

	public String getParentUuid()
  {
  	return parentUuid;
  }
  
  ////////////////////////////////////////////////////////
  
  @Override
  public String getUuid()
  {
    return uuid;
  }
  
  @Override
  public List<DEStratum> getRawDependsOn()
  {
  	return rawDependsOn == null ? new ArrayList<DEStratum>() : rawDependsOn;
  }
  
  @Override
  public List<? extends DEStratum> getDirectlyNestedPackages()
  {
    return childPackages == null ? new ArrayList<DEStratum>() : childPackages;
  }
  @Override
  public boolean isDestructive()
  {
    return destructive != null;
  }

  @Override
  public boolean isRelaxed()
  {
    return relaxed != null;
  }

  @Override
  public DEElement asElement()
  {
    return null;
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
	public List<DEElement> getChildElements()
	{
		return elements == null ? new ArrayList<DEElement>() : elements;
	}

	@Override
	public void forceParent(DEStratum parent)
	{
		this.parent = parent;
	}

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		throw new UnsupportedOperationException("Packages cannot have stereotypes");
	}

	@Override
	public boolean isCheckOnceIfReadOnly()
	{
		// doesn't matter, as this only optimises for permutations
		return false;
	}

	@Override
	public boolean isReadOnly()
	{
		// not needed here
		return false;
	}

	public void resolveLazyReferences()
	{
		if (elements != null)
		{
			for (DEElement e : elements)
				e.resolveLazyReferences();
		}
	}
}
