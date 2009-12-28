package com.hopstepjump.layout;

import java.util.*;

import com.hopstepjump.geometry.*;

/**
 *
 * (c) Andrew McVeigh 17-Dec-02
 *
 */
public class VerticalSpaceLayout implements LayoutPolicy
{
	private List<Double> boxProportions = new ArrayList<Double>();
	
	public VerticalSpaceLayout(double proportions[])
	{
		if (proportions != null)
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
		double extraHeight = fullSpace.getHeight() - getMinHeight(boxes);

		// iterate up to an expandable object from both sides, and save the expandable box
		double fullWidth = fullSpace.getWidth();
		
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
			double minHeight = box.getMinimumDimensions().getHeight();
			double actualHeight = minHeight + proportion.doubleValue() / sum * extraHeight;
			box.computeLayoutBounds(new UBounds(top, new UDimension(fullWidth, actualHeight)));
			top = top.add(new UDimension(0, actualHeight));
		}

		// we should have allocated all the space now
	}
	
	private double getMinHeight(List boxes)
	{
		double width = 0;
		Iterator iter = boxes.iterator();
		while (iter.hasNext())
		{
			LayoutBox box = (LayoutBox) iter.next();
			width += box.getMinimumDimensions().getHeight();
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
			throw new LayoutException("Number of boxes in VerticalSpaceLayout is " + boxes.size() + ", and number of proportions is " + boxProportions.size() + ", container = " + name);
		
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
				throw new LayoutException("Found an ImmovableBox in a container " + name + " with a VerticalSpaceLayout");
			
			if (!box.isYExpandable() && proportion != 0)
				throw new LayoutException("Found an non-Yexpandable box with a non-zero proportion of " + proportion + " in " + name);
			else
				foundExpander = true;
		}
		if (!foundExpander)
			throw new LayoutException("Found no Yexpandable boxes in " + name);
	}

	/**
	 * @see com.hopstepjump.layout.LayoutPolicy#computeMinimumDimensions(List)
	 */
	public UDimension computeMinimumDimensions(List boxes)
	{
		// take the maximum y, and the cumulative x
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
