package com.hopstepjump.idraw.figures.simplecontainernode;

import com.hopstepjump.geometry.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class SimpleContainerSizeInfo
{
	// this is always considered to be autosized -- the owner of this supplies the "padding"
	/** variables indicating the current positioning */
	private UPoint topLeft;
	private UDimension extent;

	/** variables which affect the new size */
	private UBounds minContainedBounds;  // empty if nothing contained
	
	/** geometry variables */
	private UDimension minExtent;
	private UDimension offset;
	
	/** autosized constructor */
	public SimpleContainerSizeInfo(UPoint topLeft, UDimension extent, UBounds minContainedBounds, UDimension minExtent, UDimension offset)
	{
		this.topLeft = topLeft;
		this.extent = extent;
		this.minExtent = minExtent;
		this.offset = offset;
		this.minContainedBounds = minContainedBounds;
	}

	public void setTopLeft(UPoint topLeft)
	{
		this.topLeft = topLeft;
	}

	public UPoint getTopLeft()
	{
		return topLeft;
	}

	public SimpleContainerSizes calculateSizes()
	{
		SimpleContainerSizes sizes = new SimpleContainerSizes();
	
		// have 2 possibilites: contained elements (or not)
		if (minContainedBounds != null)
		{
			UBounds containedBounds = minContainedBounds.addToPoint(offset.negative()).addToExtent(offset.multiply(2));
			sizes.setContained(containedBounds);
			UPoint newTopLeft = containedBounds.getTopLeftPoint();
			sizes.setEntireBounds(new UBounds(newTopLeft, containedBounds.getBottomRightPoint().subtract(newTopLeft)));
		}
		else
			sizes.setEntireBounds(new UBounds(topLeft, minExtent));
		
		return sizes;		
	}

	public void setExtent(UDimension extent)
	{
		this.extent = extent;
	}

	public UDimension getExtent()
	{
		return extent;
	}
	
	/**
	 * Returns the minContainedBounds.
	 * @return UBounds
	 */
	public UBounds getMinContainedBounds()
	{
		return minContainedBounds;
	}

}
