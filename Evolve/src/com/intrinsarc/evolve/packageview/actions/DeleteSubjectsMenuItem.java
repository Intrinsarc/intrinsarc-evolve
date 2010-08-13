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
public class DeleteSubjectsMenuItem extends UpdatingJMenuItem
{
	private ToolCoordinatorFacet toolCoordinator;
	private DiagramViewFacet diagramView;

	/**
	 * @param currentView
	 * @param toolCoordinatorFacet
	 */
	public DeleteSubjectsMenuItem(DiagramViewFacet diagramView, ToolCoordinatorFacet toolCoordinator)
	{
		super(new CursorWaitingAction(new DeleteSubjectsAction(toolCoordinator, diagramView), toolCoordinator, 0));
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
			DeleteSubjectsAction.allFiguresWritable(diagramView.getSelection().getTopLevelSelectedFigures());
	}
}