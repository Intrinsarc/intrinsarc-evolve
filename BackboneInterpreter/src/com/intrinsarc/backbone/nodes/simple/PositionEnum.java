package com.intrinsarc.backbone.nodes.simple;

public enum PositionEnum
{
	TOP,
	MIDDLE,
	BOTTOM;
	
	public static PositionEnum classifyPart(BBSimplePart part)
	{
		if (part.getType().isFactory())
			return TOP;
		if (part.getType().isLeaf())
			return BOTTOM;
		return MIDDLE;
	}
}
