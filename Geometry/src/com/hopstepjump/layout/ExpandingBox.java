package com.hopstepjump.layout;

import com.hopstepjump.geometry.*;

public class ExpandingBox implements LayoutBox
{
	/** expansion options */
	public static final int EXPAND_NONE = 0;
	public static final int EXPAND_X    = 1;
	public static final int EXPAND_Y    = 2;
	public static final int EXPAND_BOTH = 3;
	private int expand = EXPAND_NONE;
	
	private String expansionString(int expand)
	{
		switch (expand)
		{
			case EXPAND_NONE:
				return "NONE";
			case EXPAND_X:
				return "X";
			case EXPAND_Y:
				return "Y";
			case EXPAND_BOTH:
				return "BOTH";
			default:
				return "UNKNOWN";
		}
	}
	
	/** internal expansion options */
	private int internalExpand = EXPAND_BOTH;

	/** centering options.  note that y is always centred... */
	public static final int X_LEFT   = 0;
	public static final int X_RIGHT  = 1;
	public static final int X_CENTRE = 2;
	private int centreX = X_CENTRE;
	
	private String centreXString(int centreX)
	{
		switch (centreX)
		{
			case X_LEFT:
				return "LEFT";
			case X_RIGHT:
				return "RIGHT";
			case X_CENTRE:
				return "CENTRE";
			default:
				return "UNKOWN";
		}
	}

	/** internal centring option */
	private int internalCentreX = X_CENTRE;
	
	private String name;
	private UDimension minimumDimensions;
	private UBounds layoutBounds;
	
	public ExpandingBox(String name, UDimension minimumDimensions)
	{
		this.name = name;
		this.minimumDimensions = minimumDimensions;
	}
	
	public ExpandingBox(String name, UDimension minimumDimensions, int expand, int centreX)
	{
		this.name = name;
		this.minimumDimensions = minimumDimensions;
		this.expand = expand;
		this.centreX = centreX;
	}

	public ExpandingBox(String name, UDimension minimumDimensions, int expand, int centreX, int internalExpand, int internalCentreX)
	{
		this.name = name;
		this.minimumDimensions = minimumDimensions;
		this.expand = expand;
		this.centreX = centreX;
		this.internalExpand = internalExpand;
		this.internalCentreX = internalCentreX;
	}
	
