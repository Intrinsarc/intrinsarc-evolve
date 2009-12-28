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
		Set includedFigures = DeleteFromDiagramHelper.getFigureIdsIncludedInDelete(selection.getSelectedFigures(), selection, false);
		
		Command cmd = new DeleteCommandGenerator(
		    diagramView.getDiagram().getDiagramReference(),
		    includedFigures,
		    "Delete Key deletion",
		    "deleted figures from diagram",
		    "restored deleted figures in diagram").generateCommand();
		coordinator.executeCommandAndUpdateViews(cmd);
	}

	static class DeleteCommandGenerator
	{
		private DiagramReference srcReference;
		private Set includedDeleteFigureIds;
		private String name;
		private String executeDescription;
		private String unexecuteDescription;
	
		public DeleteCommandGenerator(DiagramReference srcReference, Set includedDeleteFigureIds, String name, String executeDescription, String unexecuteDescription)
		{
			this.srcReference = srcReference;
			this.includedDeleteFigureIds = includedDeleteFigureIds;
			this.name = name;
			this.executeDescription = executeDescription;
			this.unexecuteDescription = unexecuteDescription;		
		}
	
		public Command generateCommand()
		{
			return DeleteFromDiagramHelper.makeDeleteCommand(name, executeDescription, unexecuteDescription, GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(srcReference), includedDeleteFigureIds, true);
		}
	}
}
