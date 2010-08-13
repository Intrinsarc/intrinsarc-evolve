package com.intrinsarc.easydock.dockingframes;

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

import bibliothek.extension.gui.dock.theme.eclipse.rex.*;
import bibliothek.extension.gui.dock.theme.eclipse.rex.tab.*;
import bibliothek.gui.*;
import bibliothek.gui.dock.*;

/**
 * @author Janni Kovacs, amended by Andrew Mcveigh
 */
public class DockingFramesRectGradientPainter extends RectGradientPainter
{
	public DockingFramesRectGradientPainter( RexTabbedComponent component, DockController controller, StackDockStation station, Dockable dockable, int index )
	{
    super( component, controller, station, dockable, index );
	}

	public static final TabPainter FACTORY = new TabPainter()
	{
		private final DockingFramesRectEclipseBorder border = new DockingFramesRectEclipseBorder(true);

		public TabComponent createTabComponent(DockController controller,
				RexTabbedComponent component, StackDockStation station,
				Dockable dockable, int index)
		{
			return new DockingFramesRectGradientPainter(component, controller, station, dockable, index);
		}

		public void paintTabStrip(RexTabbedComponent tabbedComponent, Component tabStrip, Graphics g)
		{
		}

		public Border getFullBorder(DockController controller, Dockable dockable)
		{
			return border;
		}

		public Border getFullBorder(DockController controller, DockStation station, RexTabbedComponent component)
		{
			return border;
		}

		public TabStripPainter createTabStripPainter(final RexTabbedComponent tabbedComponent)
		{
			return new TabStripPainter()
			{
				private DockController controller;
				
				public void paintTabStrip(Component tabStrip, Graphics g)
				{
					int selectedIndex = tabbedComponent.getSelectedIndex();
					if (selectedIndex != -1)
					{
						Rectangle selectedBounds = tabbedComponent.getBoundsAt(selectedIndex);
						int to = selectedBounds.x;
						int from = selectedBounds.x + selectedBounds.width - 1;
						int end = tabStrip.getWidth();
						Color lineColor = Color.LIGHT_GRAY;
						g.setColor(lineColor);
						int y = tabStrip.getHeight() - 1;

						if (to != 0)
							g.drawLine(-1, y, to - 1, y);
						if (from != end)
							g.drawLine(from, y, end, y);
						border.setTabHeight(tabStrip.getHeight());
					}
				}

				public void setController(DockController controller)
				{
					this.controller = controller;
				}				
			};
		}
	};
}
