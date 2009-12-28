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
		Collection<String> figureIdsToDelete = CopyToDiagramHelper.getFigureIdsIncludedInSelection(diagramView, true);
		Set<String> includedFigureIds = DeleteFromDiagramHelper.getFigureIdsIncludedInDelete(selection.getSelectedFigures(), selection, true);
		
		Command kill = new KillCommandGenerator(
		    coordinator,
		    diagramView.getDiagram().getDiagramReference(),
		    figureIdsToDelete,
		    includedFigureIds).generateCommand();
		
		if (!kill.isEmpty())
		  coordinator.executeCommandAndUpdateViews(kill);
	}

	public static class KillCommandGenerator
	{
		private DiagramReference diagramReference;
		private Collection<String> figureIdsInSelection;
		private Set<String> figureIdsIncludedInDelete;
		private ToolCoordinatorFacet coordinator;
		
		public KillCommandGenerator(ToolCoordinatorFacet coordinator, DiagramReference diagramReference, Collection<String> figureIdsInSelection, Set<String> figureIdsIncludedInDelete)
		{
			this.diagramReference = diagramReference;
			this.figureIdsInSelection = figureIdsInSelection;
			this.figureIdsIncludedInDelete = figureIdsIncludedInDelete;
			this.coordinator = coordinator;
		}
		
		/*
		 * @see com.hopstepjump.idraw.foundation.CommandGeneratorFacet#generateCommand()
		 */
		public Command generateCommand()
		{
			final Set<Element> subjectsToDelete = new HashSet<Element>();
			DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(diagramReference);
		
			final Set<FigureFacet> specialFigures = new HashSet<FigureFacet>();
			for (String id : figureIdsInSelection)
			{
				FigureFacet figure = diagram.retrieveFigure(id);
				Object subject = figure.getSubject();
				if (figure.getClipboardCommandsFacet() != null && figure.getClipboardCommandsFacet().hasSpecificKillCommand())
				  specialFigures.add(figure);
				else
				if (subject != null && subject instanceof Element)
					subjectsToDelete.add((Element) subject);
			}
		
			// return a command to delete all the subjects, if they exists
			// NOTE: deleting one subject may cause another to "disappear" also
			CompositeCommand command = new CompositeCommand("Subject deletion", "deleted subjects", "restored subjects");
			Command killCommand =
				new AbstractCommand("", "")
				{
					public void execute(boolean isTop)
					{
						SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
						for (Element element : subjectsToDelete)
								repository.incrementPersistentDelete(element);
					}
				
					public void unExecute()
					{
            SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
            for (Element element : subjectsToDelete)
                repository.decrementPersistentDelete(element);
					}
				};
				if (!subjectsToDelete.isEmpty())
				  command.addCommand(killCommand);

			// now, delete any elements which are highlighted, just to give the impression that kill is a superset of delete
			// only removes elements from diagram which don't have a specific kill command
			command.addCommand(DeleteFromDiagramHelper.makeDeleteCommand(
			    "", "", "", diagram, figureIdsIncludedInDelete, false));
			
			// add the specific delete commands
			for (FigureFacet specific : specialFigures)
			  command.addCommand(specific.getClipboardCommandsFacet().makeSpecificKillCommand(coordinator));
			
			return command;
		}
	}
	
	public static boolean allFiguresWritable(Set<FigureFacet> selectedFigures)
  {
    for (FigureFacet figure : selectedFigures)
      if (!DeleteFromDiagramHelper.hasSpecialKillCommand(figure) && figure.isSubjectReadOnlyInDiagramContext(true))
        return false;
    return true;
  }
}
