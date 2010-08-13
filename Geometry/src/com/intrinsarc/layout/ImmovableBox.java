package com.intrinsarc.layout;
import com.intrinsarc.geometry.*;

public class ImmovableBox implements LayoutBox
{
	private String name;
	private UBounds minimumBounds;
	private UBounds layoutBounds;
	
	public ImmovableBox(String name, UBounds minBounds)
	{
		this.name = name;
		this.minimumBounds = minBounds;
	}
	
	/**
	 * @see com.intrinsarc.layout.Box#computeLayoutBounds(UBounds)
	 */
	public void computeLayoutBounds(UBounds fullSpace)
	{
		layoutBounds = fullSpace;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getMinimumDimensions()
	 */
	public UDimension getMinimumDimensions()
	{
		return minimumBounds.getDimension();
	}

	/**
	 * @see com.intrinsarc.layout.Box#isXExpandable()
	 */
	public boolean isXExpandable()
	{
		return true;
	}

	/**
	 * @see com.intrinsarc.layout.Box#isYExpandable()
	 */
	public boolean isYExpandable()
	{
		return true;
	}

	/**
	 * @see com.intrinsarc.layout.Box#setBoundsInBean(BeanBoundsSetter)
	 */
	public void setBoundsInBean(BeanBoundsSetter setter) throws LayoutException
	{
		setter.setBounds(name, layoutBounds);
	}
	
	/**
	 * @see com.intrinsarc.layout.Box#getImmovableBox()
	 */
	public ImmovableBox getImmovableBox()
	{
		return this;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getLayoutBounds()
	 */
	public UBounds getLayoutBounds()
	{
		return layoutBounds;
	}

	/**
	 * @see com.intrinsarc.layout.Box#getLayoutBounds(String)
	 */
	public UBounds getLayoutBounds(String name)
	{
		if (name.equals(this.name))
			return layoutBounds;
		return null;
	}
	
	/**
	 * Returns the minimumBounds.
	 * @return UBounds
	 */
	public UBounds getMinimumBounds()
	{
		return minimumBounds;
	}
	
	public void setMinimumBoundsTopLeft(UPoint topLeft)
	{
		UPoint bottomRight = minimumBounds.getBottomRightPoint();
		minimumBounds = new UBounds(topLeft, bottomRight.subtract(topLeft));
	}
	
	public void checkThatAllocatedLayoutCoversMinimumBounds() throws LayoutException
	{
		if (!layoutBounds.contains(minimumBounds))
			throw new LayoutException("Immovable allocated space = " + layoutBounds + ", does not cover " + minimumBounds);
	}
	
	/**
	 * @see com.intrinsarc.layout.Box#getName()
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @see com.intrinsarc.layout.Box#toString(int)
	 */
	public String toString(int level)
	{
		return "ImmovableBox (name=" + name + ", minBounds=" + minimumBounds + ") layout=" + layoutBounds;
	}

	/**
	 * @see com.intrinsarc.layout.LayoutBox#offsetLayoutBounds(UDimension)
	 */
	public void offsetLayoutBounds(UDimension offset)
	{
		layoutBounds = layoutBounds.addToPoint(offset);
	}

}
