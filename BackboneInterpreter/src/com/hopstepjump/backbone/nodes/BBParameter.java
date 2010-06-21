package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.converters.*;
import com.hopstepjump.deltaengine.base.*;

public class BBParameter extends DEParameter
{
	private String literal;
	private String attribute;
	private transient DEAttribute realAttribute;

	public BBParameter(String literal)
	{
		this.literal = literal;
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
	    
	    realAttribute = GlobalNodeRegistry.registry.getNode(uuid, DEAttribute.class);
		}
	}

}
