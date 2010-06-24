package com.hopstepjump.backbone.nodes.lazy;

import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class LazyObject<T>
{
	private LazyReference reference;
	private T object;
	private Class<T> cls;
	
	public LazyObject(LazyReference reference, Class<T> cls)
	{
		this.reference = reference;
		this.cls = cls;
	}
	
	public LazyObject(Class<T> cls)
	{
		this.cls = cls;
	}
	
	public LazyObject(Class<T> cls, LazyReference reference)
	{
		this.cls = cls;
		this.reference = reference;
	}
	
	public LazyObject(Class<T> cls, T object)
	{
		this.cls = cls;
		this.object = object;
	}

	public void setReference(LazyReference reference)
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
