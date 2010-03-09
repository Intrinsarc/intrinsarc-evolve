package com.hopstepjump.idraw.diagramsupport.moveandresize;

import java.awt.*;
import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

public final class MovingFiguresGem implements Gem
{
	private Set<FigureFacet> moveables;
  private DiagramFacet diagram;
  private UPoint start;
  private java.util.Map<FigureFacet, PreviewFacet> selectedFigures;   // all figures
  private java.util.Set<PreviewFacet> focussedFigures;     						// all figures that we need to send a move cmd to (non-peripheral nodes excluded) (previewable -> preview)
  private java.util.Set<FigureFacet> topLevelFigures;     						// all selected figures which are not contained by another selection
  private java.util.Map<FigureFacet, PreviewFacet> allFigures;       	// all figures in cache, including peripheral nodes (previewable -> preview)
  private PreviewFacet draggedMoving;       													// the figure we are dragging with
  private ContainerFacet fromContainer; 															// set to the container that the figures are moving from, or null if there is more than 1
  private boolean setContainer = false;
  private boolean allMovingFromSameContainer = true;
  private MovingFiguresFacetImpl movingFiguresFacet = new MovingFiguresFacetImpl();
  
  private class MovingFiguresFacetImpl implements MovingFiguresFacet
  {
	  /**
	   * return true if all moveables (taking containment into account) have the same top-level container
	   */
	  public boolean indicateMovingFigures(Collection<FigureFacet> movingFigures)
	  {
	    // iterate over the selection, and add the selection to the group of moving figures
	    moveables = new HashSet<FigureFacet>(movingFigures);

	    for (FigureFacet selected : moveables)
	    {
	      if (!nodeIsContainedByPrimaryFocusOfPreview(selected))
	      {
	        getCachedPreviewOrMakeOne(selected);
	
	        // get the container
					ContainedFacet contained = selected.getContainedFacet();
	        if (contained != null)
	        {
	          ContainerFacet container = contained.getContainer();
	
	          // now, set the containment vars
	          if (!setContainer)
	            fromContainer = container;
	          setContainer = true;
	          if (container != fromContainer)
	            allMovingFromSameContainer = false;
	        }
	        topLevelFigures.add(selected);
	      }
	    }
	    return allMovingFromSameContainer;
	  }
	
	  public ContainerFacet getContainerAllAreMovingFrom()
	  {
	    return fromContainer;
	  }
	
	  public Iterator getSelectedPreviewFigures()
	  {
	    return selectedFigures.values().iterator();
	  }
	
	  public Iterator getTopLevelFigures()
	  {
	    // return all the "top-level" moveable figures
	    return topLevelFigures.iterator();
	  }
	
	  boolean nodeIsContainedByPrimaryFocusOfPreview(FigureFacet previewable)
	  {
	    // traverse from this figure, up to the top container
	    // if any are selected, then this node is in the focus
	    ContainedFacet contained = previewable.getContainedFacet();
	    if (contained != null)
	    {
	      ContainerFacet container = contained.getContainer();
	      while (container != null)
	      {
	        PreviewFacet containerPreview = getCachedPreviewOrMakeOne(container.getFigureFacet());
	        // the isFullyMoving check covers figures contained in arcs,
	        // as if the arc is fully moving, the contained figures should be also
	        if (moveables.contains(container.getFigureFacet()) || containerPreview.isFullyMoving())
	          return true;
	
	        // if this is also contained then keep going
	        ContainedFacet containerAsContainable = container.getContainedFacet();
	        if (containerAsContainable != null)
	          container = containerAsContainable.getContainer();
	        else
	          container = null;
	      }
	    }
	
	    return false;
	  }
	
	  public void move(UPoint current)
	  {
	    Iterator iter = focussedFigures.iterator();
	    while (iter.hasNext())
	    {
	      PreviewFacet moving = (PreviewFacet) iter.next();
	      moving.move(current);
	    }
	  }
	
	  public void start(FigureFacet dragging)
	  {
	    // find out which moving figure this corresponds to
	    draggedMoving = getCachedPreviewOrMakeOne(dragging);
	  }
	
	  boolean isVisible(PreviewFacet preview)
	  {
      boolean showAll = GlobalPreferences.preferences.getRawPreference(
          new Preference("Advanced", "Show all previews", new PersistentProperty(false))).asBoolean();
	  	if (showAll)
	  		return true;
	    return preview.canChangeDueToMove() && preview.hasOutgoingsToPeripheral() || preview == draggedMoving;
	  }
	  
