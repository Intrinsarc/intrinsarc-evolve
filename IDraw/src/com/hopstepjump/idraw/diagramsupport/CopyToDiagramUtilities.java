package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 04-Feb-03
 *
 */
public class CopyToDiagramUtilities
{
  public static final int WANT_ALL = 0;
  public static final int WANT_NON_SPECIFIC = 1;
  public static final int WANT_SPECIFIC = 2;

  public static void selectFigures(DiagramViewFacet view, Collection<String> ids)
	{
		SelectionFacet selection = view.getSelection();
		selection.clearAllSelection();
		for (String id : ids)
			view.addFigureToSelectionViaId(id);
	}
	
	public static UBounds calculateFigureBounds(DiagramFacet diagram, Collection<String> includedFigureIds)
	{
		UBounds bounds = null;
		for (String id : includedFigureIds)
		{
			FigureFacet figure = diagram.retrieveFigure(id);
			if (bounds == null)
				bounds = figure.getFullBoundsForContainment();
			else
				bounds = bounds.union(figure.getFullBoundsForContainment());
		}
		return bounds;
	}
	
	public static Set<String> getFigureIdsIncludedInFigure(final FigureFacet focus)
	{
		Set<FigureFacet> figuresToCopy = new HashSet<FigureFacet>();
		
		// need to work out which figures are involved
		ClipboardFacet clip = focus.getClipboardFacet();
		if (clip != null)
			clip.addIncludedInCopy(figuresToCopy, new ChosenFiguresFacet()
			{
				public boolean isChosen(FigureFacet figure)
				{
					// if the parent is focus, then it is chosen
					while (figure != null)
					{
						if (figure == focus)
							return true;
						ContainedFacet contained = figure.getContainedFacet();
						if (contained == null)
							return false;
						ContainerFacet container = contained.getContainer();
						if (container == null)
							return false;
						figure = container.getFigureFacet();
					}
					return false;
				}
			}, false);
		
		Set<String> ids = new HashSet<String>();
		for (FigureFacet figure : figuresToCopy)
			ids.add(figure.getId());

		return ids;
	}
	
	public static Set<String> getFigureIdsIncludedInSelectionCopy(DiagramViewFacet srcView)
	{
		SelectionFacet selection = srcView.getSelection();
		Set<FigureFacet> figuresToCopy = new HashSet<FigureFacet>();
		
		// need to work out which figures are involved
		for (FigureFacet figure : selection.getSelectedFigures())
		{
			ClipboardFacet clip = figure.getClipboardFacet();
			if (clip != null)
				clip.addIncludedInCopy(figuresToCopy, selection, false);
		}
		
		Set<String> ids = new HashSet<String>();
		for (FigureFacet figure : figuresToCopy)
			ids.add(figure.getId());

		return ids;
	}
	
	public static Set<String> getFigureIdsIncludedInSelection(DiagramViewFacet srcView, boolean topLevelOnly)
	{
		SelectionFacet selection = srcView.getSelection();
		
		// need to work out which figures are involved
		Set<String> ids = new HashSet<String>();
		for (FigureFacet figure: topLevelOnly ? selection.getTopLevelSelectedFigures() : selection.getSelectedFigures())
			ids.add(figure.getId());

		return ids;
	}
	
	/**
   * 
   * @param diagram
   * @param figureIds
   * @param wantSpecificOnly true if we only want top level figures that have specific delete commands
   *        if false, we only want non-specific
   * @return
	 */
  public static Set<String> getTopLevelFigureIdsOnly(DiagramFacet diagram, Set<String> figureIds, int wantType, boolean includeLinks)
	{
		// now, filter out anything that isn't at the "top-level" for this deletion
		Set<String> topLevel = new HashSet<String>();
		for (String id : figureIds)
		{
			// note: figure may not be present due to "correction" algorithms combined with stratum perspective
			FigureFacet figure = diagram.possiblyRetrieveFigure(id);
			if (figure != null)
			{
				// if this has a parent which is included, don't use this
				if (figure.getContainedFacet() != null)
				{
					ContainerFacet container = figure.getContainedFacet().getContainer();
					if (container == null || !figureIds.contains(container.getFigureFacet().getId()))
	          considerAdding(topLevel, figure, wantType);
				}
				else
					// probably a link
					if (includeLinks)
	        considerAdding(topLevel, figure, wantType);
			}
		}
		return topLevel;
	}
	
