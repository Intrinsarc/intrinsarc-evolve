package com.hopstepjump.layout;

import java.util.*;

import com.hopstepjump.geometry.*;

public class HorizontalLayout implements LayoutPolicy
{
	/**
	 * @see com.hopstepjump.layout.LayoutPolicy#computeLayoutBounds(UBounds, List)
	 */
	public void computeLayoutBounds(String name, UBounds fullSpace, List boxes) throws LayoutException
	{
		// ensure that a maximum of 1 X expandable element is present
		Iterator iter = boxes.iterator();
		int xExpandable = 0;
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			if (box.isXExpandable())
				xExpandable++;
		}
		// complain if we have too many y expandables
		if (xExpandable > 1)
			throw new LayoutException("Found " + xExpandable + " X expandable boxes in container " + name + ".  Container has " + boxes.size() + " elements");
		
		// iterate up to an expandable object from both sides, and save the expandable box
		LayoutBox expandable = null;
		double fullHeight = fullSpace.getHeight();
		
		// fwd pass
		iter = boxes.iterator();
		UPoint top = fullSpace.getTopLeftPoint();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			if (box.isXExpandable())
			{
				expandable = box;
				break;
			}
			else
			{
				// tell the underlying object to take up the minimum amount of space in the y direction
				// and the full amount in the x direction
				double minWidth = box.getMinimumDimensions().getWidth();
				box.computeLayoutBounds(new UBounds(top, new UDimension(minWidth, fullHeight)));
				top = top.add(new UDimension(minWidth, 0));
			}
		}
	
		// reverse pass
		if (expandable != null)
		{
			Collections.reverse(boxes);
			iter = boxes.iterator();
			UPoint bottom = fullSpace.getTopRightPoint();
			while (iter.hasNext())
			{
				LayoutBox box = (LayoutBox) iter.next();
				if (box == expandable)
				{
					// expandable box gets from top to bottom, with full width
					box.computeLayoutBounds(new UBounds(top, new UDimension(bottom.subtract(top).getWidth(), fullHeight)));
					break;
				}
				else
				{
					// tell the underlying object to take up the minimum amount of space in the y direction
					// and the full amount in the x direction
					double minWidth = box.getMinimumDimensions().getWidth();
					bottom = bottom.subtract(new UDimension(minWidth, 0));
					box.computeLayoutBounds(new UBounds(bottom, new UDimension(minWidth, fullHeight)));
				}
			}
			Collections.reverse(boxes);
		}
		
		// we should have allocated all the space now		
	}

	/**
	 * @see com.hopstepjump.layout.LayoutPolicy#computeMinimumDimensions(List)
	 */
	public UDimension computeMinimumDimensions(List boxes)
	{
		// take the maximum y, and the cumulative x
		double maxY = 0;
		double sumX = 0;
		
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			
			UDimension minDim = box.getMinimumDimensions();
			maxY = Math.max(minDim.getHeight(), maxY);
			sumX += minDim.getWidth();
		}
		return new UDimension(sumX, maxY);
	}

}
