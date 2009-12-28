package com.hopstepjump.idraw.environment;

import com.hopstepjump.idraw.foundation.persistence.*;


public class Preference
{
	private String tab;
	private String name;
	private String defaultValue;
	
	public Preference(String tab, String name)
	{
		this.tab = tab;
		this.name = name;
	}

	public Preference(String tab, String name, int defaultValue)
	{
		this.tab = tab;
		this.name = name;
		this.defaultValue = "" + defaultValue;
	}

	public Preference(String tab, String name, PersistentProperty defaultValue)
	{
		this.tab = tab;
		this.name = name;
		this.defaultValue = defaultValue.asString();
	}

	public String getTab()
	{
		return tab;
	}

	public String getName()
	{
		return name;
	}
	
	public String getAccessName()
	{
		return name;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
  @Override
	public boolean equals(Object other)
	{
	  if (!(other instanceof Preference))
	    return false;
	  Preference o = (Preference) other;
	  return tab.equals(o.tab) && name.equals(o.name);
	}
	
	@Override
	public int hashCode()
	{
	  return tab.hashCode() ^ name.hashCode();
	}
	
	public String getRegistryName()
	{
    return tab + "/" + name;
	}
	
	@Override
	public String toString()
	{
		return "Preference(" + tab + ", " + name + ")";
	}
}
