package com.intrinsarc.evolve.umldiagrams.narynode;


import java.awt.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public final class NaryPreviewGem implements Gem
{
	private UBounds bounds;
	private UBounds originalBounds;
	private BasicNodePreviewAppearanceFacetImpl appearanceFacet = new BasicNodePreviewAppearanceFacetImpl();

	private class BasicNodePreviewAppearanceFacetImpl implements BasicNodePreviewAppearanceFacet
	{
		/**
		 * @see com.hopstepjump.jumble.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#calculateBoundaryPoint(OrientedPoint, boolean, UPoint, UPoint)
		 */
		public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, OrientedPoint oriented, boolean linkFromContained, UPoint boxPoint, UPoint insidePoint, boolean linkStart)
		{
	    return new BoundaryCalculator(getDiamond(bounds).getShape()).
	    	calculateBoundaryPoint(oriented.getPoint(), null, insidePoint);
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#formAnchorHighlight(boolean)
		 */
		public ZNode formAnchorHighlight(boolean showOk)
		{
	    ZGroup group = new ZGroup();

			ZPolygon diamond = getDiamond(bounds.addToPoint(new UDimension(-5, -5)).addToExtent(new UDimension(10, 10)));
			if (showOk)
	    {
        diamond.setPenPaint(new Color(200, 250, 200));
        diamond.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
	    }
	    else
	    {
        diamond.setPenPaint(new Color(250, 200, 200));
        diamond.setStroke(new BasicStroke(2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
	    }
	    group.addChild(new ZVisualLeaf(diamond));
	    return group;
	  }

	  private ZPolygon getDiamond(UBounds boxBounds)
	  {
	    double width = boxBounds.getWidth();
	    double height = boxBounds.getHeight();
	    UPoint start = boxBounds.getTopLeftPoint().add(new UDimension(width/2, 0));
	    ZPolygon diamond = new ZPolygon(start);
	
	    // add the points for the note
	    diamond.add(start.add(new UDimension(-width/2, height/2)));
	    diamond.add(start.add(new UDimension(0, height)));
	    diamond.add(start.add(new UDimension(width/2, height/2)));
	    diamond.setFillPaint(null);
	    return diamond;
	  }

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#formView()
		 */
		public ZNode formView(boolean debugOnly)
		{
	    ZGroup group = new ZGroup();

			ZPolygon diamond = getDiamond(bounds);
			diamond.setFillPaint(null);
	    diamond.setPenPaint(ScreenProperties.getPreviewColor());
	    diamond.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
	    group.addChild(new ZVisualLeaf(diamond));
	    return group;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getFullBounds()
		 */
		public UBounds getFullBounds()
		{
			return bounds;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return bounds;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#restoreOriginalBounds()
		 */
		public void restoreOriginalBounds()
		{
			bounds = originalBounds;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#setBounds(UBounds)
		 */
		public void setBounds(UBounds newBounds, boolean resizedNotMoved)
		{
			bounds = newBounds;
		}

		/**
		 * @see com.intrinsarc.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getOffsetFromOriginal()
		 */
		public UDimension getOffsetFromOriginal()
		{
			return bounds.getPoint().subtract(originalBounds.getPoint());
		}
	}	
	
  public NaryPreviewGem(UBounds bounds)
  {
  	this.bounds = bounds;
  	this.originalBounds = bounds;
  }
  
  public BasicNodePreviewAppearanceFacet getBasicNodePreviewAppearanceFacet()
  {
  	return appearanceFacet;
  }
}