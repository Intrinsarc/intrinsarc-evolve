package com.intrinsarc.deltaengine.base;

import java.util.*;

public abstract class DEAppliedStereotype extends DEConstituent
{
	public static final String UUID = "applied";
	public abstract String getName();
	public abstract DEComponent getStereotype();
	public abstract Map<DEAttribute, String> getProperties(); 
	
	public int getIntegerProperty(String uuid)
	{
		return new Integer(resolve(uuid, "0"));
	}
	
	public String getStringProperty(String uuid)
	{
		return resolve(uuid, "");
	}
	
	public String getStringProperty(String uuid, String defaultValue)
	{
		return resolve(uuid, defaultValue);
	}
	
	public boolean getBooleanProperty(String uuid)
	{
		return new Boolean(resolve(uuid, "false"));
	}

	private String resolve(String uuid, String defaultValue)
	{
		if (getProperties() == null)
			return defaultValue;
		Map<DEAttribute, String> props = getProperties();
		for (DEAttribute attr : props.keySet())
			if (attr.getUuid().equals(uuid))
				return props.get(attr);
		return defaultValue;
	}
	
	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return null;
	}
	
	@Override
	public Object getRepositoryObject()
	{
		return null;
	}
	
	@Override
	public String getUuid()
	{
		return UUID;
	}
	
	@Override
	public DEAppliedStereotype asAppliedStereotype()
	{
		return this;
	}	
}
