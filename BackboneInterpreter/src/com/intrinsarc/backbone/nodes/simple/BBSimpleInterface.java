package com.intrinsarc.backbone.nodes.simple;

import java.util.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.deltaengine.base.*;

public class BBSimpleInterface extends BBSimpleElement
{
	private String name;
	private transient String rawName;
	private Class<?> implementationClass;
	private String implementationClassName;
	private String uuid;
	private Set<BBSimpleInterface> supersPlusMe;
	private Set<BBSimpleInterface> treePlusMe;
	private DEInterface complex;
	private BBSimpleElementRegistry registry;
	
	public BBSimpleInterface(BBSimpleElementRegistry registry, DEInterface complex)
	{
		this.registry = registry;
		this.complex = complex;
		rawName = complex.getTopName();
		uuid = complex.getUuid();
		name = registry.makeName(rawName);
		implementationClassName = complex.getImplementationClass(registry.getPerspective());
		
		// get the subclasses
		supersPlusMe = new HashSet<BBSimpleInterface>();
		supersPlusMe.add(this);
		for (DEElement sub : complex.getSuperElementClosure(registry.getPerspective(), true))
			supersPlusMe.add(registry.retrieveInterface(sub.asInterface()));
	}
	
	private Set<BBSimpleInterface> getOrMakeInheritanceTree()
	{
		if (treePlusMe != null)
			return treePlusMe;
		
		// get the tree
		treePlusMe = new HashSet<BBSimpleInterface>();
		for (DEElement tree : complex.getInheritanceTree(registry.getPerspective()))
			treePlusMe.add(registry.retrieveInterface(tree.asInterface()));
		return treePlusMe;
	}
	

	public void resolveImplementation(BBSimpleElementRegistry registry) throws BBImplementationInstantiationException
	{		
		// possibly extract the class
		if (implementationClassName != null)
			try
			{
				implementationClass = Class.forName(implementationClassName);
			}
			catch (ClassNotFoundException e)
			{
				throw new BBImplementationInstantiationException("Cannot locate class " + implementationClassName + " for interface " + name, this);
			}
	}

	@Override
	public Class<?> getImplementationClass()
	{
		return implementationClass;
	}

	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getRawName()
	{
		return rawName;
	}

	@Override
	public String getImplementationClassName()
	{
		return implementationClassName;
	}

	@Override
	public String getUuid()
	{
		return uuid;
	}

  @Override
  public boolean isLegacyBean()
  {
    return false;
  }

	public Set<BBSimpleInterface> getInterfaceTreePlusMe()
	{
		return treePlusMe;
	}

	public Set<BBSimpleInterface> getInheritanceTree()
	{
		return getOrMakeInheritanceTree();
	}

	public DEInterface getComplex()
	{
		return complex;
	}
	
	public String toString()
	{
		return getTreeDescription();
	}
	
	@Override
	public String getTreeDescription()
	{
		String desc = "Interface " + name;
		if (implementationClassName != null)
			desc += ", implementation = " + implementationClassName;
		return desc;
	}
}
