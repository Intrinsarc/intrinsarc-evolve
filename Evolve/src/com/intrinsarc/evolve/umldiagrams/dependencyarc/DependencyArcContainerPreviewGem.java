package com.intrinsarc.evolve.umldiagrams.dependencyarc;

import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.arcfacilities.previewsupport.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;

/**
 * @author andrew
 */
public class DependencyArcContainerPreviewGem implements Gem
{
  private ContainerPreviewFacetImpl containerPreviewFacet = new ContainerPreviewFacetImpl();
  private PreviewFacet previewFacet;
  private PreviewCacheFacet previews;
  
  private class ContainerPreviewFacetImpl implements BasicArcContainerPreviewFacet
  {
  	public ZNode formContainerHighlight(boolean showOk)
	  {
  	  return null;
	  }
	
		/**
		 * @see com.intrinsarc.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
		}

	  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
	  {
	  }
	
    public void restoreSizeForContainables()
	  {
	  }
	
	  public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint)
	  {
	  }
	  
	  public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
	  {
	  }

		public void boundsHaveBeenSet(UBounds oldBounds, UBounds newBounds, boolean resizedNotMoved)
		{
		}
		
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}

    public void newPointsHaveBeenSet(ActualArcPoints actualPoints, UPoint originalMiddle)
    {
      // need to expand this to handle more major points
      UPoint newMiddle = getMiddlePoint();
		  UDimension offset = newMiddle.subtract(originalMiddle);
		  // get any linked text entries
		  ContainerFacet container = previewFacet.isPreviewFor().getContainerFacet();
		  for (Iterator iter = container.getContents(); iter.hasNext();)
		  {
		    FigureFacet contained = (FigureFacet) iter.next();
		    PreviewFacet preview = previews.getCachedPreview(contained);
		    preview.setFullBounds(preview.getFullBounds().addToPoint(offset), true);
		  }
    }
	}
    
  public BasicArcContainerPreviewFacet getBasicArcContainerPreviewFacet()
  {
    return containerPreviewFacet;
  }
  
  public void connectPreviewFacet(PreviewFacet previewFacet)
  {
    this.previewFacet = previewFacet;
  }
  
  /**
   * @return
   */
  private UPoint getMiddlePoint()
  {
    return previewFacet.getAnchorPreviewFacet().getMiddlePoint();
  }

  public void connectPreviewCacheFacet(PreviewCacheFacet previews)
  {
    this.previews = previews;
  }
}
