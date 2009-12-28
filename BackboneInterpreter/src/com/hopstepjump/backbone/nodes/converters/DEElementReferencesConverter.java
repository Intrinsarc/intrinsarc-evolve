package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.converters.*;
import com.thoughtworks.xstream.io.*;

public class DEElementReferencesConverter implements Converter
{
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    @SuppressWarnings("unchecked")
    List<DEElement> list = (ArrayList<DEElement>) source;
    String uuids = "";
    for (DEElement node : list)
      uuids += node.getUuid() + " ";
    writer.setValue(uuids);
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    List<DEElement> nodes = new ArrayList<DEElement>();

    StringTokenizer st = new StringTokenizer(reader.getValue());
    while (st.hasMoreTokens())
    {
      String uuid = st.nextToken().trim();
      DEElement node = (DEElement) GlobalNodeRegistry.registry.retrieveNode(reader, null, DEElement.class, uuid);
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