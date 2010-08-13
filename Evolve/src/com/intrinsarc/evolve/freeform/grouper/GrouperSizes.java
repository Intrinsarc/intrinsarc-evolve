/*
 * Created on Dec 31, 2003 by Andrew McVeigh
 */
package com.intrinsarc.evolve.freeform.grouper;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;

import edu.umd.cs.jazz.*;

/**
 * A simple javabean to hold the grouper sizing calculations
 * @author Andrew
 */
public class GrouperSizes implements ContainerCalculatedSizes
{
	private UBounds outer;
	private UBounds text;     // relative to (0,0)
	private UBounds contents;
	private ZTransformGroup zName;

	/**
	 * @return
	 */
	public UBounds getContents()
	{
		return contents;
	}

	/**
	 * @return
	 */
	public UBounds getOuter()
	{
		return outer;
	}

	/**
	 * @return
	 */
	public UBounds getText()
	{
		return text;
	}

	/**
	 * @return
	 */
	public ZTransformGroup getZName()
	{
		return zName;
	}

	/**
	 * @param bounds
	 */
	public void setContents(UBounds bounds)
	{
		contents = bounds;
	}

	/**
	 * @param bounds
	 */
	public void setOuter(UBounds bounds)
	{
		outer = bounds;
	}

	/**
	 * @param bounds
	 */
	public void setText(UBounds bounds)
	{
		text = bounds;
	}

	/**
	 * @param group
	 */
	public void setZName(ZTransformGroup group)
	{
		zName = group;
	}

}
