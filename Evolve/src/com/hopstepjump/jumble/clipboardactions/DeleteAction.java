package com.hopstepjump.jumble.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 04-Feb-03
 *
 */
public class DeleteAction extends AbstractAction
{
	private ToolCoordinatorFacet coordinator;
	private DiagramViewFacet diagramView;

	public DeleteAction(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView)
	{
		super("Delete");
		this.coordinator = coordinator;
		this.diagramView = diagramView;
	}

	public void actionPerformed(ActionEvent e)
	{
	   if (diagramView.getDiagram().isReadOnly())
	      return;

		SelectionFacet selection = diagramView.getSelection();
		Set includedFigures = DeleteFromDiagramTransaction.getFigureIdsIncludedInDelete(selection.getSelectedFigures(), selection, false);
		
		coordinator.startTransaction(
		    "deleted figures from diagram",
		    "restored deleted figures in diagram");
		DeleteFromDiagramTransaction.delete(diagramView.getDiagram(), includedFigures, true);
		coordinator.commitTransaction();
	}
}
