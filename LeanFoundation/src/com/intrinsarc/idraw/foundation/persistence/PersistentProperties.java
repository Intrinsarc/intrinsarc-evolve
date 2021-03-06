package com.intrinsarc.idraw.foundation.persistence;

import java.awt.*;
import java.util.*;
import java.util.List;

import com.intrinsarc.geometry.*;

/**
 *
 * (c) Andrew McVeigh 30-Aug-02
 *
 */
public class PersistentProperties implements Cloneable
{
	private Map<String, PersistentProperty> properties = new HashMap<String, PersistentProperty>();
	
	public PersistentProperties()
	{
	}

	public PersistentProperties(PersistentProperties otherProperties)
	{
		// copy all the properties
		if (otherProperties != null)
			for (PersistentProperty property : otherProperties.properties.values())
				add(property);
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof PersistentProperties))
			return false;
		return properties.equals(((PersistentProperties) obj).properties);
	}

	public int hashCode()
	{
		return properties.hashCode();
	}
	
	public void add(PersistentProperty property)
	{
		properties.put(property.getName(), property);
	}
	
  public void addIfNotThere(PersistentProperty property)
  {
    if (!properties.containsKey(property.getName()))
      properties.put(property.getName(), property);
  }
  
  public boolean contains(String name)
  {
    return properties.containsKey(name);
  }
  
	public PersistentProperty retrieve(String name)
	{
		PersistentProperty property = properties.get(name);
		if (property == null)
			throw new PersistentPropertyNotFoundException("Property " + name + " not found");
		return property;
	}
	
	public PersistentProperty retrieve(String name, String defaultValue)
	{
		PersistentProperty property = properties.get(name);
		if (property == null)
			property = new PersistentProperty(name, defaultValue);
		return property;
	}
	
	public PersistentProperty retrieve(String name, int defaultValue)
	{
		PersistentProperty property = properties.get(name);
		if (property == null)
			property = new PersistentProperty(name, new Integer(defaultValue).toString());
		return property;
	}
	
	public PersistentProperty retrieve(String name, boolean defaultValue)
	{
		PersistentProperty property = properties.get(name);
		if (property == null)
			property = new PersistentProperty(name, new Boolean(defaultValue).toString());
		return property;
	}
	
	public PersistentProperty retrieve(String name, UDimension defaultValue)
	{
		PersistentProperty property = properties.get(name);
		if (property == null)
			property = new PersistentProperty(name, "" + Math.round(defaultValue.getWidth()) + " " + Math.round(defaultValue.getHeight()));
		return property;
	}
	
  public PersistentProperty retrieve(String name, Color initialFillColor)
  {
    PersistentProperty property = properties.get(name);
    if (property == null)
      property = new PersistentProperty(
          name,
          initialFillColor,
          initialFillColor);
    return property;
  }

  public PersistentProperty retrieve(String name, Font defaultFont)
  {
    PersistentProperty property = properties.get(name);
    if (property == null)
      property = new PersistentProperty(
          name,
          defaultFont,
          defaultFont);
    return property;
  }

	public Iterator iterator()
	{
		return properties.values().iterator();
	}
	
	private List<String> makeLines(String text)
  {
    List<String> lines = new ArrayList<String>();
    try
    {
      java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.StringReader(text));
      for (int lp = 0;; lp++)
      {
        String line = reader.readLine();
        if (line == null)
          break;
        lines.add(line);
      }
    }
    catch (java.io.IOException ex)
    {
      // should never happen with a string
    }
    return lines;
  }

  public void remove(String name)
  {
    properties.remove(name);
  }

	public void useDefaults(PersistentProperties defaults)
	{
		// only set if not already there
		if (defaults != null)
			for (PersistentProperty property : defaults.properties.values())
				if (!contains(property.getName()))
						add(property);
	}
	
  public String toString()
  {
  	String value = "";
  	for (PersistentProperty property : properties.values())
  		value += property.toString() + " | ";
  	return value;
  }
  
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			throw new InternalError(ex.toString());
		}
	}

	public void addAll(PersistentProperties properties)
	{
		if (properties != null)
			for (PersistentProperty property : properties.properties.values())
				add(property);
	}
}
