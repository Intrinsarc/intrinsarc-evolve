package com.intrinsarc.idraw.arcfacilities.previewsupport;

import java.awt.*;
import java.util.*;
import java.util.List;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;


public final class BasicArcPreviewGem implements Gem
{
  private DiagramFacet diagram;
  private LinkingFacet moveable;
  private ActualArcPoints actualPoints;
  private UPoint start;
  private boolean offsetPointsWhenMoving;
  private boolean hasOutgoingsToPeripheral;
  private Set<LinkingPreviewFacet> linked;
  private FigureFacet isPreviewFor;
  private boolean curved;
  private Map<Class<?>, Facet> dynamicFacets = new HashMap<Class<?>, Facet>();
  private AnchorPreviewFacet anchorFacet = new AnchorPreviewFacetImpl();
  private LinkingPreviewFacet linkingFacet = new LinkingPreviewFacetImpl();
  private PreviewFacet previewFacet = new PreviewFacetImpl();
  private BasicArcPreviewFacet basicPreviewFacet = new BasicArcPreviewFacetImpl();
  private BasicArcContainerPreviewFacet containerPreviewFacet;
  private UPoint originalMiddle;
  
  private class BasicArcPreviewFacetImpl implements BasicArcPreviewFacet
  {
	  public ActualArcPoints getActualPoints()
	  {
	    // this is a bad method, as it exposes too much state -- used by the manipulator.  Look into removing...
	    return actualPoints;
	  }
	
	  public AnchorPreviewFacet getPreview2()
	  {
	    return actualPoints.getPreview2();
	  }
	
	  public void setActualPoints(ActualArcPoints newActualPoints)
	  {
	    if (originalMiddle == null)
	      originalMiddle = anchorFacet.getMiddlePoint();
	    
	    actualPoints = newActualPoints;
	    tellLinked();
	    tellContainer();
	  }
	
	  public CalculatedArcPoints getCalculatedPoints()
	  {
	    return actualPoints.calculateAllPoints();
	  }
	
	  public ReferenceCalculatedArcPoints getReferenceCalculatedPoints(DiagramFacet diagram)
	  {
	    return actualPoints.calculateAllPoints().getReferenceCalculatedArcPoints(diagram);
	  }
	
	  public void setLinkablePreviews(AnchorPreviewFacet preview1, AnchorPreviewFacet preview2)
	  {
	    actualPoints.setNode1Preview(preview1);
	    actualPoints.setNode2Preview(preview2);
	    preview1.addLinked(linkingFacet);
	    preview2.addLinked(linkingFacet);
	  }
	
	  public void formBetterVirtualPoint(UPoint oldVirtual)
	  {
	    actualPoints.setVirtualPoint(actualPoints.calculateBetterVirtualPoint(oldVirtual, actualPoints.getPreview1()));
	  }
	
	  public void setNode2(AnchorFacet node2)
	  {
	    actualPoints.setNode2(node2);
	  }
	
	  public void setNode2Preview(AnchorPreviewFacet preview2)
	  {
	    actualPoints.setNode2Preview(preview2);
	  }
	
	  public void addInternalPoint(UPoint point)
	  {
	    actualPoints.addInternalPoint(point);
	  }
	  
		/**
		 * @see com.intrinsarc.jumble.arcfacilities.moving.BasicArcPreviewFacet#getPreviewFacet()
		 */
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
  }
  
  private class PreviewFacetImpl implements PreviewFacet
  {
	  public boolean canChangeDueToMove()
	  {
	    // return false if all linked can't change due to the move
	    return actualPoints.getPreview1().getPreviewFacet().canChangeDueToMove() || actualPoints.getPreview2().getPreviewFacet().canChangeDueToMove();
	  }
	
	  public boolean hasOutgoingsToPeripheral()
	  {
	    return hasOutgoingsToPeripheral;
	  }
	
	  public void setOutgoingsToPeripheral(boolean outgoingsToPeripheral)
	  {
	    hasOutgoingsToPeripheral = outgoingsToPeripheral;
	  }
	
	
	  public void disconnect()
	  {
	    actualPoints.getPreview1().removeLinked(linkingFacet);
	    actualPoints.getPreview2().removeLinked(linkingFacet);
	  }
	
	  public void end()
	  {
	    actualPoints.removeKinks();
	    MoveArcFigureTransaction.move(moveable.getFigureFacet(), actualPoints.calculateAllPoints().getReferenceCalculatedArcPoints(diagram));
	  }
	
