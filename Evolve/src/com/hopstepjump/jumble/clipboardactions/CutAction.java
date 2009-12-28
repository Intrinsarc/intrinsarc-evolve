package com.hopstepjump.jumble.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;

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

		Collection includedCopyFigureIds = CopyToDiagramHelper.getFigureIdsIncludedInSelectionCopy(diagramView);
		SelectionFacet selection = diagramView.getSelection();
		Set includedDeleteFigureIds = DeleteFromDiagramHelper.getFigureIdsIncludedInDelete(selection.getSelectedFigures(), selection, false);		

		// don't go further if we have nothing to delete
		if (includedDeleteFigureIds.isEmpty())
			return;

		DiagramFacet src = diagramView.getDiagram();
		
		Command copy = new CopyAction.CopyCommandGenerator(
		    includedCopyFigureIds,
		    clipboard.getDiagramReference(),
		    src.getDiagramReference()).generateCommand();
		
		// delete the elements now
    Command delete = new DeleteAction.DeleteCommandGenerator(
        src.getDiagramReference(),
        includedDeleteFigureIds,
        "CutKey deletion",
        "cut figures from diagram",
        "restored cut figures in diagram").generateCommand();

    CompositeCommand composite = new CompositeCommand(delete);
    composite.addCommand(copy);
    coordinator.executeCommandAndUpdateViews(composite);

    // copy the clipboard diagram, as a metafile, to the system clipboard
		CopyAction.copyDiagramToClipboardAsMetafile(clipboard, diagramView.getAdorners());
	}

}
