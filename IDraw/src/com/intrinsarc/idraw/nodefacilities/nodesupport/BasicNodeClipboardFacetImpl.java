package com.intrinsarc.idraw.nodefacilities.nodesupport;

import java.util.*;

import com.intrinsarc.idraw.foundation.*;

/**
 * @author andrew
 */
public class BasicNodeClipboardFacetImpl implements ClipboardFacet
{
  private BasicNodeState state;
  
  public BasicNodeClipboardFacetImpl(BasicNodeState state)
  {
    this.state = state;
  }

  /**
   * @see com.intrinsarc.idraw.foundation.ClipboardFacet#addIncludedInCopy(Set, FigureChosenFacet)
   */
  public boolean addIncludedInCopy(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent)
  {
    return addIncluded(figures, focussedFigures, includedViaParent, true);
  }
  
  /**
   * @see com.intrinsarc.idraw.foundation.ClipboardFacet#addIncludedInDelete(Set, ChosenFiguresFacet, boolean)
   */
  public boolean addIncludedInDelete(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent)
  {
    return addIncluded(figures, focussedFigures, includedViaParent, false);
  }

  private boolean addIncluded(Set<FigureFacet> figures, ChosenFiguresFacet focussedFigures, boolean includedViaParent, boolean copyNotDelete)
  {
    // don't add this if it is there already
    FigureFacet figureFacet = state.figureFacet;
    if (figures.contains(figureFacet))
      return true;
      
    // add this, if it is in the set of focussed figures, or is contained by a node in the set
    if (includedViaParent || isContainedByAChosenFigure(figures, focussedFigures, copyNotDelete))
    {
      figures.add(figureFacet);
      ContainerFacet container = figureFacet.getContainerFacet();
      if (container != null)
      {
        Iterator childIter = container.getContents();
        while (childIter.hasNext())
        {
          FigureFacet child = (FigureFacet) childIter.next();
          ClipboardFacet clipFacet = child.getClipboardFacet();
          if (clipFacet != null)
          {
            if (copyNotDelete)
              clipFacet.addIncludedInCopy(figures, focussedFigures, true);
            else
              clipFacet.addIncludedInDelete(figures, focussedFigures, true);
          }
        }
      } 

      // handle any linked items
      for (Iterator links = state.linked.iterator(); links.hasNext();)
      {
        FigureFacet link = ((LinkingFacet) links.next()).getFigureFacet();
        
        ClipboardFacet linkClipFacet = link.getClipboardFacet();
        if (linkClipFacet != null)
        {
          if (copyNotDelete)
            linkClipFacet.addIncludedInCopy(figures, focussedFigures, false);
          else
            linkClipFacet.addIncludedInDelete(figures, focussedFigures, false);           
        }
      }
      return true;
    }
    else
      return false;
  }
  
  private boolean isContainedByAChosenFigure(Set figures, ChosenFiguresFacet focussedFigures, boolean copyNotDelete)
  {
    FigureFacet figureFacet = state.figureFacet;
    ContainedFacet contained = figureFacet.getContainedFacet();
    if (contained == null)
      return focussedFigures.isChosen(figureFacet);
      
    // if this is chosen, and can be stand alone, then we can choose it straight away
    if (focussedFigures.isChosen(figureFacet) && (!copyNotDelete || contained.acceptsContainer(null)))
      return true;
      
    // iterate up through the parents, looking for a chosen container
    ContainerFacet container = contained.getContainer();
    while (container != null)
    {
      if (focussedFigures.isChosen(container.getFigureFacet()))
        return true;
      ContainedFacet containerContained = container.getFigureFacet().getContainedFacet();
      if (containerContained != null)
        container = containerContained.getContainer();
      else
        container = null;
    }
    
    return false;
  }
}
