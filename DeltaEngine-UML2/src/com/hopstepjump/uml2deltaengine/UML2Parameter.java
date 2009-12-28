package com.hopstepjump.uml2deltaengine;

import com.hopstepjump.deltaengine.base.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("Parameter")
public class UML2Parameter extends DEParameter
{
	private String literal;
	private DEAttribute attribute;
	
	public UML2Parameter(String literal)
	{
		this.literal = literal;
	}
	
	public UML2Parameter(DEAttribute attribute)
	{
		this.attribute = attribute;
	}

	public String getLiteral()
	{
		return literal;
	}

	public DEAttribute getAttribute()
	{
		return attribute;
	}
}
