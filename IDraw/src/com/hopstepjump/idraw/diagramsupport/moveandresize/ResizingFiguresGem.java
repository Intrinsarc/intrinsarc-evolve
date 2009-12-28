package com.hopstepjump.idraw.diagramsupport.moveandresize;

import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

import edu.umd.cs.jazz.*;


public final class ResizingFiguresGem implements Gem
{
  private DiagramFacet diagram;
  private Map<FigureFacet, PreviewFacet> movingFigures;
  private FigureFacet focus;
  private PreviewFacet focusPreview;
  private PreviewCacheFacet nextInChain;
  private UBounds originalFocusBounds;
  private ResizingFiguresFacet containerResizings;
  private boolean shouldDisplay = false;
  private ResizingFiguresFacetImpl resizingFiguresFacet = new ResizingFiguresFacetImpl();
  
  private class ResizingFiguresFacetImpl implements ResizingFiguresFacet
  {
	  public ZNode formView()
	  {
	    boolean forceToView = GlobalPreferences.preferences.getRawPreference(
	        new Preference("Advanced", "Show all previews", new PersistentProperty(false))).asBoolean();
	    ZGroup group = new ZGroup();
	
	    // add the outlines of the moving figures
	    if (forceToView || shouldDisplay)
	    {
	      Iterator iter = movingFigures.values().iterator();
	      while (iter.hasNext())
	      {
	        PreviewFacet moving = (PreviewFacet) iter.next();
	        FigureFacet previewFor = moving.isPreviewFor();
	        
	        if (previewFor.isShowing() && (forceToView || resizingFiguresFacet.isVisible(moving)))
	        {
	        	ZNode node = moving.formView();
	        	if (node != null)
		          group.addChild(node);
	        }
	      }
	    }
	
	    // add a container
	    if (containerResizings != null)
	      group.addChild(containerResizings.formView());
	
	    // don't go down the chain
	    return group;
	  }
	  
	  /**
	   * addPreviewFigureToCache is only for a figure to call, to add a moving figure to the list
	   * before a dependent needs to get hold of it.
	   */
	  public void addPreviewToCache(FigureFacet previewable, PreviewFacet previewFigure)
	  {
			Validator.validatePreview(previewFigure);
	    movingFigures.put(previewable, previewFigure);
	  }
	
	  public PreviewFacet getCachedPreviewOrMakeOne(FigureFacet previewable)
	  {
	    // delegate to the next preview figure cache, if there is one
	    PreviewFacet preview = getCachedPreview(previewable);
	    if (preview != null)
	    {
	      // add the preview to the cache anyway
	      addPreviewToCache(previewable, preview);
	      return preview;
	    }
	
	    previewable.addPreviewToCache(diagram, this, new UPoint(0,0), true);
	
	    if (!movingFigures.containsKey(previewable))
	      throw new IllegalStateException("preview figure not found in cache after calling addPreviewFigureToCache() on it");
	    return movingFigures.get(previewable);
	  }
	
	  public PreviewFacet getCachedPreview(FigureFacet previewable)
	  {
	    if (nextInChain != null)
	    {
	      PreviewFacet preview = nextInChain.getCachedPreview(previewable);
	      if (preview != null)
	        return preview;
	    }
	
	    return movingFigures.get(previewable);
	  }
	
	  public void disconnect()
	  {
	    // tell each preview figure that we "own" to disconnect
	    Iterator iter = movingFigures.values().iterator();
	    while (iter.hasNext())
	    {
	      PreviewFacet moving = (PreviewFacet) iter.next();
	      if (nextInChain == null || nextInChain.getCachedPreview(moving.isPreviewFor()) == null)
	        moving.disconnect();
	    }
	    if (nextInChain != null)
	      nextInChain.disconnect();
	  }
	
	  public boolean nodeIsInFocusOfPreview(FigureFacet previewable)
	  {
	    return previewable == focus || nodeIsContainedByFocus(previewable);
	  }
	  
	  private boolean nodeIsContainedByFocus(FigureFacet node)
	  {
	  	// traverse up throught the containment hierarchy to see if we can find previewable
	  	do
	  	{
	  		if (node == focus)
	  			return true;
	  			
	  		// if we are at a level where the node cannot be contained (i.e. an arc), don't go higher
	  		if (node.getContainedFacet() == null)
	  		  return false;
	  		
  			ContainerFacet container = node.getContainedFacet().getContainer();
  			if (container != null)
  				 node = container.getFigureFacet();
  			else
  				 node = null;
	  	} while (node != null);

	  	return false;
	  }
	  
