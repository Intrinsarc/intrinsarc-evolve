package com.hopstepjump.backbone.nodes.converters;

import com.hopstepjump.backbone.parserbase.*;

public class LazyObject<T>
{
	private UUIDReference reference;
	private T object;
	private Class<T> cls;
	
	public LazyObject(UUIDReference reference, Class<T> cls)
	{
		this.reference = reference;
		this.cls = cls;
	}
	
	public LazyObject(Class<T> cls)
	{
		this.cls = cls;
	}
	
	public void setReference(UUIDReference reference)
	{
		this.reference = reference;
	}
	
	public void setObject(T object)
	{
		this.object = object;
	}
	
	public void resolve()
	{
		object = GlobalNodeRegistry.registry.getNode(reference, cls);
	}
	
	public T getObject()
	{
		return object; 
	}
}
