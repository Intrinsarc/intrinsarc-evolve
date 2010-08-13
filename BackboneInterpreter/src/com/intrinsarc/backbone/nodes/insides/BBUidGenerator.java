package com.intrinsarc.backbone.nodes.insides;

public class BBUidGenerator
{
	public static int currentUuid;
	
	public static String newUuid(Class<?> type)
	{
		return type.getName() + currentUuid++;
	}
}
