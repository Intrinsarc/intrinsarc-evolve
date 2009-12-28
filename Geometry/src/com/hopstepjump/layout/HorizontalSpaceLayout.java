package com.hopstepjump.layout;

import java.util.*;

import com.hopstepjump.geometry.*;

/**
 *
 * (c) Andrew McVeigh 06-Dec-02
 *
 */
public class HorizontalSpaceLayout implements LayoutPolicy
{
	private List<Double> boxProportions = new ArrayList<Double>();
	
	public HorizontalSpaceLayout(double proportions[])
	{
		for (int lp = 0; lp < proportions.length; lp++)
			boxProportions.add(new Double(proportions[lp]));
	}

	public void addProportion(double proportion)
	{
		boxProportions.add(new Double(proportion));
	}

	/**
	 * @see com.hopstepjump.layout.LayoutPolicy#computeLayoutBounds(UBounds, List)
	 */
	public void computeLayoutBounds(String name, UBounds fullSpace, List boxes) throws LayoutException
	{
		// make sure that we have no immovable boxes, and that everything expands
		ensureConstraints(name, boxes);

		// get the total sum of all the proportions
		double sum = getSumOfAllProportions(); 
		double extraWidth = fullSpace.getWidth() - getMinWidth(boxes);

		// iterate up to an expandable object from both sides, and save the expandable box
		double fullHeight = fullSpace.getHeight();
		
		// iterate and set sizes
		Iterator iter = boxes.iterator();
		Iterator propIter = boxProportions.iterator();
		UPoint top = fullSpace.getPoint();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			Double proportion = (Double) propIter.next();

			// tell the underlying object to take up the minimum amount of space in the y direction
			// and the full amount in the x direction
			double minWidth = box.getMinimumDimensions().getWidth();
			double actualWidth = minWidth + proportion.doubleValue() / sum * extraWidth;
			box.computeLayoutBounds(new UBounds(top, new UDimension(actualWidth, fullHeight)));
			top = top.add(new UDimension(actualWidth, 0));
		}

		// we should have allocated all the space now
	}
	
	private double getMinWidth(List boxes)
	{
		double width = 0;
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			width += box.getMinimumDimensions().getWidth();
		}
		return width;
	}
	
	private double getSumOfAllProportions()
	{
		double sum = 0;
		Iterator iter = boxProportions.iterator();
		while (iter.hasNext())
		{
			Double prop = (Double) iter.next();
			sum += prop.doubleValue();
		}
		return sum;
	}
	
	private void ensureConstraints(String name, List boxes) throws LayoutException
	{
		// ensure that the number of proportions is equal to the number of boxes
		if (boxes.size() != boxProportions.size())
			throw new LayoutException("Number of boxes in HorizontalSpaceLayout is " + boxes.size() + ", and number of proportions is " + boxProportions.size() + ", container = " + name);
		
		// can't have any immovables
		// also, any non-expandable boxes must have 0 for their proportion
		Iterator iter = boxes.iterator();
		Iterator propIter = boxProportions.iterator();
		boolean foundExpander = false;
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			double proportion = ((Double) propIter.next()).doubleValue();
			
			if (box.getImmovableBox() != null)
				throw new LayoutException("Found an ImmovableBox in a container " + name + " with a HorizontalSpaceLayout");
			
			if (!box.isXExpandable() && proportion != 0)
				throw new LayoutException("Found an non-Xexpandable box with a non-zero proportion of " + proportion + " in " + name);
			else
				foundExpander = true;
		}
		if (!foundExpander)
			throw new LayoutException("Found no Xexpandable boxes in " + name);
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
