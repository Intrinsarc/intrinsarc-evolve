package com.hopstepjump.jumble.umldiagrams.portnode;

import java.awt.*;
import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * @author andrew
 */

public class PortCompartmentPreviewGem implements Gem
{
  private static final double OFFSET = Grid.getGridSpace().getWidth();
  private static final double ELIDED_OFFSET = 0;
  private UBounds originalBounds;
  private ContainedPreviewFacet[] removePreviews;
  private ContainedPreviewFacet[] addPreviews;
  private ContainerPreviewFacetImpl containerFacet = new ContainerPreviewFacetImpl();
  private PortCompartmentPreviewFacet portContainerPreviewFacet = new PortContainerPreviewFacetImpl();
  private PreviewFacet previewFacet;
  private PreviewCacheFacet previews;
  private BasicNodePreviewUnusualSizingFacet unusualSizingFacet = new BasicNodePreviewUnusualSizingFacetImpl();
  
  private class BasicNodePreviewUnusualSizingFacetImpl implements BasicNodePreviewUnusualSizingFacet
  {
    public UBounds getFullBoundsForContainment()
    {      
      // if there are no underlyings, return null
      Set<PreviewFacet> underlyings = getAllUnderlyings();
      if (underlyings.size() == 0)
        return null;
      
      // intersect the current bounds of this with any bounds of ports
      UBounds bounds = previewFacet.getFullBounds();
      for (PreviewFacet underlying : underlyings)
      {
        bounds = bounds.union(underlying.getFullBoundsForContainment());
      }
      return bounds;
    }
  }  
  
