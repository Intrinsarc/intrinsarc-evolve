package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class StringReferencesConverter implements Converter
{
  public StringReferencesConverter()
  {
  }
  
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    @SuppressWarnings("unchecked")
    List<String> list = (ArrayList<String>) source;
    String uuids = "";
    int lp = 0;
    int size = list.size();
    for (String node : list)
    {
      lp++;
      uuids += node + (lp == size ? "" : " | ");
    }
    writer.setValue(uuids);
    
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    List<String> nodes = new ArrayList<String>();

    StringTokenizer st = new StringTokenizer(reader.getValue(), "|");
    while (st.hasMoreTokens())
    {
      String uuid = st.nextToken().trim();
      nodes.add(uuid);
    }
    return nodes;
  }

  @SuppressWarnings("unchecked")
  public boolean canConvert(Class type)
  {
    return true;
  }
}