	  public Command end(String description, String undoableDescription)
	  {
	    // generate the cmd, and detach all dependents
	    CompositeCommand composite = new CompositeCommand(description, undoableDescription);
	
	    Iterator iter = movingFigures.entrySet().iterator();
	    int count = 0;
	    while (iter.hasNext())
	    {
	      Map.Entry entry = (Map.Entry) iter.next();
	      PreviewFacet moving = (PreviewFacet) entry.getValue();
	      FigureFacet previewable = (FigureFacet) entry.getKey();
	      if (previewable == focus || nextInChain == null || nextInChain.getCachedPreview(previewable) == null)
	      {
	        Command cmd = moving.end();
	        if (cmd != null)
	        {
	          count++;
	          composite.addCommand(cmd);
	        }
	      }
	    }
	
	    if (nextInChain != null)
	      composite.addCommand(nextInChain.end("next in chain", "next in chain"));
	    return composite;
	  }

	  public void markForResizing(FigureFacet previewable)
	  {
	  	markForResizing(previewable, true);
	  }
	
	  public void markForResizingWithoutContainer(FigureFacet previewable)
	  {
	  	markForResizing(previewable, false);
	  }
	
	  private void markForResizing(FigureFacet previewable, boolean includeContainer)
	  {
	    // if we are in a container, make a new resizing figure for it
			ContainedFacet contained = previewable.getContainedFacet();
	    if (contained != null && includeContainer)
	    {
	      ContainerFacet container = contained.getContainer();
	      if (container != null)
	      {
	        containerResizings = new ResizingFiguresGem(nextInChain, diagram).getResizingFiguresFacet();
	        containerResizings.markForResizing(container.getFigureFacet());
	        nextInChain = containerResizings;
	      }
	    }
	
	    focus = previewable;
	    PreviewFacet preview = getCachedPreview(previewable);
	    if (preview != null)
	      addPreviewToCache(previewable, preview);
	    previewable.addPreviewToCache(diagram, this, new UPoint(0,0), preview == null);
	
	    // add any containers for resizing
	    focusPreview = getCachedPreview(previewable);
	    originalFocusBounds = focusPreview.getFullBounds();
	  }
	
	  public void forceDisplay()
	  {
	    // used for arc manipulation, which always wants the graph to be visible
	    shouldDisplay = true;
	  }
	
	  public PreviewFacet getFocusPreview()
	  {
	    return focusPreview;
	  }
	
	  public void setFocusBounds(UBounds bounds)
	  {
	    focusPreview.setFullBounds(bounds, true);
	    shouldDisplay = !focusPreview.getFullBounds().equals(originalFocusBounds);
	    tellContainerAboutPossibleChangeInSize();
	  }
	
	  /**
	   * container preview methods, used for then we are resizing a container
	   */
	  public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
	  {
	    ContainerPreviewFacet containerPreview = focusPreview.getContainerPreviewFacet();
	    containerPreview.resizeToAddContainables(adds, movePoint);
	    shouldDisplay = !focusPreview.getFullBounds().equals(originalFocusBounds);
	    tellContainerAboutPossibleChangeInSize();
	  }
	
	  public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint)
	  {
	    ContainerPreviewFacet containerPreview = focusPreview.getContainerPreviewFacet();
	    containerPreview.resizeToRemoveContainables(takes, movePoint);
	    shouldDisplay = !focusPreview.getFullBounds().equals(originalFocusBounds);
	    tellContainerAboutPossibleChangeInSize();
	  }
	
	  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
	  {
	    ContainerPreviewFacet containerPreview = focusPreview.getContainerPreviewFacet();
	    containerPreview.adjustForExistingContainables(exists, movePoint);
	    shouldDisplay = !focusPreview.getFullBounds().equals(originalFocusBounds);
	    tellContainerAboutPossibleChangeInSize();
	  }
	
	  public void restoreSizeForContainables()
	  {
	    ContainerPreviewFacet containerPreview = focusPreview.getContainerPreviewFacet();
	    containerPreview.restoreSizeForContainables();
	    shouldDisplay = !focusPreview.getFullBounds().equals(originalFocusBounds);
	    tellContainerAboutPossibleChangeInSize();
	  }

		/**
		 * @see com.hopstepjump.idraw.diagramsupport.moveandresize.ResizingFiguresFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
	    ContainerPreviewFacet containerPreview = focusPreview.getContainerPreviewFacet();
	    containerPreview.recalculateSizeForContainables();
	    shouldDisplay = !focusPreview.getFullBounds().equals(originalFocusBounds);
	    tellContainerAboutPossibleChangeInSize();
		}
	
	  private void tellContainerAboutPossibleChangeInSize()
	  {
	    // tell the container about this
	    if (containerResizings != null)
	      containerResizings.adjustForExistingContainables(new ContainedPreviewFacet[]{focusPreview.getContainedPreviewFacet()}, null);
	  }
	
	  private boolean isVisible(PreviewFacet preview)
	  {
	  	return preview.canChangeDueToMove() && preview.hasOutgoingsToPeripheral() || preview == focusPreview;
	  }

  }

  public ResizingFiguresGem(PreviewCacheFacet nextInChain, DiagramFacet diagram)
  {
    movingFigures = new HashMap<FigureFacet, PreviewFacet>();
    this.diagram = diagram;
    this.nextInChain = nextInChain;
  }
  
  public ResizingFiguresFacet getResizingFiguresFacet()
  {
  	return resizingFiguresFacet;
  }
}