  private class PortContainerPreviewFacetImpl implements PortCompartmentPreviewFacet
  {
    public boolean hasPorts()
    {
      return getAllUnderlyings().size() != 0;
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
        rect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			}
			else
			{
        rect.setPenPaint(Color.RED);
        rect.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0, new float[]{0,2}, 0));
			}

	    return new ZVisualLeaf(rect);
	  }

	  public void restoreSizeForContainables()
	  {
	  	addPreviews = null;
	  	removePreviews = null;
	    previewFacet.setFullBounds(originalBounds, true);
	    alignPorts();
	  }
	
	  public void resizeToRemoveContainables(ContainedPreviewFacet[] takes, UPoint movePoint)
	  {
	  	removePreviews = takes;
	  	addPreviews = null;
	  	alignPorts();
	  }
	
	  public void resizeToAddContainables(ContainedPreviewFacet[] adds, UPoint movePoint)
	  {
	   	addPreviews = adds;
	  	removePreviews = null;
	  	alignPorts();
	  }
	  
	  /**
     * @param preview
     */
    private void alignPorts()
    {
      UBounds cls = previewFacet.getFullBounds();

      // get all the previews
      Set<PreviewFacet> allPreviews = getAllUnderlyings();
      for (PreviewFacet preview : allPreviews)
	      preview.setFullBounds(alignPort(cls, preview), false);
    }
	  
		/**
     * @param cls
     * @param bounds
     * @return
     */
    private UBounds alignPort(UBounds cls, PreviewFacet portPreview)
    {
      // get the port preview facet
      PortNodePreviewFacet portPreviewFacet =
        (PortNodePreviewFacet) portPreview.getDynamicFacet(PortNodePreviewFacet.class);
      
      // get the original closest line
      ClosestLine originalClosest = portPreviewFacet.getOriginalClosestLine();
      UBounds original = portPreviewFacet.getOriginalBounds();
      
      // get the current closest line
      ClosestLine currentClosest = portPreviewFacet.getCurrentClosestLine(cls);
      
      // if we have changed between horizontal and vertical, flip
      UPoint centre = original.getMiddlePoint();
      UDimension topLeftOffset = centre.subtract(original.getTopLeftPoint());
      UDimension bottomRightOffset = original.getBottomRightPoint().subtract(centre);
      if (currentClosest.isHorizontal() != originalClosest.isHorizontal())
      {
        topLeftOffset = new UDimension(topLeftOffset.getHeight(), topLeftOffset.getWidth());
        bottomRightOffset = new UDimension(bottomRightOffset.getHeight(), bottomRightOffset.getWidth());
        original = new UBounds(centre.subtract(topLeftOffset), centre.add(bottomRightOffset));
      }
      
      // for the new location
      UPoint newCentre = portPreview.getFullBounds().getMiddlePoint();
      UBounds newBounds = new UBounds(newCentre.subtract(topLeftOffset), newCentre.add(bottomRightOffset));
      return limitBounds(portPreviewFacet.isPrivate(), currentClosest, newBounds,
                          portPreviewFacet.isElided() ? ELIDED_OFFSET : OFFSET);
    }
    
    
    public UBounds limitBounds(boolean isPrivate, ClosestLine currentClosest, UBounds newBounds, double offset)
    {
      UBounds bounds;
      if (currentClosest.isHorizontal())
        bounds = limitHorizontalBounds(currentClosest, newBounds, offset);
      else
        bounds = limitVerticalBounds(currentClosest, newBounds, offset);
      
      // handle private ports
      if (isPrivate)
      {
	      switch (currentClosest.getLineNumber())
	      {
	        case 0:
	          bounds = bounds.addToPoint(new UDimension(0, offset*2));
	          break;
	        case 1:
	          bounds = bounds.addToPoint(new UDimension(offset*2, 0));
	          break;
	        case 2:
	          bounds = bounds.addToPoint(new UDimension(0, -offset*2));
	          break;
	        case 3:
	          bounds = bounds.addToPoint(new UDimension(-offset*2, 0));
	          break;
	      }
      }
      
      // ensure that the point is rounded correctly
      return bounds;
    }

    /**
     * @param currentClosest
     * @param newBounds
     * @return
     */
    private UBounds limitHorizontalBounds(ClosestLine currentClosest, UBounds newBounds, double offset)
    {
      double guide = currentClosest.getFirst().getX() + offset;
      double guideDistance = currentClosest.getSecond().getX() - offset - guide;
      
      double port = newBounds.getTopLeftPoint().getX();
      double portDistance = newBounds.getBottomRightPoint().getX() - port;
      
      // make the distance match
      portDistance = Math.min(portDistance, guideDistance);
      portDistance = Math.max(portDistance, offset * 4);
      
      // position start at the correct place
      port = Math.max(port, guide);
      if (port + portDistance > guide + guideDistance)
        port = guide + guideDistance - portDistance;

      UPoint point = new UPoint(port, currentClosest.getFirst().getY() - offset * 2);
      UPoint griddedPoint = Grid.roundToGrid(point);
      return new UBounds(
          new UPoint(griddedPoint.getX(), point.getY()),
          new UDimension(portDistance, offset * 4));
    }

    /**
     * @param currentClosest
     * @param newBounds
     * @return
     */
    private UBounds limitVerticalBounds(ClosestLine currentClosest, UBounds newBounds, double offset)
    {
      double guide = currentClosest.getFirst().getY() + offset;
      double guideDistance = currentClosest.getSecond().getY() - offset - guide;
      
      double port = newBounds.getTopLeftPoint().getY();
      double portDistance = newBounds.getBottomRightPoint().getY() - port;
      
      // make the distance match
      portDistance = Math.min(portDistance, guideDistance);
      portDistance = Math.max(portDistance, offset * 4);
      
      // position start at the correct place
      port = Math.max(port, guide);
      if (port + portDistance > guide + guideDistance)
        port = guide + guideDistance - portDistance;
      
      UPoint point = new UPoint(currentClosest.getFirst().getX() - offset * 2, port);
      UPoint griddedPoint = Grid.roundToGrid(point);
      return new UBounds(
          new UPoint(point.getX(), griddedPoint.getY()),
          new UDimension(offset * 4, portDistance));
      
    }

    /**
		 * @see com.hopstepjump.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
		 */
		public void recalculateSizeForContainables()
		{
		}

	  public void adjustForExistingContainables(ContainedPreviewFacet[] exists, UPoint movePoint)
	  {
	  	addPreviews = exists;
	  	removePreviews = null;
	  	alignPorts();
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
		  // adjust the ports contained within
		  alignPorts();
		}
  }

  public PortCompartmentPreviewGem()
  {
  }
  
  public void connectPreviewFacet(PreviewFacet previewFacet)
  {
  	this.previewFacet = previewFacet;
  	this.originalBounds = previewFacet.getFullBounds();
  }
  
  public void connectPreviewCacheFacet(PreviewCacheFacet previews)
  {
  	this.previews = previews;
  	previewFacet.registerDynamicFacet(portContainerPreviewFacet, PortCompartmentPreviewFacet.class);
  }
  
  public BasicNodePreviewUnusualSizingFacet getBasicNodePreviewUnusualSizingFacet()
  {
    return unusualSizingFacet;
  }
  
  public ContainerPreviewFacet getContainerPreviewFacet()
  {
  	return containerFacet;
  }

  private Set<PreviewFacet> getAllUnderlyings()
	{
		// returns a set of underlying bounds...
		Set<PreviewFacet> all = new HashSet<PreviewFacet>();
		if (addPreviews != null)
		{
			for (ContainedPreviewFacet add : addPreviews)
			  all.add(add.getPreviewFacet());
		}
		
		Iterator iter = previewFacet.isPreviewFor().getContainerFacet().getContents();
		while (iter.hasNext())
		{
			// only add this if we are not removing it
			FigureFacet contained = (FigureFacet) iter.next();
			all.add(previews.getCachedPreviewOrMakeOne(contained));
		}
		
		if (removePreviews != null)
		{
			for (ContainedPreviewFacet remove : removePreviews)
			  all.remove(remove.getPreviewFacet());
		}
		
		return all;
	}
}
