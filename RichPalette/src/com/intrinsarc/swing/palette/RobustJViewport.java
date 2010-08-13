package com.intrinsarc.swing.palette;

import java.awt.*;

import javax.swing.*;

/**
 * prevents the need to keep a reasonably sized JPanel in the middle of the viewport
 * @author andrew
 *
 */
public class RobustJViewport extends JViewport
{
	private boolean ignoreSizings = true;

	public RobustJViewport()
	{
	}
	
	public void setIgnoreResizings(boolean ignore)
	{
		this.ignoreSizings = ignore;
	}

	@Override
	public void setViewPosition(Point p)
	{
		if (!ignoreSizings)
			super.setViewPosition(p);
	}
}
