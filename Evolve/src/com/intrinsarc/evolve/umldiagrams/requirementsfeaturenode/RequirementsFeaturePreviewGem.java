/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.awt.geom.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.previewsupport.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class RequirementsFeaturePreviewGem implements Gem
{
  private UBounds bounds;
  private UBounds containmentBounds;
  private UBounds originalBounds;

  private RequirementsFeatureNodeFacet classifierFacet;
  private BasicNodePreviewAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  
  public RequirementsFeaturePreviewGem(UBounds bounds)
  {
    this.bounds = bounds;
    originalBounds = bounds;
  }
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodePreviewAppearanceFacet
  {
	  public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, OrientedPoint oriented,  boolean linkFromContained, UPoint boxPoint, UPoint insidePoint, boolean linkStart)
	  {
	  	UBounds bounds = getFullBounds();
	  	if (linkStart)
	  	{
	  		UPoint pt = Grid.roundToGrid(new UPoint(bounds.getCenterX(), 0));
	  		return new UPoint(pt.getX(), bounds.getBottomLeftPoint().getY());
	  	}

	  	// otherwise, just go to boundary
	  	if (classifierFacet.isDisplayOnlyIcon())
	  	{
	  		Shape shape = classifierFacet.formShapeForBoundaryCalculation(bounds);
	  		return new BoundaryCalculator(shape).calculateBoundaryPoint(oriented.getPoint(), null, insidePoint);
	  	}

	    boolean useBox = true;
	    UBounds newBounds = bounds;
	    UPoint offsetPoint = oriented.getPoint();
	    return new BoundaryCalculator(newBounds.addToExtent(new UDimension(1,1))).calculateBoundaryPoint(offsetPoint, useBox ? boxPoint : null, insidePoint);

	  }

		public ZNode formView(boolean debugOnly)
		{
	    ZGroup group = new ZGroup();
	    Shape shape = classifierFacet.formShapeForPreview(bounds);
	    GeneralPath path = new GeneralPath(shape);
	    ZPath zPath = new ZPath(path);
	    zPath.setFillPaint(null);
			zPath.setPenPaint(ScreenProperties.getPreviewColor());
	    zPath.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
	    group.addChild(new ZVisualLeaf(zPath));
	    return group;
		}
		
	  public ZNode formAnchorHighlight(boolean showOk)
	  {
	    ZGroup group = new ZGroup();
	
	    UBounds adjustedBounds = bounds.addToPoint(new UDimension(-5, -5)).addToExtent(new UDimension(10, 10));
	
	    ZRectangle rect = new ZRectangle(adjustedBounds);
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
      return containmentBounds;
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
			containmentBounds = classifierFacet.getContainmentBounds(newBounds);
		}
		
		/**
		 * @see com.intrinsarc.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getOffsetFromOriginal()
		 */
		public UDimension getOffsetFromOriginal()
		{
			return bounds.getPoint().subtract(originalBounds.getPoint());
		}

	}

  public void connectClassifierNodeFacet(RequirementsFeatureNodeFacet facet)
  {
  	this.classifierFacet = facet;
    containmentBounds = classifierFacet.getContainmentBounds(bounds);
  }

	public BasicNodePreviewAppearanceFacet getBasicNodePreviewAppearanceFacet()
	{
		return appearanceFacet;
	}
	
	public ContainerPreviewFacet getContainerPreviewFacet()
	{
		return null;
	}
}

