package com.hopstepjump.backbone.nodes.simple;

import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class SimpleReferenceConverter implements Converter
{
  public SimpleReferenceConverter()
  {
  }
  
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    BBSimpleObject element = (BBSimpleObject) source;
  	writer.setValue(element.getName());
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