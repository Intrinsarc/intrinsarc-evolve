/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.featurenode;

import com.intrinsarc.geometry.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class FeatureSizes
{
	private UPoint topLeft;
	private UPoint textTopLeft;
	private UPoint iconPoint;

	private UPoint linkMarkPoint;
	private ZNode linkMark;
	
	private ZText text;
	private UBounds entireBounds;

	public void setTopLeft(UPoint topLeft)
	{
		this.topLeft = topLeft;
	}

	public void setIconPoint(UPoint iconPoint)
	{
		this.iconPoint = iconPoint;
	}

	public UPoint getIconPoint()
	{
		return iconPoint;
	}

	public void setEntireBounds(UBounds entireBounds)
	{
		this.entireBounds = entireBounds;
	}

	public UBounds getEntireBounds()
	{
		return entireBounds;
	}

	public void setText(ZText text)
	{
		this.text = text;
	}

	public ZText getText()
	{
		return text;
	}

	public void setTextTopLeft(UPoint textTopLeft)
	{
		this.textTopLeft = textTopLeft;
	}

	public UPoint getTextTopLeft()
	{
		return textTopLeft;
	}

	public UPoint getTopLeft()
	{
		return topLeft;
	}
	/**
	 * Returns the linkMarkPoint.
	 * @return UPoint
	 */
	public UPoint getLinkMarkPoint()
	{
		return linkMarkPoint;
	}

	/**
	 * Sets the linkMarkPoint.
	 * @param linkMarkPoint The linkMarkPoint to set
	 */
	public void setLinkMarkPoint(UPoint linkMarkPoint)
	{
		this.linkMarkPoint = linkMarkPoint;
	}

	/**
	 * Returns the linkMark.
	 * @return ZNode
	 */
	public ZNode getLinkMark()
	{
		return linkMark;
	}

	/**
	 * Sets the linkMark.
	 * @param linkMark The linkMark to set
	 */
	public void setLinkMark(ZNode linkMark)
	{
		this.linkMark = linkMark;
	}

}
