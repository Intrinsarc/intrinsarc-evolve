package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Value")
public class BBParameter extends DEParameter implements LazyResolver, Serializable
{
	private String literal;
	private String attribute;
	private transient DEAttribute realAttribute;

	public BBParameter() {}
	
	public BBParameter(String literal)
	{
		this.literal = literal;
	}

	protected Object readResolve()
  {
  	GlobalNodeRegistry.registry.addLazyResolver(this);
  	return this;
  }
	
	@Override
	public DEAttribute getAttribute()
	{
		if (realAttribute == null)
			return null;
		return realAttribute;
	}

	@Override
	public String getLiteral()
	{
		return literal;
	}

	public void resolveLazyReferences() throws BBNodeNotFoundException
	{
		if (attribute != null)
		{
	    String raw = attribute.trim();
	    
	    // take this up to the first space
	    String uuid = raw;
	    StringTokenizer tk = new StringTokenizer(raw);
	    if (tk.hasMoreTokens())
	    	uuid = tk.nextToken();
	    
	    realAttribute = GlobalNodeRegistry.registry.retrieveNode(null, null, DEAttribute.class, uuid).asConstituent().asAttribute();
		}
	}

}
