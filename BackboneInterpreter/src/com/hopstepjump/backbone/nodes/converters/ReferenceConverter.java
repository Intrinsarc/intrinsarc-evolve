package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public abstract class ReferenceConverter implements Converter
{
  private Class<?> cls;
  
  public ReferenceConverter(Class<?> cls)
  {
    this.cls = cls;
  }
  
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    DEObject[] element = (DEObject[]) source;
    String fqn = element[0].getFullyQualifiedName(".");
    boolean skip = element[0].getUuid().equals(fqn);

  	writer.setValue(element[0].getUuid() + (skip ? "" : " (" + fqn + ")"));
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    String raw = reader.getValue().trim();
    
    // take this up to the first space
    String uuid = raw;
    StringTokenizer tk = new StringTokenizer(raw);
    if (tk.hasMoreTokens())
    	uuid = tk.nextToken();
    
    return cast(GlobalNodeRegistry.registry.retrieveNode(reader, null, cls, uuid));
  }
  
  public abstract Object cast(DEObject obj);

  @SuppressWarnings("unchecked")
  public boolean canConvert(Class type)
  {
    return true;
  }
}