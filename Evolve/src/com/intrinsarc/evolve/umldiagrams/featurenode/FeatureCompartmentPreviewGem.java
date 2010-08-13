/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.featurenode;

import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;

/**
 * @version 	1.0
 * @author
 */
public final class FeatureCompartmentPreviewGem implements Gem
{
  private UBounds originalBounds;
  private ContainedPreviewFacet[] removePreviews;
  private ContainedPreviewFacet[] addPreviews;
  private ContainerPreviewFacetImpl containerFacet = new ContainerPreviewFacetImpl();
  private FeatureCompartmentPreviewFacetImpl compartmentPreviewFacet = new FeatureCompartmentPreviewFacetImpl();
  private FeatureCompartmentFacet compartmentFacet;
  private PreviewFacet previewFacet;
  private PreviewCacheFacet previews;
  
  private class FeatureCompartmentPreviewFacetImpl implements FeatureCompartmentPreviewFacet
  {
	  public void adjustPreviewPoint(PreviewCacheFacet previews, UPoint point)
	  {
	  	// assumes that adds are 0 at this point
	  	UBounds bounds = new UBounds(
	  	    point,
	  	    compartmentFacet.formNewDimensions(
	  	        previews,
	  	        getCurrentPreviews(previews, null),
	  	        point));
	    previewFacet.setFullBounds(bounds, false);
	  }
  }
  
  private class ContainerPreviewFacetImpl implements ContainerPreviewFacet
  {
	  public ZNode formContainerHighlight(boolean showOk)
	  {
	    // ask the parent container for this
	    FigureFacet parentContainer =
	      previewFacet.isPreviewFor().getContainedFacet().getContainer().getFigureFacet();
	    return
	    	parentContainer.getSinglePreview(
	    	    previewFacet.isPreviewFor().getDiagram()).getContainerPreviewFacet().formContainerHighlight(showOk);
	  }
	
		/**
		 * @see com.intrinsarc.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
	  	UPoint topLeft = previewFacet.getTopLeftPoint();
	  	UBounds bounds = new UBounds(topLeft, compartmentFacet.formNewDimensions(previews, getCurrentPreviews(previews, new UPoint(0,0)), topLeft));
	    previewFacet.setFullBounds(bounds, true);
		}

	  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
	  {
	  	addPreviews = exists;
	  	removePreviews = null;
	  	UPoint topLeft = previewFacet.getTopLeftPoint();
	  	UBounds bounds = new UBounds(topLeft, compartmentFacet.formNewDimensions(previews, getCurrentPreviews(previews, movePoint), topLeft));
	    previewFacet.setFullBounds(bounds, true);
	  }
	
	  public void restoreSizeForContainables()
	  {
	  	addPreviews = null;
	  	removePreviews = null;
	    previewFacet.setFullBounds(originalBounds, true);
	  }
	
	  public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint)
	  {
	  	removePreviews = takes;
	  	addPreviews = null;
	  	UPoint topLeft = previewFacet.getTopLeftPoint();
	  	UBounds bounds = new UBounds(topLeft, compartmentFacet.formNewDimensions(previews, getCurrentPreviews(previews, movePoint), topLeft));
	    previewFacet.setFullBounds(bounds, true);
	  }
	
	  public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
	  {
	  	addPreviews = adds;
	  	removePreviews = null;
	  	UPoint topLeft = previewFacet.getTopLeftPoint();
	  	UBounds bounds = new UBounds(topLeft, compartmentFacet.formNewDimensions(previews, getCurrentPreviews(previews, movePoint), topLeft));
	    previewFacet.setFullBounds(bounds, true);
	  }
	  
	  public void boundsHaveBeenSet(UBounds oldBounds, UBounds newBounds, boolean resizedNotMoved)
	  {
	  }

		/**
		 * @see com.intrinsarc.jumble.foundation.ContainerPreviewFacet#getPreviewFacet()
		 */
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
  }
  
  public FeatureCompartmentPreviewGem()
  {
    removePreviews = null;
    addPreviews = null;
  }
  
  public void connectPreviewFacet(PreviewFacet previewFacet)
  {
  	this.previewFacet = previewFacet;
  	originalBounds = previewFacet.getFullBounds();
  	previewFacet.registerDynamicFacet(compartmentPreviewFacet, FeatureCompartmentPreviewFacet.class);
  }
  
  public void connectPreviewCacheFacet(PreviewCacheFacet previews)
  {
  	this.previews = previews;
  }
  
  public void connectFeatureCompartmentFacet(FeatureCompartmentFacet compartmentFacet)
  {
  	this.compartmentFacet = compartmentFacet;
  }
  
  public ContainerPreviewFacet getContainerPreviewFacet()
  {
  	return containerFacet;
  }

	private List<PreviewFacet> getCurrentPreviews(PreviewCacheFacet previews, UPoint movePoint)
	{
		return compartmentFacet.getSortedOperations(previews, addPreviews, removePreviews, movePoint);
	}
}
