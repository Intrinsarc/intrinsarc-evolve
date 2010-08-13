package com.intrinsarc.evolve.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.evolve.clipboardactions.CopyAction.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 22-Jan-03
 *
 */

public class CutAction extends AbstractAction
{
	private DiagramViewFacet diagramView;
	private ToolCoordinatorFacet coordinator;
  private DiagramFacet clipboard;

	public CutAction(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, DiagramFacet clipboard)
	{
		super("Cut");
		this.coordinator = coordinator;
		this.diagramView = diagramView;
		this.clipboard = clipboard;
	}

	public void actionPerformed(ActionEvent e)
	{
	   if (diagramView.getDiagram().isReadOnly())
	      return;

		Collection<String> includedCopyFigureIds = CopyToDiagramUtilities.getFigureIdsIncludedInSelectionCopy(diagramView);
		SelectionFacet selection = diagramView.getSelection();
		Set<String> includedDeleteFigureIds = DeleteFromDiagramTransaction.getFigureIdsIncludedInDelete(selection.getSelectedFigures(), selection, false);		

		// don't go further if we have nothing to delete
		if (includedDeleteFigureIds.isEmpty())
			return;

		DiagramFacet src = diagramView.getDiagram();
		
		coordinator.startTransaction(
        "cut figures from diagram",
        "restored cut figures in diagram");
		CopyCommandGenerator.copy(
		    includedCopyFigureIds,
		    clipboard,
		    src);
		DeleteFromDiagramTransaction.delete(diagramView.getDiagram(), includedDeleteFigureIds, true);
		coordinator.commitTransaction();

    // copy the clipboard diagram, as a metafile, to the system clipboard
		CopyAction.copyDiagramToClipboardAsMetafile(clipboard, diagramView.getAdorners());
	}

}
