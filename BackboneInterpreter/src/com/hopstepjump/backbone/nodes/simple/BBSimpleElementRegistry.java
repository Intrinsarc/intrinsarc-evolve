package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;

public class BBSimpleElementRegistry
{
	private Map<DEElement, BBSimpleElement> existing = new HashMap<DEElement, BBSimpleElement>();
	private DEStratum perspective;
	private int count = 0;
	private List<BBSimpleComponent> hasPlaceholderParts = new ArrayList<BBSimpleComponent>();
	private List<BBSimpleComponent> hasComplexConnectors = new ArrayList<BBSimpleComponent>();
	private DEComponent top;
	
	public BBSimpleElementRegistry(DEStratum perspective, DEComponent top)
	{
		this.perspective = perspective;
		this.top = top;
	}
	
	public void put(DEComponent complex, BBSimpleElement simple)
	{
		existing.put(complex, simple);
	}
	
	public BBSimpleElement retrieve(DEElement complex)
	{
		BBSimpleElement simple = existing.get(complex);
		if (simple != null)
			return simple;
		if (complex.asComponent() != null)
			simple = new BBSimpleComponent(this, complex.asComponent());
		else
			simple = new BBSimpleInterface(this, complex.asInterface());
		existing.put(complex, simple);
		return simple;
	}
	
	public DEStratum getPerspective()
	{
		return perspective;
	}

	public BBSimpleInterface retrieveInterface(DEInterface iface)
	{
		return (BBSimpleInterface) retrieve(iface);
	}
	
	public BBSimpleComponent retrieveComponent(DEComponent comp)
	{
		return (BBSimpleComponent) retrieve(comp);
	}
	
	public String makeName(String name)
	{
		return (name != null && name.length() != 0 ? name + "  " : "") + "{" + count++ +"}";
	}
	
	public void addHasPlaceholderParts(BBSimpleComponent component)
	{
		hasPlaceholderParts.add(component);
	}
	
	public List<BBSimpleComponent> getHasPlaceholderParts()
	{
		return hasPlaceholderParts;
	}

	public void addHasComplexConnectors(BBSimpleComponent component)
	{
		hasComplexConnectors.add(component);
	}
	
	public List<BBSimpleComponent> getHasComplexConnectors()
	{
		return hasComplexConnectors;
	}

	public DEComponent getTopComponent()
	{
		return top;
	}
	
	public void setTopComponent(DEComponent top)
	{
		this.top = top;
	}
}
