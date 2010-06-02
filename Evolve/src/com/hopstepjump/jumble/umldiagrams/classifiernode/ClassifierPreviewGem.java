/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.geom.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class ClassifierPreviewGem implements Gem
{
  private UBounds bounds;
  private UBounds containmentBounds;
  private UBounds originalBounds;

  private ClassifierNodeFacet classifierFacet;

  private PreviewFacet previewFacet;
  private PreviewCacheFacet previews;
  private BasicNodePreviewAppearanceFacet appearanceFacet = new BasicNodeAppearanceFacetImpl();
  private ContainerPreviewFacetImpl containerFacet = new ContainerPreviewFacetImpl();
  
  private class ContainerPreviewFacetImpl implements ContainerPreviewFacet
  {
  	public ZNode formContainerHighlight(boolean showOk)
	  {
	    ZRectangle rect = new ZRectangle(originalBounds);
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
	    previewFacet.setFullBounds(classifierFacet.getBoundsAfterExistingContainablesAlter(previews, new ContainedPreviewFacet[0]), true);
		}

	  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
	  {
	    previewFacet.setFullBounds(classifierFacet.getBoundsAfterExistingContainablesAlter(previews, exists), true);
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
			classifierFacet.tellContainedAboutResize(previews, bounds);
		}
		
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}

	}

  public ClassifierPreviewGem(UBounds bounds)
  {
    this.bounds = bounds;
    originalBounds = bounds;
  }
  
  private class BasicNodeAppearanceFacetImpl implements BasicNodePreviewAppearanceFacet
  {
	  public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, OrientedPoint oriented,  boolean linkFromContained, UPoint boxPoint, UPoint insidePoint, boolean linkStart)
	  {
	  	if (classifierFacet.isDisplayOnlyIcon())
	  	{
	  		Shape shape = classifierFacet.formShapeForBoundaryCalculation(bounds);
	  		return new BoundaryCalculator(shape).calculateBoundaryPoint(oriented.getPoint(), null, insidePoint);
	  	}

	    boolean useBox = true;
	    UBounds newBounds = bounds;
	    if (linkFromContained)
	    {
	    	double contentsHeightOffset = classifierFacet.getContentsHeightOffsetViaPreviews(previews);
	      newBounds = new UBounds(new UPoint(newBounds.getX(), newBounds.getY() + contentsHeightOffset + 1), new UDimension(newBounds.getWidth(), newBounds.getHeight() - contentsHeightOffset - 2));
	      useBox = false;
	    }
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
      // get the port bounds -- will be null if no ports are present
      UBounds portPreviewBounds = classifierFacet.getPortContainmentBounds(previews);
      if (portPreviewBounds == null)
        return containmentBounds;
      else
        return containmentBounds.union(portPreviewBounds);
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
			containmentBounds = classifierFacet.getContainmentBounds(newBounds);
		}
		
		/**
		 * @see com.hopstepjump.idraw.nodefacilities.previewsupport.BasicNodePreviewAppearanceFacet#getOffsetFromOriginal()
		 */
		public UDimension getOffsetFromOriginal()
		{
			return bounds.getPoint().subtract(originalBounds.getPoint());
		}

	}

  public void connectPreviewFacet(PreviewFacet previewFacet)
  {
  	this.previewFacet = previewFacet;
  }
  
  public void connectPreviewCacheFacet(PreviewCacheFacet previews)
  {
  	this.previews = previews;
  }
  
  public void connectClassifierNodeFacet(ClassifierNodeFacet facet)
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
		return containerFacet;
	}
}

