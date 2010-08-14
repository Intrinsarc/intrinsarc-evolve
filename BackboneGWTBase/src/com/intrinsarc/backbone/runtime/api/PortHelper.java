package com.intrinsarc.backbone.runtime.api;

import java.util.*;

public class PortHelper
{
	public static <T> T fill(List<T> list, T provided, int index)
	{
		if (index == -1)
		{
			// find a possible null, before increasing the size
			int possible = list.indexOf(null);
			if (possible == -1)
			{
				list.add(provided);
			}
			else
			{
				System.out.println("$$ found null");
				list.set(possible, provided);
			}
		}
		else
		{
			int size = list.size();
			for (; size <= index; size++)
					list.add(null);
			list.set(index, provided);
		}
		return provided;
	}
	
	public static void remove(List<?> list, Object provided)
	{
		list.remove(provided);
	}
}