	/**
	 * @see com.hopstepjump.layout.Box#computeLayoutBounds(UBounds)
	 */
	public void computeLayoutBounds(UBounds fullSpace) throws LayoutException
	{
		// if the full space doesn't cover the immovable area, we have a problem
		UDimension dim = fullSpace.getDimension();
		if (!dim.maxOfEach(minimumDimensions).equals(dim))
			throw new LayoutException("Allocated dimensions for expanding box " + name + " = " + dim + ", which doesn't cover the required dimensions correctly = " + minimumDimensions);

		// depending on what the options are, choose a dimension
		switch (expand)
		{
			case EXPAND_BOTH:
				// already set to full space
				break;
				
			case EXPAND_X:
				dim = new UDimension(dim.getWidth(), minimumDimensions.getHeight());
				break;
				
			case EXPAND_Y:
				dim = new UDimension(minimumDimensions.getWidth(), dim.getHeight());
				break;
				
			case EXPAND_NONE:
				dim = minimumDimensions;
				break;
		}
		
		// depending on the options, centre the box
		UPoint pt = fullSpace.getPoint();
		double yOffset = (fullSpace.getHeight() - dim.getHeight()) / 2;

		switch (centreX)
		{
			case X_LEFT:
				pt = pt.add(new UDimension(0, yOffset));
				break;
			case X_RIGHT:
				double xOffset1 = fullSpace.getWidth() - dim.getWidth();
				pt = pt.add(new UDimension(xOffset1, yOffset));
				break;
			case X_CENTRE:
				double xOffset2 = (fullSpace.getWidth() - dim.getWidth()) / 2;
				pt = pt.add(new UDimension(xOffset2, yOffset));
				break;
		}
		
		// we have all the information, now set the outside box's bounds
		UBounds fullBounds = new UBounds(pt, dim);
		
		// now, use the internal options to place the actual layout box inside the expanding box
		dim = fullBounds.getDimension();
		if (!dim.maxOfEach(minimumDimensions).equals(dim))
			throw new LayoutException("Allocated dimensions for expanding box = " + dim + ", which doesn't cover the required dimensions correctly = " + minimumDimensions);

		// depending on what the options are, choose a dimension
		switch (internalExpand)
		{
			case EXPAND_BOTH:
				// already set to full space
				break;
				
			case EXPAND_X:
				dim = new UDimension(dim.getWidth(), minimumDimensions.getHeight());
				break;
				
			case EXPAND_Y:
				dim = new UDimension(minimumDimensions.getWidth(), dim.getHeight());
				break;
				
			case EXPAND_NONE:
				dim = minimumDimensions;
				break;
		}
		
		// depending on the options, centre the box
		pt = fullBounds.getPoint();
		yOffset = (fullBounds.getHeight() - dim.getHeight()) / 2;

		switch (internalCentreX)
		{
			case X_LEFT:
				pt = pt.add(new UDimension(0, yOffset));
				break;
			case X_RIGHT:
				double xOffset1 = fullBounds.getWidth() - dim.getWidth();
				pt = pt.add(new UDimension(xOffset1, yOffset));
				break;
			case X_CENTRE:
				double xOffset2 = (fullBounds.getWidth() - dim.getWidth()) / 2;
				pt = pt.add(new UDimension(xOffset2, yOffset));
				break;
		}		
		
		layoutBounds = new UBounds(pt, dim);
	}

	/**
	 * @see com.hopstepjump.layout.Box#getMinimumDimensions()
	 */
	public UDimension getMinimumDimensions()
	{
		return minimumDimensions;
	}

	/**
	 * @see com.hopstepjump.layout.Box#isXExpandable()
	 */
	public boolean isXExpandable()
	{
		return expand == EXPAND_BOTH || expand == EXPAND_X;
	}

	/**
	 * @see com.hopstepjump.layout.Box#isYExpandable()
	 */
	public boolean isYExpandable()
	{
		return expand == EXPAND_BOTH || expand == EXPAND_Y;
	}

	/**
	 * @see com.hopstepjump.layout.Box#setBoundsInBean(BeanBoundsSetter)
	 */
	public void setBoundsInBean(BeanBoundsSetter setter) throws LayoutException
	{
		setter.setBounds(name, layoutBounds);
	}
	
	/**
	 * @see com.hopstepjump.layout.Box#getImmovableBox()
	 */
	public ImmovableBox getImmovableBox()
	{
		return null;
	}

	/**
	 * @see com.hopstepjump.layout.Box#getLayoutBounds()
	 */
	public UBounds getLayoutBounds()
	{
		return layoutBounds;
	}

	/**
	 * @see com.hopstepjump.layout.Box#getLayoutBounds(String)
	 */
	public UBounds getLayoutBounds(String name)
	{
		if (name.equals(this.name))
			return layoutBounds;
		return null;
	}
	
	/**
	 * @see com.hopstepjump.layout.Box#getName()
	 */
	public String getName()
	{
		return name;
	}
	
	public String toString(int level)
	{
		return "ExpandingBox (name=" + name + ", minDimensions=" + minimumDimensions + ", expand=" + expansionString(expand) + ", centreX=" + centreXString(centreX) + ", intExpand=" +
		       expansionString(internalExpand) + ", intCentreX=" + centreXString(internalCentreX) + ") layout=" + layoutBounds;
	}
	
	/**
	 * @see com.hopstepjump.layout.LayoutBox#offsetLayoutBounds(UDimension)
	 */
	public void offsetLayoutBounds(UDimension offset)
	{
			layoutBounds = layoutBounds.addToPoint(offset);
	}
}
