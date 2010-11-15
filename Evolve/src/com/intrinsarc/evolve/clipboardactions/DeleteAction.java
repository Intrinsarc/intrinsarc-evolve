package com.intrinsarc.evolve.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.foundation.*;

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
		super("Delete views only");
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
		    "deleted views from diagram",
		    "restored deleted views in diagram");
		DeleteFromDiagramTransaction.delete(diagramView.getDiagram(), includedFigures, true);
		coordinator.commitTransaction();
	}
}