	/**
   * @param topLevel
   * @param figure
   * @param wantSpecificOnly
   */

  private static void considerAdding(Set<String> topLevel, FigureFacet figure, int wantType)
  {
    boolean hasSpecific = figure.getClipboardCommandsFacet() != null && figure.getClipboardCommandsFacet().hasSpecificDeleteAction(); 
    if (wantType == WANT_ALL ||
        wantType == WANT_SPECIFIC && hasSpecific ||
        wantType == WANT_NON_SPECIFIC && !hasSpecific)
      topLevel.add(figure.getId());
  }


  public static Collection<String> getAllFigureIdsInDiagram(DiagramFacet diagram)
	{
		PersistentDiagram persistentDiagram = diagram.makePersistentDiagram();
		List<String> ids = new ArrayList<String>();
		for (PersistentFigure persistentFigure : persistentDiagram.getFigures())
			ids.add(persistentFigure.getId());

		return ids;
	}

	public static Collection<PersistentFigure> makePersistentFiguresFromIds(DiagramFacet diagram, Collection<String> figureIdsToCopy)
	{
		// make the persistent figures
		Collection<PersistentFigure> persistentFigures = new ArrayList<PersistentFigure>();
		for (String id : figureIdsToCopy)
		{
			FigureFacet figure = diagram.retrieveFigure(id);
			persistentFigures.add(figure.makePersistentFigure());
		}
		return persistentFigures;
	}		

	public static Collection<PersistentFigure>
		makePersistentFiguresAndAssignNewIds(DiagramFacet src, Collection<String> figureIdsToCopy, DiagramFacet destination, boolean removeIdsNotInSet)
	{
		// make the persistent figures
		Map<String, PersistentFigure> persistentFigures = new HashMap<String, PersistentFigure>();
		for (String id : figureIdsToCopy)
		{
			FigureFacet figure = src.retrieveFigure(id);
			persistentFigures.put(figure.getId(), figure.makePersistentFigure());
		}
		
		// allocate new ids
		for (PersistentFigure figure : persistentFigures.values())
			figure.setId(destination.makeNewFigureReference().getId());
		
		// translate any ids in the persistent figures
		for (PersistentFigure figure : persistentFigures.values())
		{
			figure.setContainerId(translateOldIdToNew(persistentFigures, figure.getContainerId(), removeIdsNotInSet));
			figure.setAnchor1Id(translateOldIdToNew(persistentFigures, figure.getAnchor1Id(), removeIdsNotInSet));
			figure.setAnchor2Id(translateOldIdToNew(persistentFigures, figure.getAnchor2Id(), removeIdsNotInSet));
			
			// any links
			List<String> linkIds = new ArrayList<String>();
			for (String oldLinkId : figure.getLinkIds())
			{
				String newLinkId = translateOldIdToNew(persistentFigures, oldLinkId, removeIdsNotInSet);
				linkIds.add(newLinkId);
			}
			figure.getLinkIds().clear();
			figure.getLinkIds().addAll(linkIds);
			
			// any contents
			List<String> contentIds = new ArrayList<String>();
			for (String oldContentId : figure.getContentIds())
			{
				String newContentId = translateOldIdToNew(persistentFigures, oldContentId, removeIdsNotInSet);
				contentIds.add(newContentId);
			}
			figure.getContentIds().clear();
			figure.getContentIds().addAll(contentIds);
		}
				
		// return the figures -- have to make a new collection so that it is serializable
		return new ArrayList<PersistentFigure>(persistentFigures.values());
	}
	
	private static String translateOldIdToNew(
	    Map<String, PersistentFigure> persistentFigures,
	    String oldId,
	    boolean removeIdIfNotInSet)
	{
		if (!persistentFigures.containsKey(oldId))
			return removeIdIfNotInSet ? null : oldId;
		return persistentFigures.get(oldId).getId();
	}
}
