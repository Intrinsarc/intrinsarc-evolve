package com.intrinsarc.backbone.nodes.simple;

import java.util.*;

import com.intrinsarc.backbone.exceptions.*;
import com.intrinsarc.deltaengine.base.*;

public class BBSimplePort extends BBSimpleObject
{
	private String name;
	private transient String rawName;
	private List<BBSimpleInterface> provides;
	private List<BBSimpleInterface> requires;
  private int upperBound;
  private int lowerBound;
	private transient DEPort complex;
	private transient boolean resolved;
	private transient BBSimpleComponent owner;
	
	public BBSimplePort(BBSimpleElementRegistry registry, DEComponent component, DEPort port, BBSimpleComponent owner)
	{
		rawName = port.getName();
		name = registry.makeName(rawName);
		upperBound = port.getUpperBound();
		lowerBound = port.getLowerBound();
		this.owner = owner;
		Set<? extends DEInterface> provided = component.getProvidedInterfaces(registry.getPerspective(), port);
		if (!provided.isEmpty())
		{
			provides = new ArrayList<BBSimpleInterface>();
			for (DEInterface prov : provided)
				provides.add(registry.retrieveInterface(prov));
		}
		Set<? extends DEInterface> required = component.getRequiredInterfaces(registry.getPerspective(), port);
		if (!required.isEmpty())
		{
			requires = new ArrayList<BBSimpleInterface>();
			for (DEInterface req : component.getRequiredInterfaces(registry.getPerspective(), port))
				requires.add(registry.retrieveInterface(req));
		}
		complex = port;
	}

	public void resolveImplementation(BBSimpleElementRegistry registry) throws BBImplementationInstantiationException
	{
		if (resolved)
			return;
		resolved = true;
		
		if (provides != null)
			for (BBSimpleInterface prov : provides)
				prov.resolveImplementation(registry);
		if (requires != null)
			for (BBSimpleInterface req : requires)
				req.resolveImplementation(registry);
	}

	public DEPort getComplexPort()
	{
		return complex;
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

	public List<BBSimpleInterface> getProvides()
	{
		if (provides == null)
			return new ArrayList<BBSimpleInterface>();
		return provides;
	}

	public List<BBSimpleInterface> getRequires()
	{
		if (requires == null)
			return new ArrayList<BBSimpleInterface>();
		return requires;
	}

	public int getUpperBound()
	{
		return upperBound;
	}

	public int getLowerBound()
	{
		return lowerBound;
	}

	public BBSimpleComponent getOwner()
	{
		return owner;
	}
	
	public boolean isIndexed()
	{
		return upperBound > 1 || upperBound == -1;
	}
	
	public boolean suppressGeneration()
	{
		return false;
	}

	public boolean isHyperport()
	{
		return complex.getPortKind() == PortKindEnum.HYPERPORT;
	}

	@Override
	public Map<String, List<? extends BBSimpleObject>> getChildren(boolean top)
	{
		Map<String, List<? extends BBSimpleObject>> children = new LinkedHashMap<String, List<? extends BBSimpleObject>>();
		children.put("provided interfaces", provides);
		children.put("required interfaces", requires);
		return children;
	}

	@Override
	public String getTreeDescription()
	{
		return
			"Port " + name + ", lower bound = " + lowerBound +
			", upper bound = " + upperBound;
	}
}
