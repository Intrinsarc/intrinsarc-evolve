/*
 * Created on Jan 2, 2004 by Andrew McVeigh
 */
package com.intrinsarc.evolve.freeform.grouper;

import java.awt.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author Andrew
 */
public class GrouperNodePreviewGem
{
	private UBounds contentBounds;
	private UBounds originalBounds;

	private GrouperNodeFacet grouperFacet;

	private PreviewFacet previewFacet;
	private PreviewCacheFacet previews;
	private ContainerPreviewFacetImpl containerFacet = new ContainerPreviewFacetImpl();
  
	private class ContainerPreviewFacetImpl implements ContainerPreviewFacet
	{
		public ZNode formContainerHighlight(boolean showOk)
		{
			ZRectangle rect = new ZRectangle(contentBounds);
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
	
		/**
		 * @see com.intrinsarc.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
			previewFacet.setFullBounds(grouperFacet.getBoundsAfterExistingContainablesAlter(previews), true);
		}

		public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
		{
			previewFacet.setFullBounds(grouperFacet.getBoundsAfterExistingContainablesAlter(previews), true);
		}
	
		public void restoreSizeForContainables()
		{
			previewFacet.setFullBounds(originalBounds, true);
		}
	
		public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint)
		{
			// not used for classifier
		}
	  
		public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
		{
			// not used for classifier
		}

		public void boundsHaveBeenSet(UBounds oldBounds, UBounds newBounds, boolean resizedNotMoved)
		{
      grouperFacet.tellContainedAboutResize(previews, newBounds);
		}
		
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
	}

	public GrouperNodePreviewGem(UBounds bounds, UBounds contentBounds)
	{
		originalBounds = bounds;
		this.contentBounds = contentBounds;
	}
  
	public void connectPreviewFacet(PreviewFacet previewFacet)
	{
		this.previewFacet = previewFacet;
	}
  
	public void connectPreviewCacheFacet(PreviewCacheFacet previews)
	{
		this.previews = previews;
	}
  
	public void connectGrouperNodeFacet(GrouperNodeFacet facet)
	{
		this.grouperFacet = facet;
	}

	public ContainerPreviewFacet getContainerPreviewFacet()
	{
		return containerFacet;
	}
}
