package com.hopstepjump.backbone.nodes.converters;

import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class LazyObject<T>
{
	private UuidReference reference;
	private T object;
	private Class<T> cls;
	
	public LazyObject(UuidReference reference, Class<T> cls)
	{
		this.reference = reference;
		this.cls = cls;
	}
	
	public LazyObject(Class<T> cls)
	{
		this.cls = cls;
	}
	
	public LazyObject(Class<T> cls, UuidReference reference)
	{
		this.cls = cls;
		this.reference = reference;
	}
	
	public LazyObject(Class<T> cls, T object)
	{
		this.cls = cls;
		this.object = object;
	}

	public void setReference(UuidReference reference)
	{
		this.reference = reference;
	}
	
	public void setObject(T object)
	{
		this.object = object;
	}
	
	public void resolve()
	{
		if (object == null)
			object = GlobalNodeRegistry.registry.getNode(reference, cls);
		reference = null;
	}
	
	public T getObject()
	{
		return object; 
	}
}
