package com.hopstepjump.idraw.nodefacilities.previewsupport;

import java.awt.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 *
 * (c) Andrew McVeigh 06-Aug-02
 *
 */
public final class BasicNodePreviewAppearanceGem implements Gem
{
	private UBounds bounds;
	private UBounds originalBounds;
	private PreviewFacet previewFacet;
	private BasicNodePreviewAppearanceFacetImpl appearanceFacet = new BasicNodePreviewAppearanceFacetImpl();
	
	private class BasicNodePreviewAppearanceFacetImpl implements BasicNodePreviewAppearanceFacet
	{
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

		public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, OrientedPoint oriented, boolean linkFromContained, UPoint boxPoint, UPoint insidePoint, boolean linkStart)
		{
	    UPoint offsetPoint = oriented.getPoint();
	    return new BoundaryCalculator(bounds.addToExtent(new UDimension(1,1))).calculateBoundaryPoint(offsetPoint, boxPoint, insidePoint);
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return bounds;
		}

		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getFullBounds()
		 */
		public UBounds getFullBounds()
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
	
	public BasicNodePreviewAppearanceGem(UBounds bounds)
	{
		this.bounds = bounds;
		this.originalBounds = bounds;
	}
	
	public void connectPreviewFacet(PreviewFacet previewFacet)
	{
		this.previewFacet = previewFacet;
	}
  
	public BasicNodePreviewAppearanceFacet getBasicNodePreviewAppearanceFacet()
	{
		return appearanceFacet;
	}
}
