package com.hopstepjump.layout;

import com.hopstepjump.geometry.*;

public interface LayoutBox
{
	public String getName();
	public boolean isXExpandable();
	public boolean isYExpandable();
	public UDimension getMinimumDimensions();
	public LayoutBox getImmovableBox() throws LayoutException;

	public void computeLayoutBounds(UBounds fullSpace) throws LayoutException;
	public void offsetLayoutBounds(UDimension offset);
	public void setBoundsInBean(BeanBoundsSetter setter) throws LayoutException;
	
	/** to support testing */
	public UBounds getLayoutBounds(String name);
	public UBounds getLayoutBounds();
	
	/** for printing and testing */
	public String toString(int level);
}

