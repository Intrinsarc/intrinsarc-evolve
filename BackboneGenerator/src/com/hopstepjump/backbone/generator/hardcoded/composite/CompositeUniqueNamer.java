package com.hopstepjump.backbone.generator.hardcoded.composite;

import java.util.*;

/**
 * returns a unique name for each DEObject and remembers it for subsequent calls
 * @author andrew
 */

public class CompositeUniqueNamer
{
	private Map<Object, String> allocated = new HashMap<Object, String>();
	private Map<Object, String> shortAllocated = new HashMap<Object, String>();
	private Set<String> existing = new HashSet<String>();
	
	public CompositeUniqueNamer()
	{
	}
	
	public static String makeAcceptable(String name)
	{
		name = name.trim();
		if (!Character.isJavaIdentifierStart(name.charAt(0)))
			name += "x";
		StringBuilder b = new StringBuilder();
		int size = name.length();
		for (int lp = 0; lp < size; lp++)
		{
			char c = name.charAt(lp);
			if (Character.isJavaIdentifierPart(c))
				b.append(c);
		}
		
		return b.toString();		
	}
	
	public String getUniqueName(Object key, String desiredName)
	{
		return getUniqueName(key, desiredName, false);
	}

	public String getUniqueName(Object key, String desiredName, boolean makeAcceptable)
	{
		return getUniqueName(key, "", desiredName, false);
	}
	
	public String getAllocatedUniqueName(Object key)
	{
		return shortAllocated.get(key);
	}
	
	public String getUniqueName(Object key, String uniquePrefix, String desiredName, boolean makeAcceptable)
	{
		// if we have already allocated, then return that
		if (shortAllocated.containsKey(key))
			return shortAllocated.get(key);
		
		if (desiredName == null || desiredName.length() == 0)
			desiredName = "x";
		String name = makeAcceptable ? makeAcceptable(desiredName) : desiredName;

		// make the name unique
		for (int lp = 0;; lp++)
		{
			String possible = name + (lp != 0 ? ("" + lp) : "");
			if (!existing.contains(uniquePrefix + possible))
			{
				name = possible;
				break;
			}
		}
		
		allocated.put(key, uniquePrefix + name);
		shortAllocated.put(key, name);
		existing.add(uniquePrefix + name);
		return name;
	}
}
