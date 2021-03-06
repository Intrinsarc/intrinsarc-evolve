/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import javax.swing.*;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.enhanced.*;

/**
 * @author Andrew
 */
public class TabGotoCurrentMenuItem extends UpdatingJMenuItem
{
	private static final ImageIcon TAB_RIGHT_ICON = IconLoader.loadIcon("tab_right.png");

	public TabGotoCurrentMenuItem(DiagramFacet diagram, ToolCoordinatorFacet toolCoordinatorFacet)
	{
		super(
				new CursorWaitingAction(
						new GotoDiagramAction(
								diagram,
								"Open current in new tab",
								true,
								false,
								true),
				toolCoordinatorFacet, 0));
		setIcon(TAB_RIGHT_ICON);
	}
	
	/*
	 * @see com.intrinsarc.swing.enhanced.UpdatingJMenuItem#update(boolean)
	 */
	public boolean update()
	{
		return true;
	}
}	    
