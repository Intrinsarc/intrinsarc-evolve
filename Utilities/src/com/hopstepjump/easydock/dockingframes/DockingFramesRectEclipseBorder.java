package com.hopstepjump.easydock.dockingframes;

/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * 
 * Copyright (C) 2007 Benjamin Sigg
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */

import java.awt.*;

import javax.swing.border.*;

/**
 * This border paints a thin line. At the bottom, two hard edges are painted, at
 * the top the line makes a little curve.
 * 
 * @author Benjamin Sigg
 * 
 */
public class DockingFramesRectEclipseBorder implements Border
{
	private int tabHeight;
	
	/**
	 * Creates a new border.
	 * 
	 * @param fillEdges
	 *          whether the top edges should be filled with the background color
	 *          or let empty.
	 */
	public DockingFramesRectEclipseBorder(boolean fillEdges)
	{
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		g.setColor(Color.LIGHT_GRAY);
		// left
		g.drawLine(0, 2, 0, height - 1);
		// right
		g.drawLine(width - 1, tabHeight, width - 1, height - 1);
		// bottom
		g.drawLine(0, height - 1, width - 1, height - 1);
	}

	public Insets getBorderInsets(Component c)
	{
		return new Insets(0, 0, 0, 0);
	}

	public boolean isBorderOpaque()
	{
		return false;
	}

	public void setTabHeight(int height)
	{
		tabHeight = height;
	}
}
