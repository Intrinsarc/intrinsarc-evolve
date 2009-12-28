package com.hopstepjump.geometry;


/**
 *
 * (c) Andrew McVeigh 14-Aug-02
 *
 */
public class Grid
{
	private static UDimension gridSpace = new UDimension(4,4);

	public static void setGridSpace(UDimension newGridSpace)
	{
		gridSpace = newGridSpace;
	}
	
	public static UDimension getGridSpace()
	{
		return gridSpace;
	}
	
	public static UPoint roundToGrid(UPoint point)
	{
		double
	  spaceWidth = gridSpace.getWidth(),
	  spaceHeight = gridSpace.getHeight();
	
		return new UPoint(Math.round(point.getX() / spaceWidth) * spaceWidth,
	                    Math.round(point.getY() / spaceHeight) * spaceHeight);
	}
	
	public static UDimension roundToGrid(UDimension extent)
	{
		double
	  spaceWidth = gridSpace.getWidth(),
	  spaceHeight = gridSpace.getHeight();
	
		return new UDimension(Math.round(extent.getWidth() / spaceWidth) * spaceWidth,
	                        Math.round(extent.getHeight() / spaceHeight) * spaceHeight);
	}
	
	public static UPoint floorToGrid(UPoint point)
	{
		double
	  spaceWidth = gridSpace.getWidth(),
	  spaceHeight = gridSpace.getHeight();
	
		return new UPoint(Math.floor(point.getX() / spaceWidth) * spaceWidth,
	                    Math.floor(point.getY() / spaceHeight) * spaceHeight);
	}
}
