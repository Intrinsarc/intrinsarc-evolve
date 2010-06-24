package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

public abstract class BBSimpleObject
{
	public abstract String getName();
	public abstract String getRawName();
	public Map<String, List<? extends BBSimpleObject>> getChildren(boolean top)
	{
		return null;
	}
	
	public String getTreeDescription()
	{
		return getName();
	}
}
