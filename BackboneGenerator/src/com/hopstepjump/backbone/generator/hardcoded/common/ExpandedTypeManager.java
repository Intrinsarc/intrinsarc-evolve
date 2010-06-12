package com.hopstepjump.backbone.generator.hardcoded.common;

import java.util.*;

import com.hopstepjump.backbone.nodes.simple.*;

public class ExpandedTypeManager
{
	private List<ExpandedTypeGenerator> generators = new ArrayList<ExpandedTypeGenerator>();
	private BBSimpleElementRegistry registry;
	private List<String> profile;
	
	public ExpandedTypeManager(BBSimpleElementRegistry registry, List<String> profile)
	{
		this.registry = registry;
		this.profile = profile;
	}
	
	public void addGenerator(ExpandedTypeGenerator generator)
	{
		generators.add(generator);
	}
	
	public String formConstructionAndRemember(BBSimplePart part, String partName)
	{
		for (ExpandedTypeGenerator g : generators)
		{
			String expand = g.formConstructionAndRemember(registry, part, partName, profile);
			if (expand != null)
				return expand;
		}
		return null;
	}
	
	public String constructClasses()
	{
		StringBuilder s = new StringBuilder();
		for (ExpandedTypeGenerator g : generators)
			s.append(g.constructClasses());
		return s.toString();
	}
}
