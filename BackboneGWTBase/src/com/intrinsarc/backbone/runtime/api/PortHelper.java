package com.intrinsarc.backbone.runtime.api;

import java.util.*;

public class PortHelper
{
	public static <T> int fill(List<T> list, T provided, int index)
	{
		if (index == -1)
		{
			// find a possible null, before increasing the size
			int possible = list.indexOf(null);
			if (possible == -1)
			{
				list.add(provided);
				return list.size() - 1;
			}
			else
			{
				list.set(possible, provided);
				return possible;
			}
		}
		else
		{
			int size = list.size();
			for (; size <= index; size++)
					list.add(null);
			list.set(index, provided);
			return index;
		}
	}
	
	public static <T> void remove(List<T> list, T provided)
	{
		list.remove(provided);
	}
}
