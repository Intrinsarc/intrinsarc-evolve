/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import javax.swing.*;

import com.intrinsarc.idraw.foundation.*;

/**
 * @author Andrew
 */
public class SelectAllMenuItem extends JMenuItem
{
	public SelectAllMenuItem(DiagramViewFacet diagramView, ToolCoordinatorFacet toolCoordinator)
	{
		super(new CursorWaitingAction(new SelectAllAction(diagramView),
																	toolCoordinator, 200 /* wait before showing busy */));
	}
}
