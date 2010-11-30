package com.intrinsarc.idraw.diagramsupport;

import java.util.*;

import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utilities.*;

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
    
    List<FigureFacet> containers = getTopLevelContainers(bothIds, diagram);
    for (String id : topLevelIds)
			{
				FigureFacet figure = diagram.possiblyRetrieveFigure(id);
        if (figure != null)
          diagram.remove(figure);  // removes children also
			}

		addContainerResizingCommands(containers);
	}

	private static List<FigureFacet> getTopLevelContainers(Collection<String> topLevelIds, DiagramFacet diagram)
	{
		List<FigureFacet> containers = new ArrayList<FigureFacet>();
		for (String figureId : topLevelIds)
		{
			FigureFacet figure = diagram.possiblyRetrieveFigure(figureId);			
			if (figure != null && figure.getContainedFacet() != null && figure.getContainedFacet().getContainer() != null)
				containers.add(figure.getContainedFacet().getContainer().getFigureFacet());
		}
		return containers;
	}

	private static void addContainerResizingCommands(List<FigureFacet> containers)
	{
		for (FigureFacet f : containers)
		{
			ResizingFiguresGem gem = new ResizingFiguresGem(null, f.getDiagram());
			ResizingFiguresFacet facet = gem.getResizingFiguresFacet();
			facet.markForResizing(f);
			facet.recalculateSizeForContainables();
			facet.end();
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
