package com.hopstepjump.jumble.clipboardactions;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;

import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 07-Feb-03
 *
 */
public class DeleteSubjectsAction extends AbstractAction
{
	private DiagramViewFacet diagramView;
	private ToolCoordinatorFacet coordinator;

	public DeleteSubjectsAction(ToolCoordinatorFacet coordinator, final DiagramViewFacet diagramView)
	{
		super("Delete subjects");
		this.coordinator = coordinator;
		this.diagramView = diagramView;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
	   if (diagramView.getDiagram().isReadOnly())
	      return;
	   if (!allFiguresWritable(diagramView.getSelection().getTopLevelSelectedFigures()))
	     return;
	  
		SelectionFacet selection = diagramView.getSelection();
		Collection<String> figureIdsToDelete = CopyToDiagramUtilities.getFigureIdsIncludedInSelection(diagramView, true);
		Set<String> includedFigureIds = DeleteFromDiagramTransaction.getFigureIdsIncludedInDelete(selection.getSelectedFigures(), selection, true);
		
		coordinator.startTransaction("deleted subject", "restored subjects");
		KillTransaction.kill(
		    coordinator,
		    diagramView.getDiagram(),
		    figureIdsToDelete,
		    includedFigureIds);		
		coordinator.commitTransaction();
	}
	
	public static boolean allFiguresWritable(Set<FigureFacet> selectedFigures)
  {
    for (FigureFacet figure : selectedFigures)
      if (!DeleteFromDiagramTransaction.hasSpecialKillCommand(figure) && figure.isSubjectReadOnlyInDiagramContext(true))
        return false;
    return true;
  }
}
