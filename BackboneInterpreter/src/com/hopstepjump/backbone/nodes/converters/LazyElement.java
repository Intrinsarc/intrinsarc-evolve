package com.hopstepjump.backbone.nodes.converters;

public class LazyElement<T>
{
	private String uuid;
	private T element;
	
	public void resolve()
	{
		element = (T) GlobalNodeRegistry.registry.getNode(uuid).asElement();
	}
	
	public T getElement()
	{
		return element; 
	}
}
