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
  
  /**
   * retrieve the node from the map, or throw an exception
   */
  public DEObject retrieveNode(String possibleLocation, Class<?> cls, String uuid) throws BBNodeNotFoundException
  {
  	DEObject node = nodes.get(uuid);
  	if (node == null)
      throw new BBNodeNotFoundException("Cannot find " + cls.getName() + " with uuid = " + uuid, determineLocation());

  	// make sure the element is compatible
  	if (!cls.isAssignableFrom(node.getClass()))
  	{
  		if (possibleLocation != null)
	      throw new BBNodeNotFoundException(
	          "Found element with uuid = " + uuid + ", but type was " + node.getClass().getName() + ", not " + cls.getName(),
	          possibleLocation);
  		else
        throw new BBNodeNotFoundException(
            "Found element with uuid = " + uuid + ", but type was " + node.getClass().getName() + ", not " + cls.getName(),
            determineLocation());
  	}
  	else
  		return node;
  }

  /**
   * determine the location in terms of nodes and values
   **/
  public static String determineLocation()
  {
    List<String> parts = new ArrayList<String>();
    for (;;)
    {
      // must do it this way, as there doesn't seem to be a way in XStream to work out if we are at the top
      try
      {
        String name = null; //reader.getNodeName();
        String value = null; //reader.getValue();
       
        if (value != null && value.length() != 0)
          parts.add(name + ": \"" + value + "\"");
        else
          parts.add(name);
      }
      catch (Exception ex)
      {
        // convert to a sensible string
        String ret = "";
        Collections.reverse(parts);
        for (String part : parts)
        {
          if (ret.length() != 0)
            ret += " / ";
          ret += part;
        }
        return ret;
      }
      //reader.moveUp();
    }
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
