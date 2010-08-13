/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.swing.enhanced.*;

/**
 * @author Andrew
 */
public class LocateElementsMenuItem extends UpdatingJMenuItem
{
	private ToolCoordinatorFacet toolCoordinator;
	private DiagramViewFacet diagramView;

	/**
	 * @param currentView
	 * @param toolCoordinatorFacet
	 */
	public LocateElementsMenuItem(DiagramViewFacet diagramView, ToolCoordinatorFacet toolCoordinator)
	{
		super(new CursorWaitingAction(new LocateElementsAction(toolCoordinator, diagramView), toolCoordinator, 0));
		this.diagramView = diagramView;
		this.toolCoordinator = toolCoordinator;		
	}

	/*
	 * @see com.intrinsarc.swing.enhanced.UpdatingJMenuItem#update(boolean)
	 */
	public boolean update()
	{
		return
			!diagramView.getSelection().getSelectedFigures().isEmpty() &&
			!diagramView.getDiagram().isReadOnly() &&
			LocateElementsAction.allFiguresWritable(diagramView.getSelection().getSelectedFigures());
	}
}
