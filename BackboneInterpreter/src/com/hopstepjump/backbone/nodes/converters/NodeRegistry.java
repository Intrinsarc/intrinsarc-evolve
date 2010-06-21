package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.backbone.parserbase.*;
import com.hopstepjump.deltaengine.base.*;

public class NodeRegistry
{
  private Map<String, DEObject> nodes = new HashMap<String, DEObject>();
  private BBStratum root;
  public NodeRegistry()
  {
  	root = new BBStratum("model");
  	root.setName("model");
  	root.setDestructive(true);
  	addNode(root);
  }
  
  public BBStratum getRoot()
  {
  	return root;
  }
  
  public void addNode(DEObject node)
  {
    nodes.put(node.getUuid(), node);
  }
  
  public <T> T getNode(UUIDReference reference, Class<T> cls)
  {
  	DEObject obj = nodes.get(reference.getUUID());
  	if (obj == null)
  		throw new BBNodeNotFoundException("Object " + cls.getName() + ", UUID = " + reference.getUUID() + " not found", reference.toString());
  	if (!(cls.isAssignableFrom(obj.getClass())))
  		throw new BBNodeNotFoundException("Found " + cls.getName() + ", UUID = " + reference.getUUID() + " but it is not an instance of " + cls.getName(), reference.toString());
  	return (T) obj;
  }
  
  public <T> T getNode(String uuid, Class<T> cls)
  {
  	DEObject obj = nodes.get(uuid);
  	if (obj == null)
  		throw new BBNodeNotFoundException("Object " + cls.getName() + ", UUID = " + uuid, "");
  	if (!(cls.isAssignableFrom(obj.getClass())))
  		throw new BBNodeNotFoundException("Found " + cls.getName() + ", UUID = " + uuid, "");
  	return (T) obj;
  }
}
