package com.intrinsarc.idraw.figures.simplecontainernode;

import java.awt.*;
import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class SimpleContainerPreviewGem implements Gem
{
  private UBounds originalBounds;
  private ContainedPreviewFacet[] removePreviews;
  private ContainedPreviewFacet[] addPreviews;
  private ContainerPreviewFacetImpl containerFacet = new ContainerPreviewFacetImpl();
  private SimpleContainerPreviewFacetImpl simplePreviewFacet = new SimpleContainerPreviewFacetImpl();
  private PreviewFacet previewFacet;
  private SimpleContainerFacet simpleContainerFacet;
  private PreviewCacheFacet previews;
  
	private class SimpleContainerPreviewFacetImpl implements SimpleContainerPreviewFacet
	{
		public UBounds getMinimumBoundsFromPreviews(PreviewCacheFacet previews)
		{
			Set all = getAllUnderlyings();
			Iterator iter = all.iterator();
			UBounds bounds = null;
			while (iter.hasNext())
			{
				UBounds currBounds = (UBounds) iter.next();
				if (bounds == null)
					bounds = currBounds;
				else
					bounds = bounds.union(currBounds);
			}
			if (bounds == null)
				return null;
			SimpleContainerSizeInfo info = simpleContainerFacet.makeCurrentSizeInfo(bounds);
			return info.calculateSizes().getEntireBounds();
		}
		
		public boolean isEmpty()
		{
			return getAllUnderlyings().size() == 0;
		}
	}
	
  private class ContainerPreviewFacetImpl implements ContainerPreviewFacet
  {
	  public ZNode formContainerHighlight(boolean showOk)
	  {
	  	UDimension offset = new UDimension(5,5);
	    ZRectangle rect = new ZRectangle(originalBounds.addToPoint(offset).addToExtent(offset.multiply(2).negative()));
	    rect.setFillPaint(null);
	
			if (showOk)
			{
        rect.setPenPaint(new Color(200, 200, 250));
        rect.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			}
			else
			{
        rect.setPenPaint(Color.red);
        rect.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			}

	    return new ZVisualLeaf(rect);
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
	  	SimpleContainerSizeInfo info = simpleContainerFacet.makeCurrentSizeInfo(simplePreviewFacet.getMinimumBoundsFromPreviews(previews));
	    previewFacet.setFullBounds(info.calculateSizes().getEntireBounds(), true);
	  }
	
	  public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
	  {
	   	addPreviews = adds;
	  	removePreviews = null;
	  	SimpleContainerSizeInfo info = simpleContainerFacet.makeCurrentSizeInfo(simplePreviewFacet.getMinimumBoundsFromPreviews(previews));
	    previewFacet.setFullBounds(info.calculateSizes().getEntireBounds(), true);
	  }
	  
		/**
		 * @see com.intrinsarc.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
	  	SimpleContainerSizeInfo info = simpleContainerFacet.makeCurrentSizeInfo(simplePreviewFacet.getMinimumBoundsFromPreviews(previews));
	    previewFacet.setFullBounds(info.calculateSizes().getEntireBounds(), true);
		}

	  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
	  {
	  	addPreviews = exists;
	  	removePreviews = null;
	  	SimpleContainerSizeInfo info = simpleContainerFacet.makeCurrentSizeInfo(simplePreviewFacet.getMinimumBoundsFromPreviews(previews));
	    previewFacet.setFullBounds(info.calculateSizes().getEntireBounds(), true);
	  }
	
	  public PreviewFacet getPreviewFacet()
	  {
	  	return previewFacet;
	  }
	  
		/**
		 * @see com.hopstepjump.jumble.foundation.ContainerPreviewFacet#boundsHaveBeenSet(PreviewCacheFacet)
		 */
		public void boundsHaveBeenSet(UBounds oldBounds, UBounds newBounds, boolean resizedNotMoved)
		{
		}

  }

  public SimpleContainerPreviewGem()
  {
  }
  
  public void connectSimpleContainerFacet(SimpleContainerFacet simpleContainerFacet)
  {
  	this.simpleContainerFacet = simpleContainerFacet;
  }
  
  public void connectPreviewFacet(PreviewFacet previewFacet)
  {
  	this.previewFacet = previewFacet;
  	this.originalBounds = previewFacet.getFullBounds();
  	previewFacet.registerDynamicFacet(simplePreviewFacet, SimpleContainerPreviewFacet.class);
  }
  
  public void connectPreviewCacheFacet(PreviewCacheFacet previews)
  {
  	this.previews = previews;
  }
  
  public ContainerPreviewFacet getContainerPreviewFacet()
  {
  	return containerFacet;
  }

	private Set getAllUnderlyings()
	{
		// returns a set of underlying bounds...
		Set<UBounds> all = new HashSet<UBounds>();
		Iterator iter = simpleContainerFacet.getFigureFacet().getContainerFacet().getContents();
		while (iter.hasNext())
		{
			// only add this if we are not removing it
			FigureFacet contained = (FigureFacet) iter.next();
			boolean okToUse = true;
			if (removePreviews != null)
				for (int lp = 0; lp < removePreviews.length; lp++)
					if (contained == removePreviews[lp].getPreviewFacet().isPreviewFor())
					{
						okToUse = false;
						break;
					}
			// only add if this is not one of the previews that are adjusting
			if (addPreviews != null)
				for (int lp = 0; lp < addPreviews.length; lp++)
					if (contained == addPreviews[lp].getPreviewFacet().isPreviewFor())
					{
						okToUse = false;
						break;
					}
			
			if (okToUse)
				all.add(contained.getFullBoundsForContainment());
		}
		if (addPreviews != null)
			for (int lp = 0; lp < addPreviews.length; lp++)
				all.add(addPreviews[lp].getPreviewFacet().getFullBoundsForContainment());
		
		return all;
	}
}
