package com.hopstepjump.backbone.nodes.lazy;

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
  }
  
  public NodeRegistry initialize()
  {
  	root = new BBStratum("model");
  	root.setName("model");
  	root.setDestructive(true);
  	addNode(root);
  	return this;
  }
  
  public BBStratum getRoot()
  {
  	return root;
  }
  
  public void addNode(DEObject node)
  {
    nodes.put(node.getUuid(), node);
  }
  
  public <T> T getNode(UuidReference reference, Class<T> cls)
  {
  	DEObject obj = nodes.get(reference.getUuid());
  	if (obj == null)
  		throw new BBNodeNotFoundException("Object " + cls.getName() + ", UUID = " + reference.getUuid() + " not found", reference.toString());
  	if (!(cls.isAssignableFrom(obj.getClass())))
  		throw new BBNodeNotFoundException("Found " + cls.getName() + ", UUID = " + reference.getUuid() + " but it is not an instance of " + cls.getName(), reference.toString());
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

	public boolean hasNode(UuidReference uuidReference)
	{
		return nodes.containsKey(uuidReference.getUuid());
	}
}
