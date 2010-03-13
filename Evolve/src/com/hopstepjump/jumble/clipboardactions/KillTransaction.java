package com.hopstepjump.jumble.clipboardactions;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

	public class KillTransaction implements TransactionFacet
	{
		public static void kill(ToolCoordinatorFacet coordinator, DiagramFacet diagram, Collection<String> figureIdsInSelection, Set<String> figureIdsIncludedInDelete)
		{
			final Set<Element> subjectsToDelete = new HashSet<Element>();
		
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
			SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
			for (Element element : subjectsToDelete)
					repository.incrementPersistentDelete(element);

			// now, delete any elements which are highlighted, just to give the impression that kill is a superset of delete
			// only removes elements from diagram which don't have a specific kill command
			DeleteFromDiagramTransaction.delete(
			   diagram, figureIdsIncludedInDelete, false);
			
			// add the specific delete commands
			for (FigureFacet specific : specialFigures)
			 specific.getClipboardCommandsFacet().makeSpecificKillCommand(coordinator);
		}
	}