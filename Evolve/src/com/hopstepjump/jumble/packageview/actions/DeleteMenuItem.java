/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.hopstepjump.jumble.packageview.actions;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.clipboardactions.*;
import com.hopstepjump.swing.enhanced.*;

/**
 * @author Andrew
 */
public class DeleteMenuItem extends UpdatingJMenuItem
{
	private ToolCoordinatorFacet toolCoordinator;
	private DiagramViewFacet diagramView;

	/**
	 * @param currentView
	 * @param toolCoordinatorFacet
	 */
	public DeleteMenuItem(DiagramViewFacet diagramView, ToolCoordinatorFacet toolCoordinator)
	{
		super(new CursorWaitingAction(new DeleteAction(toolCoordinator, diagramView), toolCoordinator, 0));
		this.diagramView = diagramView;
		this.toolCoordinator = toolCoordinator;		
	}

	/*
	 * @see com.hopstepjump.swing.enhanced.UpdatingJMenuItem#update(boolean)
	 */
	public boolean update()
	{
		return
			!diagramView.getSelection().getSelectedFigures().isEmpty() && !diagramView.getDiagram().isReadOnly();
	}
}
