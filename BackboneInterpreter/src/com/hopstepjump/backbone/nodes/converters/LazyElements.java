package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

public class LazyElements<T>
{
	private List<String> uuids;
	private List<T> elements;
	
	public LazyElements(List<String> uuids)
	{
		this.uuids = uuids;
	}
	
	public void resolve()
	{
		elements = new ArrayList<T>();
		for (String uuid : uuids)
		{
			T elem = (T) GlobalNodeRegistry.registry.getNode(uuid).asElement();
			elements.add(elem);
		}
		uuids = null;
	}
	
	public List<T> getElements()
	{
		return elements;
	}
}
