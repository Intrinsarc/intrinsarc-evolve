package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class ReferencesConverter implements Converter
{
  private Class<?> cls;
  
  public ReferencesConverter(Class<?> cls)
  {
    this.cls = cls;
  }
  
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    @SuppressWarnings("unchecked")
    List<DEObject> list = (ArrayList<DEObject>) source;
    String uuids = "";
    int lp = 0;
    int size = list.size();
    for (DEObject node : list)
    {
      lp++;
      String fqn = node.getFullyQualifiedName(".");
      boolean skip = node.getUuid().equals(fqn);
      uuids += node.getUuid() + (skip ? "" : " (" + fqn  + ")") + (lp == size ? "" : " | ");
    }
    writer.setValue(uuids);
    
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    List<DEObject> nodes = new ArrayList<DEObject>();

    StringTokenizer st = new StringTokenizer(reader.getValue(), "|");
    while (st.hasMoreTokens())
    {
      String raw = st.nextToken();
      
      // take this up to the first space
      String uuid = raw;
      StringTokenizer tk = new StringTokenizer(raw);
      if (tk.hasMoreTokens())
      	uuid = tk.nextToken();
      
      DEObject node = GlobalNodeRegistry.registry.retrieveNode(reader, null, cls, uuid);
      nodes.add(node);
    }
    return nodes;
  }

  @SuppressWarnings("unchecked")
  public boolean canConvert(Class type)
  {
    return true;
  }
}