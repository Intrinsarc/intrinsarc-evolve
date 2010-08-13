package com.intrinsarc.backbone.generator.hardcoded.flattened;

import java.util.*;

import com.intrinsarc.backbone.nodes.simple.*;
import com.intrinsarc.deltaengine.base.*;

/**
 * returns a unique name for each DEObject and remembers it for subsequent calls
 * @author andrew
 */

public class UniqueNamer
{
	private Map<Object, String> allocated = new HashMap<Object, String>();
	
	public UniqueNamer()
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

	public String getUniqueName(BBSimpleObject object)
	{
		return getGeneralUniqueName(object, object.getRawName());
	}

	public String getUniqueName(BBSimpleObject object, String desired)
	{
		return getGeneralUniqueName(object, desired);
	}

	public String getUniqueName(DEObject object)
	{
		return getGeneralUniqueName(object, object.getName());
	}
	
	public String getUniqueName(Object key, String desiredName)
	{
		return getGeneralUniqueName(key, desiredName);
	}
	
	private String getGeneralUniqueName(Object key, String desiredName)
	{
		// if we have already allocated, then return that
		if (allocated.containsKey(key))
			return allocated.get(key);
		
		if (desiredName == null || desiredName.length() == 0)
			desiredName = "x";
		String name = makeAcceptable(desiredName);

		// make the name unique
		Collection<String> existing = allocated.values();
		for (int lp = 0;; lp++)
		{
			String possible = name + (lp != 0 ? ("" + lp) : "");
			if (!existing.contains(possible))
			{
				name = possible;
				break;
			}
		}
		
		allocated.put(key, name);
		return name;
	}
}