	  public ZNode formView()
	  {
	    ZGroup group = new ZGroup();
	
	    // get the centre point for the cross hairs
	    UPoint centre = Grid.roundToGrid(
	        new UBounds(draggedMoving.formView().getBounds()).adjustForJazzBounds().getTopLeftPoint());
	
	    // add the cross hairs
	    group.addChild(new CrossHair(centre).formView());
	
	    // add the outlines of the moving figures
	    Iterator iter = allFigures.values().iterator();
	    UBounds focusBounds = null;
	    while (iter.hasNext())
	    {
	      PreviewFacet moving = (PreviewFacet) iter.next();
	      FigureFacet previewFor = moving.isPreviewFor();
	
	      if (previewFor.isShowing() && movingFiguresFacet.isVisible(moving))
	      {
	      	ZNode node = moving.formView();
	      	if (node != null)
		        group.addChild(node);
	      }
	
	      if (previewFor.isShowing() && moving.isFullyMoving())
	      {
	      	ZNode node = moving.formView();
	      	if (node != null)
	      	{
		        UBounds movingBounds = new UBounds(node.getBounds()).adjustForJazzBounds();
		        if (focusBounds == null)
		          focusBounds = movingBounds;
		        else
		        	focusBounds = focusBounds.union(movingBounds);
	      	}
	      }
	    }
	
	    // add the focus outline
	    if (focusBounds != null)
	    {
	      ZRectangle focusRect = new ZRectangle(focusBounds);
	      focusRect.setFillPaint(ScreenProperties.getPreviewFillColor());
	      focusRect.setPenPaint(ScreenProperties.getPreviewColor());
	      focusRect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
	      group.addChild(new ZVisualLeaf(focusRect));
	    }
	
	    return group;
	  }
	
	  /**
	   * addMovingFigureToCache is only for a figure to call, to add a moving figure to the list
	   * before a dependent needs to get hold of it.
	   */
	  public void addPreviewToCache(FigureFacet previewable, PreviewFacet previewFigure)
	  {
			Validator.validatePreview(previewFigure);
	    allFigures.put(previewable, previewFigure);
	    if (nodeIsInFocusOfPreview(previewable))
	      focussedFigures.add(previewFigure);
	      
	    if (moveables.contains(previewable))
	      selectedFigures.put(previewable, previewFigure);
	  }
	
	  public PreviewFacet getCachedPreviewOrMakeOne(FigureFacet previewable)
	  {
	    // called by the anyone to get a moving figure off a IFigure
	    if (allFigures.containsKey(previewable))
	      return allFigures.get(previewable);
	
			if (!allFigures.containsKey(previewable))
		    previewable.addPreviewToCache(diagram, this, start, true);
	
	    if (!allFigures.containsKey(previewable))
	      throw new IllegalStateException("preview figure not found in cache after calling addPreviewFigureToCache() on it");
	    return allFigures.get(previewable);
	  }
	
	  public boolean nodeIsInFocusOfPreview(FigureFacet previewable)
	  {
	    // this is in focus, if it is selected
	    if (previewable.getLinkingFacet() != null)
	      return true;
	    return
	      moveables.contains(previewable) ||
	      movingFiguresFacet.nodeIsContainedByPrimaryFocusOfPreview(previewable);
	  }
	
	  public PreviewFacet getCachedPreview(FigureFacet previewable)
	  {
	    return allFigures.get(previewable);
	  }
	
	  public void disconnect()
	  {
	    // not valid, as this is always at the end of the chain
	  }
	
	  public Command end(String description, String undoDescription)
	  {
	    for (PreviewFacet moving : allFigures.values())
	    {
	    	diagram.aboutToAdjust(moving.isPreviewFor());
	      Command cmd = moving.end();
	      if (cmd != null)
	        cmd.execute(true);
	    }
	    System.out.println("$$ executed commands");
	    return new CompositeCommand("", "");
	  }

	  /**
	   * can the top level elements move container?
	   */
		public boolean contentsCanMoveContainers()
		{
			for (FigureFacet figure : topLevelFigures)
				if (figure.getContainedFacet() != null)
				{
				  if (!figure.getContainedFacet().canMoveContainers())
				    return false;
				  if (figure.getContainedFacet() != null)
				  {
				    ContainerFacet container = figure.getContainedFacet().getContainer();
				    if (container != null && container.getFigureFacet().isSubjectReadOnlyInDiagramContext(false))
				      return false;
				  }
				}

			return true;
		}
  }

  public MovingFiguresGem(DiagramFacet diagram, UPoint start)
  {
    allFigures = new HashMap<FigureFacet, PreviewFacet>();
    selectedFigures = new HashMap<FigureFacet, PreviewFacet>();
    focussedFigures = new HashSet<PreviewFacet>();
    topLevelFigures = new HashSet<FigureFacet>();
    this.diagram = diagram;
    this.start = start;
  }
  
  public MovingFiguresFacet getMovingFiguresFacet()
  {
  	return movingFiguresFacet;
  }
}