	  public ZNode formView()
	  {
	  	AnchorFacet node1 = actualPoints.getNode1();
	  	if (node1 != null && !node1.getFigureFacet().isShowing())
	  		return null;
	
	  	AnchorFacet node2 = actualPoints.getNode2();
	  	// node 2 can be the basic linking figure
	  	if (node2 != null && !node2.getFigureFacet().isShowing())
	  		return null;
	
			return formBasicView(1, ScreenProperties.getPreviewColor(), new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,4}, 0));
	  }
	
	  /**returns the full bounds of the linkable shape*/
	  public UBounds getFullBounds()
	  {
	  	ZNode view = formView();
	  	if (view == null)
	  		return new UBounds(new UPoint(0,0), new UDimension(0,0));
	    return new UBounds(view.getBounds()).adjustForJazzBounds();
	  }
	
		public UPoint getTopLeftPoint()
		{
			return getFullBounds().getTopLeftPoint();
		}
		
	  public boolean isFullyMoving()
	  {
	    return offsetPointsWhenMoving;
	  }
	
	  public FigureFacet isPreviewFor()
	  {
	    return isPreviewFor;
	  }
	
	  public void move(UPoint current)
	  {
	    if (offsetPointsWhenMoving)
	    {
	      actualPoints.offsetPoints(current.subtract(start));
	      start = current;
	    }
	  }
	
		public void setFullBounds(UBounds resizedBounds, boolean resizedNotMoved)
		{
			// not used in this case -- more of a contained fn
		}
	
	
		public LinkingPreviewFacet getLinkingPreviewFacet()
		{
			return linkingFacet;
		}
		
		public AnchorPreviewFacet getAnchorPreviewFacet()
		{
			return anchorFacet;
		}
		
		public ContainedPreviewFacet getContainedPreviewFacet()
		{
			return null;
		}
		
		public BasicArcContainerPreviewFacet getContainerPreviewFacet()
		{
			return containerPreviewFacet;
		}
		
		public Facet getDynamicFacet(Class facetClass)
		{
			Facet facet = dynamicFacets.get(facetClass);
			if (facet == null)
				throw new FacetNotFoundException("Cannot find facet corresponding to " + facetClass + " in " + this);
			return facet;
		}

		public boolean hasDynamicFacet(Class facetClass)
		{
			return dynamicFacets.containsKey(facetClass);
		}

		/**
		 * @see com.intrinsarc.idraw.foundation.PreviewFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
			return getFullBounds();
		}

    public void registerDynamicFacet(Facet facet, Class facetInterface)
    {
  	  dynamicFacets.put(facetInterface, facet);
    }
	}
  
  private class AnchorPreviewFacetImpl implements AnchorPreviewFacet
  {
	  public void addLinked(LinkingPreviewFacet linking)
	  {
	    linked.add(linking);
	  }
	
	  public void removeLinked(LinkingPreviewFacet linking)
	  {
	    linked.remove(linking);
	  }
	
	  public boolean removeKinksOfLinkingIfIntersects()
	  {
	    return false;
	  }
	
	  public UPoint getMiddlePoint()
	  {
	    return basicPreviewFacet.getCalculatedPoints().calculateMiddlePoint(curved);
	  }
	
		public UPoint calculateBoundaryPoint(OrientedPoint oriented,  boolean linkFromContained, UPoint possibleBoxPoint, UPoint insidePoint, boolean linkStart)
	  {
			// get the list of points
			List points = basicPreviewFacet.getCalculatedPoints().getAllPointsByFlattening(curved);

	    UPoint outsidePoint = oriented.getPoint();
	    int orientation = oriented.getOrientation();
	    UPoint candidate = null;
	    double dist = 1e7;
	
	    // follow the line, and try to interset
			Iterator iter = points.iterator();
			UPoint startPoint = (UPoint) iter.next();
			UPoint start = startPoint;
	    while (iter.hasNext())
	    {
	      UPoint end = (UPoint) iter.next();
	
	      double firstX = Math.min(start.getX(), end.getX());
	      double lastX = Math.max(start.getX(), end.getX());
	      double firstY = Math.min(start.getY(), end.getY());
	      double lastY = Math.max(start.getY(), end.getY());
	
	      if (orientation == OrientedPoint.ORIENTED_VERTICAL || orientation == OrientedPoint.ORIENTED_UNKNOWN)
	      {
	        // try for a horizontal overlap
	        double ptX = outsidePoint.getX();
	        if (ptX >= firstX && ptX <= lastX)
	        {
	          // we have a horizontal overlap -- return a pt on the line with this Y value
	          if (lastX != firstX)
	          {
	            UPoint possible = new UPoint(ptX, start.getY() + (ptX - start.getX()) / (end.getX() - start.getX()) * (end.getY() - start.getY()));
	            double possibleDist = possible.distance(outsidePoint);
	            if (candidate == null || possibleDist < dist)
	            {
	              candidate = possible;
	              dist = possibleDist;
	            }
	          }
	        }
	      }
	
	      if (orientation == OrientedPoint.ORIENTED_HORIZONTAL || orientation == OrientedPoint.ORIENTED_UNKNOWN)
	      {
	        // try for a vertical overlap
	        double ptY = outsidePoint.getY();
	        if (ptY >= firstY && ptY <= lastY)
	        {
	          // we have a horizontal overlap -- return a pt on the line with this Y value
	          if (lastY != firstY)
	          {
	            UPoint possible = new UPoint(start.getX() + (ptY - start.getY()) / (end.getY() - start.getY()) * (end.getX() - start.getX()), ptY);
	            double possibleDist = possible.distance(outsidePoint);
	            if (candidate == null || possibleDist < dist)
	            {
	              candidate = possible;
	              dist = possibleDist;
	            }
	          }
	        }
	      }
	
	      // old end becomes the new start
	      start = end;
	    }
	
	    return candidate != null ? candidate : getMiddlePoint();
	  }
	
		public ZNode formAnchorHighlight(boolean showOk)
	  {
	  	return formBasicView(4, showOk ? new Color(200, 250, 200, 150) : new Color(250, 200, 200, 150), null);
	  }	  	
	
	  /**returns the linkable bounds given that the next point on the line is nextPointAfterConnection*/
	  public UBounds getLinkableBounds(OrientedPoint orientedPoint)
	  {
	    UPoint possible = calculateBoundaryPoint(orientedPoint, false, null, null, true);
	    return new UBounds(possible.subtract(new UDimension(0.1, 0.1)), new UDimension(0.2, 0.2));
	  }
	
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
	}
  
  private class LinkingPreviewFacetImpl implements LinkingPreviewFacet
  {
	  public void anchorPreviewHasChanged(AnchorPreviewFacet linkableNode)
	  {
	    if (originalMiddle == null)
	      originalMiddle = anchorFacet.getMiddlePoint();
	    	    
	    if (!offsetPointsWhenMoving)
	    {
	      actualPoints.setVirtualPoint(actualPoints.calculateBetterVirtualPoint(actualPoints.getVirtualPoint(), linkableNode));
	      tellContainer();
	    }
	    
	    tellLinked();
	  }

		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
	}

  public BasicArcPreviewGem(DiagramFacet diagram, LinkingFacet moveable,
  													ActualArcPoints actualPoints, UPoint start, boolean offsetPointsWhenMoving,
  													boolean curved)
  {
    this.diagram = diagram;
    this.moveable = moveable;
    this.actualPoints = new ActualArcPoints(actualPoints);
    this.offsetPointsWhenMoving = offsetPointsWhenMoving;
    this.hasOutgoingsToPeripheral = false;
    this.linked = new HashSet<LinkingPreviewFacet>();
    this.start = start;
    this.curved = curved;
    if (moveable != null)  // allows previews to be constructed with no underlying -- used for creation
	    this.isPreviewFor = moveable.getFigureFacet();
	    
	  // register the basic arc preview as a dynamic facet, as it needs to be found from a preview
    previewFacet.registerDynamicFacet(basicPreviewFacet, BasicArcPreviewFacet.class);
  }
  
  public void connectBasicContainerPreviewFacet(BasicArcContainerPreviewFacet containerPreviewFacet)
  {
    this.containerPreviewFacet = containerPreviewFacet;
  }
  
	public BasicArcPreviewFacet getBasicArcPreviewFacet()
	{
		return basicPreviewFacet;
	}
	
	public PreviewFacet getPreviewFacet()
	{
		return previewFacet;
	}

  private void tellLinked()
  {
    Iterator iter = linked.iterator();
    while (iter.hasNext())
    {
      LinkingPreviewFacet dependentOnMe = (LinkingPreviewFacet) iter.next();
      dependentOnMe.anchorPreviewHasChanged(anchorFacet);
    }
  }
  
  private void tellContainer()
  {
    // possibly need to tell the children also
    if (containerPreviewFacet != null)
      containerPreviewFacet.newPointsHaveBeenSet(actualPoints, originalMiddle, curved);
    originalMiddle = anchorFacet.getMiddlePoint();
  }

  private ZNode formBasicView(double width, Color color, Stroke stroke)
  {
    CalculatedArcPoints calculated = actualPoints.calculateAllPoints();

    // make a nice outline for the rectilinear part
    List<UPoint> allPoints = calculated.getAllPoints();
    ZShape outline = CalculatedArcPoints.makeConnection(allPoints, curved);
		outline.setPenWidth(width);
		outline.setPenPaint(color);
		if (stroke != null)
			outline.setStroke(stroke);
		ZGroup group = new ZGroup(new ZVisualLeaf(outline));

    // add the virtual point(s)
    if (actualPoints.isVirtual() && ActualArcPoints.isVirtualPointDebuggingOn())
    {
      int offset = 8;
      UPoint point = actualPoints.getVirtualPoint();
      UBounds virtualBounds = new UBounds(point, new UDimension(offset, offset)).addToPoint(new UDimension(-offset/2, -offset/2));
      ZEllipse ell = new ZEllipse(virtualBounds.getX(), virtualBounds.getY(), virtualBounds.getWidth(), virtualBounds.getHeight());
      ell.setFillPaint(ScreenProperties.getHighlightColor());
      ell.setPenPaint(Color.black);
      ZNode node = new ZVisualLeaf(ell);
      group.addChild(node);
    }

    return group;
  }
}