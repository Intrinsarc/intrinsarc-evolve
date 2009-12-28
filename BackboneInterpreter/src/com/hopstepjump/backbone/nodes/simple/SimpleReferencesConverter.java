package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class SimpleReferencesConverter implements Converter
{
  public SimpleReferencesConverter()
  {
  }
  
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    if (source instanceof Collection)
    {
      @SuppressWarnings("unchecked")
	    Collection<BBSimpleObject> list = (Collection<BBSimpleObject>) source;
	    String uuids = "";
	    int lp = 0;
	    int size = list.size();
	    for (BBSimpleObject node : list)
	    {
	      lp++;
	      uuids += node.getName() + (lp == size ? "" : " | ");
	    }
	    writer.setValue(uuids);
    }
    else
    {
	    BBSimpleObject list[] = (BBSimpleObject[]) source;
	    String uuids = "";
	    int lp = 0;
	    int size = list.length;
	    for (BBSimpleObject node : list)
	    {
	      lp++;
	      String fqn = node == null ? "null" : node.getName();
	      uuids += fqn + (lp == size ? "" : " | ");
	    }
	    writer.setValue(uuids);    	
    }
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
  	return null;
  }

  @SuppressWarnings("unchecked")
  public boolean canConvert(Class type)
  {
    return true;
  }
}