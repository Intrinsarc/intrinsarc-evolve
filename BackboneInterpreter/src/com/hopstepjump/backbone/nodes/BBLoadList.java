package com.hopstepjump.backbone.nodes;

import java.io.*;
import java.util.*;

import com.hopstepjump.backbone.exceptions.*;
import com.hopstepjump.backbone.nodes.insides.*;
import com.thoughtworks.xstream.annotations.*;

@XStreamAlias("LoadList")
public class BBLoadList implements Serializable
{
	@XStreamImplicit
	private List<BBVariableDefinition> variables;
	@XStreamImplicit
	private List<BBStratumDirectory> strataDirectories;
	
	public BBLoadList()
	{
	}
	
  public List<BBStratumDirectory> settable_getStrataDirectories()
  {
  	if (strataDirectories == null)
  		strataDirectories = new ArrayList<BBStratumDirectory>();
  	return strataDirectories;
  }
  
  public List<BBStratumDirectory> getTranslatedStrataDirectories() throws BBVariableNotFoundException
  {
  	if (strataDirectories == null)
  		return new ArrayList<BBStratumDirectory>();
  	
  	List<BBStratumDirectory> translated = new ArrayList<BBStratumDirectory>();
  	for (BBStratumDirectory d : strataDirectories)
  		translated.add(new BBStratumDirectory(expandVariables(d.getPath()), d.getStratumName()));
  	return translated;
  }

	class Location { public Location(int start, int end) { this.start = start; this.end = end; } public int start; public int end; }

	public String expandVariables(String text) throws BBVariableNotFoundException
	{
		// loop around until we have no variables left
		Location loc;
		int count = 0;
		while ((loc = locateFirstVariable(text)) != null)
		{
			// is this a problem?
			if (loc.end == loc.start + 1)
				throw new BBVariableNotFoundException("Problem with lone $ as variable");

			// find the variable
			String name = text.substring(loc.start + 1, loc.end);
			String variableContents = getVariable(name);
			if (variableContents == null)
				throw new BBVariableNotFoundException("Cannot find variable " + name);
			
			text = text.substring(0, loc.start) + variableContents + text.substring(loc.end);
			
			// try really hard to resolve
			if (++count == 100)
				throw new BBVariableNotFoundException("Circular variable references found");
		}
		return text;
	}

	private String getVariable(String name)
	{
		if (variables == null)
			return null;
		for (BBVariableDefinition var : variables)
		{
			if (var.getName().equals(name))
				return var.getValue();
		}
		return null;
	}

	private Location locateFirstVariable(String text)
	{
		// scan to the first $ and then look for the next alphanumeric characters
		int start = text.indexOf('$');
		if (start == -1)
			return null;
		int length  = text.length();
		int end;
		for (end = start + 1; end < length; end++)
		{
			char c = text.charAt(end);
			if (!Character.isJavaIdentifierPart(c))
				break;
		}
		return new Location(start, end);
	}

  public List<BBVariableDefinition> settable_getVariables()
  {
  	if (variables == null)
  		variables = new ArrayList<BBVariableDefinition>();
  	return variables;
  }
}
