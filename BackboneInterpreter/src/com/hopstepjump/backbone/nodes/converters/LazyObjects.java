package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.backbone.parserbase.*;

public class LazyObjects<T>
{
	private List<UUIDReference> references;
	private List<T> objects = new ArrayList<T>();
	private Class<T> cls;
	
	public LazyObjects(List<UUIDReference> references, Class<T> cls)
	{
		this.references = references;
		this.cls = cls;
	}
	
	public LazyObjects(Class<T> cls)
	{
		this.references = new ArrayList<UUIDReference>();
		this.cls = cls;
	}
	
	public void addReference(UUIDReference reference)
	{
		references.add(reference);
	}
	
	public void addObject(T object)
	{
		objects.add(object);
	}
	
	public void resolve()
	{
		for (UUIDReference ref : references)
		{
			T obj = GlobalNodeRegistry.registry.getNode(ref, cls);
			objects.add(obj);
		}
		references = null;
	}
	
	public List<T> getObjects()
	{
		return objects;
	}

	public boolean isEmpty()
	{
		return references.isEmpty();
	}
}
