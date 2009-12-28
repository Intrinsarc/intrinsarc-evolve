/*
 * Created on Nov 30, 2003 by Andrew McVeigh
 */
package com.hopstepjump.jumble.umldiagrams.notenode;

import java.awt.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;



public final class NotePreviewGem implements Gem
{
	private UBounds containerArea;
	private UBounds bounds;
	private UBounds originalBounds;

	private NoteNodeFacet noteNodeFacet;
  
	private PreviewFacet previewFacet;
	private PreviewCacheFacet previews;
	private BasicNodeAppearanceFacetImpl appearanceFacet = new BasicNodeAppearanceFacetImpl();
	private ContainerPreviewFacetImpl containerFacet = new ContainerPreviewFacetImpl();
  
	private class ContainerPreviewFacetImpl implements ContainerPreviewFacet
	{
		public ZNode formContainerHighlight(boolean showOk)
		{
			ZRectangle rect = new ZRectangle(containerArea);
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
		 * @see com.hopstepjump.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
			UBounds previewBounds = noteNodeFacet.getBoundsAfterExistingContainablesAlter(previews);
			previewFacet.setFullBounds(previewBounds, true);
		}

		public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
		{
			UBounds previewBounds = noteNodeFacet.getBoundsAfterExistingContainablesAlter(previews);
			previewFacet.setFullBounds(previewBounds, true);
		}
	
		public void restoreSizeForContainables()
		{
			previewFacet.setFullBounds(originalBounds, true);
		}
	
		public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint)
		{
			// never called
		}
	
		public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
		{
			// never called
		}
	    
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}

		public void boundsHaveBeenSet(UBounds oldBounds, UBounds newBounds, boolean resizedNotMoved)
		{
      noteNodeFacet.tellContainedAboutResize(previews, bounds);
		}
	}
  
	private class BasicNodeAppearanceFacetImpl implements BasicNodePreviewAppearanceFacet
	{
		public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, OrientedPoint oriented,  boolean linkFromContained, UPoint boxPoint, UPoint insidePoint)
		{
			UPoint offsetPoint = oriented.getPoint();
			return new BoundaryCalculator(bounds.addToExtent(new UDimension(1,1))).calculateBoundaryPoint(offsetPoint, boxPoint, insidePoint);
		}

		public ZNode formView(boolean debugOnly)
		{
			ZGroup group = new ZGroup();
			ZRectangle rect = new ZRectangle(bounds);
			rect.setFillPaint(null);
			if (debugOnly)
				rect.setPenPaint(Color.red);
			else
				rect.setPenPaint(ScreenProperties.getPreviewColor());
			rect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			rect.setFillPaint(null);
			group.addChild(new ZVisualLeaf(rect));
			return group;
		}
		
		public ZNode formAnchorHighlight(boolean showOk)
		{
			ZGroup group = new ZGroup();
	
			UBounds anchorBounds = bounds.addToPoint(new UDimension(-5, -5)).addToExtent(new UDimension(10, 10));
	
			ZRectangle rect = new ZRectangle(anchorBounds);
			rect.setFillPaint(null);
	
			if (showOk)
			{
				rect.setPenPaint(new Color(200, 250, 200));
				rect.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			}
			else
			{
				rect.setPenPaint(new Color(250, 200, 200));
				rect.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			}
	
			group.addChild(new ZVisualLeaf(rect));
			return group;
		}
	  
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getFullBounds()
		 */
		public UBounds getFullBounds()
		{
			return bounds;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return bounds;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#restoreOriginalBounds()
		 */
		public void restoreOriginalBounds()
		{
			bounds = originalBounds;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#setBounds(UBounds)
		 */
		public void setBounds(UBounds newBounds, boolean resizedNotMoved)
		{
			bounds = newBounds;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getOffsetFromOriginal()
		 */
		public UDimension getOffsetFromOriginal()
		{
			return bounds.getPoint().subtract(originalBounds.getPoint());
		}
	}

	public NotePreviewGem(UBounds bounds, UBounds containerArea)
	{
		this.bounds = bounds;
		originalBounds = bounds;
		this.containerArea = containerArea;
	}
  
	public void connectPreviewFacet(PreviewFacet previewFacet)
	{
		this.previewFacet = previewFacet;
	}
  
	public void connectPreviewCacheFacet(PreviewCacheFacet previews)
	{
		this.previews = previews;
	}

	public BasicNodePreviewAppearanceFacet getBasicNodePreviewAppearanceFacet()
	{
		return appearanceFacet;
	}
	
	public ContainerPreviewFacet getContainerPreviewFacet()
	{
		return containerFacet;
	}

	public void setNoteNodeFacet(NoteNodeFacet facet)
	{
		noteNodeFacet = facet;
	}
}