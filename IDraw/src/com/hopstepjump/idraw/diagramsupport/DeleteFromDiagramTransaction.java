package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 07-Feb-03
 *
 */
public class DeleteFromDiagramTransaction
{
	public static void delete(DiagramFacet diagram, Set<String> includedFigureIds, boolean runPostDeleteCommands)
	{
		// only proceed if we actually have some figures to consider
		if (includedFigureIds.isEmpty())
			return;

		Collection<String> topLevelIds = CopyToDiagramUtilities.getTopLevelFigureIdsOnly(diagram, includedFigureIds, CopyToDiagramUtilities.WANT_NON_SPECIFIC, true);
    Collection<String> specificTopLevelIds = CopyToDiagramUtilities.getTopLevelFigureIdsOnly(diagram, includedFigureIds, CopyToDiagramUtilities.WANT_SPECIFIC, true);
    Set<String> bothIds = new HashSet<String>(topLevelIds);
		
		bothIds.addAll(specificTopLevelIds);
		
		// add any specific deletes
    for (String specificTopLevelId : specificTopLevelIds)
    {
      FigureFacet figure = diagram.retrieveFigure(specificTopLevelId);
      figure.getClipboardCommandsFacet().makeSpecificDeleteAction();
    }
    
    // do any specific post deletes
    if (runPostDeleteCommands)
      for (String topLevelId : bothIds)
      {
        FigureFacet figure = diagram.retrieveFigure(topLevelId);
        if (figure.getClipboardCommandsFacet() != null)
          figure.getClipboardCommandsFacet().performPostDeleteAction();
      }
    
			for (String id : topLevelIds)
			{
				FigureFacet figure = diagram.possiblyRetrieveFigure(id);
        if (figure != null)
          diagram.remove(figure);  // removes children also
			}

		addContainerResizingCommands(bothIds, diagram);
	}

	private static void addContainerResizingCommands(Collection<String> topLevelIds, DiagramFacet diagram)
	{
		for (String figureId : topLevelIds)
		{
			FigureFacet figure = diagram.possiblyRetrieveFigure(figureId);
			
			// if this has a container, then make sure it gets resized
			if (figure != null && figure.getContainedFacet() != null && figure.getContainedFacet().getContainer() != null)
			{
				FigureFacet container = figure.getContainedFacet().getContainer().getFigureFacet();
				final FigureReference containerReference = container.getFigureReference();
	
				FigureFacet cont = GlobalDiagramRegistry.registry.retrieveFigure(containerReference);
				ResizingFiguresGem gem = new ResizingFiguresGem(null, cont.getDiagram());
				ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
				facet.markForResizing(cont);
				facet.recalculateSizeForContainables();
				facet.end();
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
      figure.getClipboardCommandsFacet().hasSpecificKillAction();
  }
}
