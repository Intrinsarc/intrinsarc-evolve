package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 07-Feb-03
 *
 */
public class DeleteFromDiagramHelper
{
	public static Command makeDeleteCommand(String name, String executeDescription, String unExecuteDescription, DiagramFacet diagram, Set includedFigureIds, boolean runPostDeleteCommands)
	{
		// only proceed if we actually have some figures to consider
		if (includedFigureIds.isEmpty())
			return null;

		final Collection<PersistentFigure> persistentFigures =
		  CopyToDiagramHelper.makePersistentFiguresFromIds(diagram, includedFigureIds);
		final Collection<String> topLevelIds = CopyToDiagramHelper.getTopLevelFigureIdsOnly(diagram, includedFigureIds, CopyToDiagramHelper.WANT_NON_SPECIFIC, true);
    final Collection<String> specificTopLevelIds = CopyToDiagramHelper.getTopLevelFigureIdsOnly(diagram, includedFigureIds, CopyToDiagramHelper.WANT_SPECIFIC, true);
    final Set<String> bothIds = new HashSet<String>(topLevelIds);
		final DiagramReference diagramReference = diagram.getDiagramReference();
		
		bothIds.addAll(specificTopLevelIds);
		
		// make the command
		CompositeCommand deletionCommand = new CompositeCommand(name, executeDescription, unExecuteDescription);

		// add the specific delete command
    for (String specificTopLevelId : specificTopLevelIds)
    {
      FigureFacet figure = diagram.retrieveFigure(specificTopLevelId);
      deletionCommand.addCommand(figure.getClipboardCommandsFacet().makeSpecificDeleteCommand());
    }
    
    // ask for any specific post delete commands
    if (runPostDeleteCommands)
      for (String topLevelId : bothIds)
      {
        FigureFacet figure = diagram.retrieveFigure(topLevelId);
        if (figure.getClipboardCommandsFacet() != null)
          deletionCommand.addCommand(figure.getClipboardCommandsFacet().makePostDeleteCommand());
      }
    
		deletionCommand.addCommand(new AbstractCommand("", "")
		{
			public void execute(boolean isTop)
			{
				DiagramFacet actual = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(diagramReference);
				for (Iterator iter = topLevelIds.iterator(); iter.hasNext();)
				{
					String id = (String) iter.next();
					FigureFacet figure = actual.possiblyRetrieveFigure(id);
          if (figure != null)
            actual.remove(figure);  // removes children also
				}
			}
			
			public void unExecute()
			{
				DiagramFacet actual = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(diagramReference);
				actual.addPersistentFigures(persistentFigures, new UDimension(0,0));
			}
		});

		addContainerResizingCommands(bothIds, diagram, deletionCommand);

		return deletionCommand;
	}

	private static void addContainerResizingCommands(Collection topLevelIds, DiagramFacet diagram, CompositeCommand deletionCommand)
	{
		for (Iterator iter = topLevelIds.iterator(); iter.hasNext();)
		{
			String figureId = (String) iter.next();
			FigureFacet figure = diagram.retrieveFigure(figureId);
			
			// if this has a container, then make sure it gets resized
			if (figure.getContainedFacet() != null && figure.getContainedFacet().getContainer() != null)
			{
				FigureFacet container = figure.getContainedFacet().getContainer().getFigureFacet();
				final FigureReference containerReference = container.getFigureReference();
	
				deletionCommand.addCommand(new AbstractCommand("", "")
				{
					private Command resizingCommand;
					
					public void execute(boolean isTop)
					{
						FigureFacet figure = GlobalDiagramRegistry.registry.retrieveFigure(containerReference);
						ResizingFiguresGem gem = new ResizingFiguresGem(null, figure.getDiagram());
						ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
						facet.markForResizing(figure);
						facet.recalculateSizeForContainables();
						resizingCommand = facet.end("", "");
						resizingCommand.execute(false);
					}
					
					public void unExecute()
					{
						resizingCommand.unExecute();
						resizingCommand = null;
					}
				});
			}
		}
	}

	public static Set<String> getFigureIdsIncludedInDelete(Collection<FigureFacet> figures, ChosenFiguresFacet chosenFigures, boolean ignoreSpecialKills)
	{
		Set<FigureFacet> figuresToDelete = new HashSet<FigureFacet>();
		
		// need to work out which figures are involved
		for (FigureFacet figure : figures)
		{
			ClipboardFacet clip = figure.getClipboardFacet();
			if (clip != null && !(ignoreSpecialKills && hasSpecialKillCommand(figure)))
				clip.addIncludedInDelete(figuresToDelete, chosenFigures, false);
		}
		
		Set<String> ids = new HashSet<String>();
		for (FigureFacet figure : figuresToDelete)
			ids.add(figure.getId());
			
		return ids;
	}

  public static boolean hasSpecialKillCommand(FigureFacet figure)
  {
    return
      figure.getClipboardCommandsFacet() != null &&
      figure.getClipboardCommandsFacet().hasSpecificKillCommand();
  }
}
