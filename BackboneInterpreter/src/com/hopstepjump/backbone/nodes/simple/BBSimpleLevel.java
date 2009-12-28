package com.hopstepjump.backbone.nodes.simple;

import java.util.*;

public class BBSimpleLevel
{
	private BBSimpleLevel parent;
	private List<BBSimpleLevel> children = new ArrayList<BBSimpleLevel>();
	private List<BBSimplePart> parts = new ArrayList<BBSimplePart>();
	
	public BBSimpleLevel(BBSimpleLevel parent)
	{
		this.parent = parent;
	}
	
	public List<BBSimplePart> getParts(int moveUp)
	{
		BBSimpleLevel here = this;
		for (int lp = 0; lp < moveUp; lp++)
		{
			here = here.parent;
			if (here == null)
				return null;
		}
		return here.parts;
	}
	
	public void addLevel(BBSimpleLevel child)
	{
		children.add(child);
	}
	
	public void addPart(BBSimplePart part)
	{
		parts.add(part);
	}
}
