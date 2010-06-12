package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.deltaengine.base.*;

public class BBSimpleInterface extends BBSimpleElement
{
	private String name;
	private transient String rawName;
	private transient Class implementationClass;
	private String implementationClassName;
	private transient String uuid;
	private transient Set<BBSimpleInterface> supersPlusMe;
	private transient DEInterface complex;
	
	public BBSimpleInterface(BBSimpleElementRegistry registry, DEInterface complex)
	{
		this.complex = complex;
		rawName = complex.getName();
		uuid = complex.getUuid();
		name = registry.makeName(rawName);
		implementationClassName = complex.getImplementationClass(registry.getPerspective());
		
		// get the subclasses
		supersPlusMe = new HashSet<BBSimpleInterface>();
		supersPlusMe.add(this);
		for (DEElement sub : complex.getSuperElementClosure(registry.getPerspective(), true))
			supersPlusMe.add(registry.retrieveInterface(sub.asInterface()));
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
	public Class getImplementationClass()
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
  public boolean isBean()
  {
    return false;
  }

	public Set<BBSimpleInterface> getSuperInterfaceClosurePlusMe()
	{
		return supersPlusMe;
	}

	public DEInterface getComplex()
	{
		return complex;
	}
}
