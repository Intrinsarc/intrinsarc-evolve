package com.intrinsarc.idraw.foundation;

import java.util.*;

public class ToolClassification
{
	private String category;
	private List<String> elements;
	private List<String> hints;
	private Object userObject;
	private boolean node;

	public ToolClassification(String category, String elements, String hints, Object userObject, boolean node)
	{
		this.category = category;
		this.elements = expand(elements);
		this.hints = expand(hints);
		this.userObject = userObject;
		this.node = node;
	}
	
	static List<String> expand(String str)
	{
		if (str == null)
			return null;
		List<String> exp = new ArrayList<String>();
		StringTokenizer tok = new StringTokenizer(str, ",");
		while (tok.hasMoreElements())
			exp.add(tok.nextToken());
		return exp;
	}

	public String getCategory()
	{
		return category;
	}

	public List<String> getElements()
	{
		return elements;
	}

	public List<String> getHints()
	{
		return hints;
	}

	public Object getUserObject()
	{
		return userObject;
	}

	public boolean isNode()
	{
		return node;
	}
	
	public String toString()
	{
		return "ToolClassification(" + elements + ", " + hints + ")";
	}
}
