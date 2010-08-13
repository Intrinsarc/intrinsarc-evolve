/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import com.intrinsarc.evolve.clipboardactions.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.swing.enhanced.*;

/**
 * @author Andrew
 */
public class CopyMenuItem extends UpdatingJMenuItem
{
	private ToolCoordinatorFacet toolCoordinator;
	private DiagramViewFacet diagramView;

	/**
	 * @param currentView
	 * @param toolCoordinatorFacet
	 */
	public CopyMenuItem(DiagramViewFacet diagramView, DiagramFacet clipboard, ToolCoordinatorFacet toolCoordinator)
	{
		super(new CursorWaitingAction(
		    new CopyAction(
		        toolCoordinator,
		        diagramView,
		        clipboard),
		        toolCoordinator,
		        0));
		this.diagramView = diagramView;
		this.toolCoordinator = toolCoordinator;		
	}

	/*
	 * @see com.hopstepjump.swing.enhanced.UpdatingJMenuItem#update(boolean)
	 */
	public boolean update()
	{
		return
			!diagramView.getSelection().getSelectedFigures().isEmpty();
	}
}