package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class LazyObjects<T>
{
	private List<UuidReference> references;
	private List<T> objects = new ArrayList<T>();
	private Class<T> cls;
	
	public LazyObjects(List<UuidReference> references, Class<T> cls)
	{
		this.references = references;
		this.cls = cls;
	}
	
	public LazyObjects(Class<T> cls)
	{
		this.references = new ArrayList<UuidReference>();
		this.cls = cls;
	}
	
	public void addReference(UuidReference reference)
	{
		references.add(reference);
	}
	
	public void addObject(T object)
	{
		objects.add(object);
	}
	
	public void resolve()
	{
		for (UuidReference ref : references)
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

	public Set<T> asObjectsSet()
	{
		return new HashSet<T>(objects);
	}

	public List<T> asObjectsList()
	{
		return objects;
	}
}
