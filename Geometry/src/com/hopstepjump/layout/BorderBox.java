package com.hopstepjump.layout;

import com.hopstepjump.geometry.*;

/**
 *
 * (c) Andrew McVeigh 04-Dec-02
 *
 */
public class BorderBox extends ContainerBox
{
	public BorderBox(String name, UDimension inset, LayoutBox contents)
	{
		super(name, new VerticalLayout());
		setTopLeftInset(inset);
		setBottomRightInset(inset);
		addBox(contents);
	}
	
	public BorderBox(String name, UDimension topLeftInset, UDimension bottomRightInset, LayoutBox contents)
	{
		super(name, new VerticalLayout());
		setTopLeftInset(topLeftInset);
		setBottomRightInset(bottomRightInset);
		addBox(contents);
	}

	/**
	 * @see com.hopstepjump.layout.Box#toString(int)
	 */
	protected String getType()
	{
		return "BorderBox";
	}
}
