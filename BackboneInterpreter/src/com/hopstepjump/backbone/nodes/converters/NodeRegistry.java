package com.hopstepjump.backbone.nodes.converters;

import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.*;
import com.hopstepjump.deltaengine.base.*;

public class NodeRegistry
{
  private Map<String, DEObject> nodes = new HashMap<String, DEObject>();
  private BBStratum root;
	private List<LazyResolver> lazies = new ArrayList<LazyResolver>();  
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
  
  public DEObject getNode(String uuid)
  {
  	return nodes.get(uuid);
  }

	public void addLazyResolver(LazyResolver lazy)
	{
		lazies.add(lazy);
	}
	
	public void resolveLazyReferences() throws BBNodeNotFoundException
	{
		for (LazyResolver lazy : lazies)
			lazy.resolveLazyReferences();
	}
}
