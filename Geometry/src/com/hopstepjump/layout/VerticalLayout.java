package com.hopstepjump.layout;

import java.util.*;

import com.hopstepjump.geometry.*;

public class VerticalLayout implements LayoutPolicy
{
	/**
	 *@param name
	 *@param fullSpace
	 *@param boxes
	 *@throws LayoutException
	 *@see com.hopstepjump.layout.LayoutPolicy#computeLayoutBounds(UBounds, List)
	 */
	public void computeLayoutBounds(String name, UBounds fullSpace, List boxes) throws LayoutException
	{
		// ensure that a maximum of 1 Y expandable element is present
		Iterator iter = boxes.iterator();
		int yExpandable = 0;
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			if (box.isYExpandable())
				yExpandable++;
		}
		// complain if we have too many y expandables
		if (yExpandable > 1)
			throw new IllegalStateException("Found " + yExpandable + " Y expandable boxes in container " + name);
		
		// iterate up to an expandable object from both sides, and save the expandable box
		LayoutBox expandable = null;
		double fullWidth = fullSpace.getWidth();
		
		// fwd pass
		iter = boxes.iterator();
		UPoint top = fullSpace.getTopLeftPoint();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			if (box.isYExpandable())
			{
				expandable = box;
				break;
			}
			else
			{
				// tell the underlying object to take up the minimum amount of space in the y direction
				// and the full amount in the x direction
				double minHeight = box.getMinimumDimensions().getHeight();
				box.computeLayoutBounds(new UBounds(top, new UDimension(fullWidth, minHeight)));
				top = top.add(new UDimension(0, minHeight));
			}
		}
		
		// reverse pass
		if (expandable != null)
		{
			Collections.reverse(boxes);
			iter = boxes.iterator();
			UPoint bottom = fullSpace.getBottomLeftPoint();
			while (iter.hasNext())
			{
				LayoutBox box = (LayoutBox) iter.next();
				if (box == expandable)
				{
					// expandable box gets from top to bottom, with full width
					box.computeLayoutBounds(new UBounds(top, new UDimension(fullWidth, bottom.subtract(top).getHeight())));
					break;
				}
				else
				{
					// tell the underlying object to take up the minimum amount of space in the y direction
					// and the full amount in the x direction
					double minHeight = box.getMinimumDimensions().getHeight();
					bottom = bottom.subtract(new UDimension(0, minHeight));
					box.computeLayoutBounds(new UBounds(bottom, new UDimension(fullWidth, minHeight)));
				}
			}
			Collections.reverse(boxes);
		}
		
		// we should have allocated all the space now		
	}

	/**
	 *@param boxes
	 *@return @see com.hopstepjump.layout.LayoutPolicy#computeMinimumDimensions(List)
	 */
	public UDimension computeMinimumDimensions(List boxes)
	{
		// take the maximum x, and the cumulative y
		double maxX = 0;
		double sumY = 0;
		
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			
			UDimension minDim = box.getMinimumDimensions();
			maxX = Math.max(minDim.getWidth(), maxX);
			sumY += minDim.getHeight();
		}
		return new UDimension(maxX, sumY);
	}

}
