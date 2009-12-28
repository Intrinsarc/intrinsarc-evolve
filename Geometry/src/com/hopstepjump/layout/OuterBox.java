package com.hopstepjump.layout;

import com.hopstepjump.geometry.*;

public class OuterBox
{
	private UBounds currentBounds;
	private boolean autoSized;
	private boolean moveTopLeftIn;
	private UBounds layoutBounds;
	private LayoutBox contents;
	private String name;
	private boolean gridAlign;

	public OuterBox(UBounds currentBounds, boolean autoSized, boolean moveTopLeftIn, boolean gridAlign, LayoutBox contents)
	{
		this.currentBounds = currentBounds;
		this.autoSized = autoSized;
		this.moveTopLeftIn = moveTopLeftIn;
		this.contents = contents;
		this.gridAlign = gridAlign;
	}
	
	public OuterBox(String name, UBounds currentBounds, boolean autoSized, boolean moveTopLeftIn, boolean gridAlign, LayoutBox contents)
	{
		this.name = name;
		this.currentBounds = currentBounds;
		this.autoSized = autoSized;
		this.moveTopLeftIn = moveTopLeftIn;
		this.contents = contents;
		this.gridAlign = gridAlign;
	}
	
	public void computeLayoutBounds() throws LayoutException
	{
		// compute the minimum space and possibly adjust the current bounds to fit...
		
		// rule: only 1 immovable box anywhere -- this will throw an exception if > 1
		ImmovableBox immovable = (ImmovableBox) contents.getImmovableBox();
		
		// get the minimum size of the entire network
		UDimension minDimensions = contents.getMinimumDimensions();
		contents.computeLayoutBounds(new UBounds(new UPoint(0,0), minDimensions));
		
		// adjust current bounds: permutations
		//   immovable x autoSized x moveTopLeftIn
		
		// handle movable
		if (immovable == null)
		{
			// if we have no immovables, then simply transport to the origin of current bounds
			layoutBounds = new UBounds(currentBounds.getPoint(), minDimensions);
			if (!autoSized)
				layoutBounds = layoutBounds.union(currentBounds);
		}
		else
		{
			// if we are not autosized, take the max of the current bounds and the min bounds, and
			// use this to work out the distance of the 
			UDimension distanceFromOrigin = immovable.getLayoutBounds().getPoint().asDimension();
			UPoint desiredImmovableTopLeft = immovable.getMinimumBounds().getPoint();

			// work out the real start pt
			UPoint topLeft = desiredImmovableTopLeft.subtract(distanceFromOrigin);
			if (!moveTopLeftIn)
				topLeft = topLeft.minOfEach(currentBounds.getPoint());

			// set the desired immovable start pt to topLeft + distance from origin
			immovable.setMinimumBoundsTopLeft(topLeft.add(distanceFromOrigin));
			UDimension realMinDimension = contents.getMinimumDimensions();
			
			// set the bottom right point
			UPoint bottomRight = topLeft.add(realMinDimension).maxOfEach(currentBounds.getBottomRightPoint());
				
			// we now have the layout bounds
			layoutBounds = new UBounds(topLeft, bottomRight.subtract(topLeft));
		}
		
		// if we have a grid to align to, then adjust the point slightly
		if (gridAlign)
		{
			UPoint flooredTopLeft = Grid.floorToGrid(layoutBounds.getPoint());
			layoutBounds = new UBounds(flooredTopLeft, layoutBounds.getBottomRightPoint());
		}
		
		// ask each other element to compute the layout bounds, using the outer as a guide
		contents.computeLayoutBounds(layoutBounds);
		
		// if we have an immoveable, check that its layout bounds cover it ok.  throws a runtime exception if not...
		if (immovable != null)
			immovable.checkThatAllocatedLayoutCoversMinimumBounds();
	}
	
	public void offsetLayoutBounds(UDimension offset)
	{
		layoutBounds = layoutBounds.addToPoint(offset);
		contents.offsetLayoutBounds(offset);
	}
	
	public void computeLayoutAndSetBoundsInBean(Object bean) throws LayoutException
	{
		computeLayoutBounds();
		
		BeanBoundsSetter setter = new BeanBoundsSetter(bean);
		setter.setBounds(name, layoutBounds);
		contents.setBoundsInBean(setter);
	}
	
	public void setBoundsInBean(Object bean) throws LayoutException
	{
		BeanBoundsSetter setter = new BeanBoundsSetter(bean);
		setter.setBounds(name, layoutBounds);
		contents.setBoundsInBean(setter);
	}	
	
	/**
	 * Sets the contents.
	 * @param contents The contents to set
	 */
	public void setContents(LayoutBox contents)
	{
		this.contents = contents;
	}

	/**
	 * Returns the layoutBounds.
	 * @return UBounds
	 */
	public UBounds getLayoutBounds()
	{
		return layoutBounds;
	}

	/**
	 * Returns the layoutBounds for the named item
	 * @return UBounds
	 */
	public UBounds getLayoutBounds(String name)
	{
		if (name.equals("outer"))
			return layoutBounds;
		return contents.getLayoutBounds(name);
	}

	public String toString()
	{
		return "OuterBox (name=" + name + ", autoSized=" + autoSized + ", moveTopLeftIn=" + moveTopLeftIn + ", currentBounds=" + currentBounds + ") layout=" + layoutBounds + "\n" + contents.toString(0);
	}
	
	/**
	 * Sets the gridAlign.
	 * @param gridAlign The gridAlign to set
	 */
	public void setGridAlign(boolean gridAlign)
	{
		this.gridAlign = gridAlign;
	}

}
