package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.nodes.converters.BBXStreamConverters.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("AppliedStereotype")
public class BBAppliedStereotype extends DEAppliedStereotype implements Serializable
{
  private transient DEObject parent;
  @XStreamConverter(ComponentReferenceConverter.class)
	private DEComponent[] stereotype = new DEComponent[1];
	private Map<DEAttribute, String> properties;
	
	public BBAppliedStereotype()
	{		
	}
	
	public BBAppliedStereotype(DEAppliedStereotype us)
	{
		stereotype[0] = us.getStereotype();
		Map<DEAttribute, String> props = us.getProperties();
		if (props != null && !props.isEmpty())
		{
			properties = new HashMap<DEAttribute, String>();
			for (DEAttribute key : props.keySet())
				properties.put(key, props.get(key));
		}
	}

	@Override
	public String getName()
	{
		return "";
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
		if (properties == null)
			return new HashMap<DEAttribute, String>();
		return properties;
	}

  public void setParent(DEObject parent)
  {
    this.parent = parent;
  }

	@Override
	public DEComponent getStereotype()
	{
		return stereotype[0];
	}

	@Override
	public DEObject getParent()
	{
		return parent;
	}
	
	public void setStereotype(DEComponent stereo)
	{
		stereotype[0] = stereo;
	}
}
