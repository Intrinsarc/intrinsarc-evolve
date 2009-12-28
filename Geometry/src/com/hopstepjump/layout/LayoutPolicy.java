package com.hopstepjump.layout;

import java.util.*;

import com.hopstepjump.geometry.*;

public interface LayoutPolicy
{
	/**
	 *@param name
	 *@param fullSpace
	 *@param boxes
	 *@throws LayoutException
	 */
	public void computeLayoutBounds(String name, UBounds fullSpace, List boxes) throws LayoutException;
	/**
	 *@param boxes
	 *@return 
	 */
	public UDimension computeMinimumDimensions(List boxes);
}
