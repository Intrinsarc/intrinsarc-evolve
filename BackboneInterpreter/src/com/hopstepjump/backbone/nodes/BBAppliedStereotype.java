package com.hopstepjump.backbone.nodes;

import java.util.*;

import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class BBAppliedStereotype extends DEAppliedStereotype
{
  private transient DEObject parent;
	private LazyObject<DEComponent> stereotype = new LazyObject<DEComponent>(DEComponent.class);
	private Map<LazyObject<DEAttribute>, String> lazyProperties;
	private Map<DEAttribute, String> properties;
	private boolean addedLazy;
	
	public BBAppliedStereotype()
	{		
	}
	
	@Override
	public String getName()
	{
		return "";
	}
	
	public Map<LazyObject<DEAttribute>, String> settable_getLazyProperties()
	{
		if (lazyProperties == null)
			lazyProperties = new HashMap<LazyObject<DEAttribute>, String>();
		return lazyProperties;		
	}
	
	public Map<DEAttribute, String> settable_getProperties()
	{
		if (properties == null)
			properties = new HashMap<DEAttribute, String>();
		return properties;		
	}
	
	@Override
	public Map<DEAttribute, String> getProperties()
	{
		Map<DEAttribute, String> props = settable_getProperties();
		if (!addedLazy)
		{
			for (LazyObject<DEAttribute> attr : lazyProperties.keySet())
				props.put(attr.getObject(), properties.get(attr));
			addedLazy = true;
		}
		return props;
	}

  public void setParent(DEObject parent)
  {
    this.parent = parent;
  }

	@Override
	public DEComponent getStereotype()
	{
		return stereotype.getObject();
	}

	@Override
	public DEObject getParent()
	{
		return parent;
	}
	
	public void setStereotype(DEComponent stereo)
	{
		stereotype.setObject(stereo);
	}

	
	public void setStereotype(UUIDReference stereo)
	{
		stereotype.setReference(stereo);
	}
}
