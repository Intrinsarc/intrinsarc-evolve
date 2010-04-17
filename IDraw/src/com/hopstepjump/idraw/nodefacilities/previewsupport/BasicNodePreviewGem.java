package com.hopstepjump.idraw.nodefacilities.previewsupport;

import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.resize.*;

import edu.umd.cs.jazz.*;


public final class BasicNodePreviewGem implements Gem
{
  private FigureFacet previewing;
  private UPoint start;
  private Set<LinkingPreviewFacet> linked;
  private boolean isFocus;
  private boolean setBounds;
  private boolean hasOutgoingsToPeripheral;
  private PreviewCacheFacet previews;
  private ContainedPreviewFacet containedFacet = new ContainedPreviewFacetImpl();
  private AnchorPreviewFacet anchorFacet = new BasicNodeAnchorFacetImpl();
  private BasicNodePreviewAppearanceFacet appearanceFacet;
  private BasicNodePreviewUnusualSizingFacet unusualSizingFacet;
  private PreviewFacet previewFacet = new PreviewFacetImpl();
  private ContainerPreviewFacet containerFacet;
  private boolean displayPreview;
  private UDimension centreToEdge;
  
  public void setCentreToEdge(UDimension centreToEdge)
  {
  	this.centreToEdge = centreToEdge;
  }
  
  private class BasicNodeAnchorFacetImpl implements AnchorPreviewFacet
  {
	  public void addLinked(LinkingPreviewFacet linking)
	  {
	    linked.add(linking);
	  }
	
	  public void removeLinked(LinkingPreviewFacet linking)
	  {
	    linked.remove(linking);
	  }

	  public ZNode formAnchorHighlight(boolean showOk)
	  {
	  	return appearanceFacet.formAnchorHighlight(showOk);
	  }

	  public UPoint calculateBoundaryPoint(OrientedPoint oriented,  boolean linkFromContained, UPoint boxPoint, UPoint insidePoint, boolean linkStart)
	  {
			return appearanceFacet.calculateBoundaryPoint(previews, oriented, linkFromContained, boxPoint, insidePoint, linkStart);
	  }

	  /**returns the linkable bounds given that the next point on the line is nextPointAfterConnection*/
	  public UBounds getLinkableBounds(OrientedPoint nextPointAfterConnection)
	  {
	  	if (previewFacet.getFullBounds().getDimension().equals(centreToEdge))
	  	{
	  		// middle vertical or middle horizontal?
	  		int orientation = nextPointAfterConnection.getOrientation();
	  		if (orientation == OrientedPoint.ORIENTED_HORIZONTAL)
	  		{
	  			double y = Grid.roundToGrid(previewFacet.getFullBounds().getMiddlePoint()).getY();
	  			double left = previewFacet.getFullBounds().getTopLeftPoint().getX();
	  			double right = previewFacet.getFullBounds().getTopRightPoint().getX();
		  		return new UBounds(new UPoint(left, y), new UDimension(right - left, 0)); 
	  		}
	  		else
	  		{
	  			double x = Grid.roundToGrid(previewFacet.getFullBounds().getMiddlePoint()).getX();
	  			double top = previewFacet.getFullBounds().getTopLeftPoint().getY();
	  			double bottom = previewFacet.getFullBounds().getBottomRightPoint().getY();
		  		return new UBounds(new UPoint(x, top), new UDimension(0, bottom - top)); 	  			
	  		}
	  		
	  	}
	  
	    return previewFacet.getFullBounds();
	  }

	  public UPoint getMiddlePoint()
	  {
	    return new UBounds(previewFacet.getFullBounds()).getMiddlePoint();
	  }

	  public boolean removeKinksOfLinkingIfIntersects()
	  {
	    return true;
	  }

  	public PreviewFacet getPreviewFacet()
  	{
  		return previewFacet;
  	}
  }
  
  private class PreviewFacetImpl implements PreviewFacet
  {
  	private Map<Class, Facet> dynamicFacets = new HashMap<Class, Facet>();

	  public FigureFacet isPreviewFor()
	  {
	    return previewing;
	  }
	
	  public boolean canChangeDueToMove()
	  {
	    return isFocus;
	  }
	
	  public boolean isFullyMoving()
	  {
	    return isFocus;
	  }
	
	  public boolean hasOutgoingsToPeripheral()
	  {
	      return hasOutgoingsToPeripheral;
	  }
	
	  public void setOutgoingsToPeripheral(boolean outgoingsToPeripheral)
	  {
	    hasOutgoingsToPeripheral = outgoingsToPeripheral;
	  }
	
	  public UBounds getFullBounds()
	  {
	    return appearanceFacet.getFullBounds();
	  }
	  
	  public UPoint getTopLeftPoint()
	  {
	  	return getFullBounds().getPoint();
	  }
	
	  public void disconnect()
	  {
	    // arcs disconnect, nodes don't need to
	  }
	
	  public void move(UPoint current)
	  {
	    UDimension offset = current.subtract(start);
	    appearanceFacet.restoreOriginalBounds();
	    appearanceFacet.setBounds(appearanceFacet.getFullBounds().addToPoint(offset), false);
	    setBounds = false;
	    tellLinked();
	  }
	
		public void setFullBounds(UBounds newBounds, boolean resizedNotMoved)
	  {
	    setBounds = true;
	    UBounds oldBounds = previewFacet.getFullBounds();
	    appearanceFacet.setBounds(newBounds, resizedNotMoved);
	    if (containerFacet != null)
		    containerFacet.boundsHaveBeenSet(oldBounds, newBounds, resizedNotMoved);
	    tellLinked();
	  }

	  public void end()
	  {
	    // make a new move cmd for this shape
	    if (setBounds)
	    {
	      NodeResizeTransaction.resize(previewing, appearanceFacet.getFullBounds());
	    }
	    else
	    {
	      // make a new move cmd for this shape
	      UDimension offset = appearanceFacet.getOffsetFromOriginal();
	      if (offset.distance() != 0)
	        MoveNodeFigureTransaction.move(previewing, offset);
	    }
	  }
	
	  public ContainedPreviewFacet getContainedPreviewFacet()
	  {
	  	return containedFacet;
	  }
	  
	  public ContainerPreviewFacet getContainerPreviewFacet()
	  {
	  	return containerFacet;
	  }
	  
	  public AnchorPreviewFacet getAnchorPreviewFacet()
	  {
	  	return anchorFacet;
	  }
	  
	  public LinkingPreviewFacet getLinkingPreviewFacet()
	  {
	  	return null; // nodes don't have links
	  }

	  public ZNode formView()
	  {
	  	if (displayPreview)
		  	return appearanceFacet.formView(false);
		  else
      {
  		  // faster to do the check separately
        boolean showAll = GlobalPreferences.preferences.getRawPreference(
            new Preference("Advanced", "Show all previews", new PersistentProperty(false))).asBoolean();
  		  if (showAll)
          return appearanceFacet.formView(true);
      }
		  return null;
	  }

		/**
		 * @see com.hopstepjump.jumble.foundation.MainFacet#getDynamicFacet(Class)
		 */
		public Facet getDynamicFacet(Class facetClass)
		{
			Facet facet = dynamicFacets.get(facetClass);
			if (facet == null)
				throw new FacetNotFoundException("Cannot find dynamic preview facet corresponding to " + facetClass + " in " + appearanceFacet);
			return facet;
		}

	
		/**
		 * @see com.hopstepjump.jumble.foundation.MainFacet#hasDynamicFacet(Class)
		 */
		public boolean hasDynamicFacet(Class facetClass)
		{
			return dynamicFacets.containsKey(facetClass);
		}

		/**
		 * @see com.hopstepjump.jumble.nodefacilities.previewsupport.PreviewFacet#registerDynamicFacet(Facet, Class)
		 */
		public void registerDynamicFacet(Facet facet, Class facetInterface)
		{
			dynamicFacets.put(facetInterface, facet);
		}

		/**
		 * @see com.hopstepjump.idraw.foundation.PreviewFacet#getFullBoundsForContainment()
		 */
		public UBounds getFullBoundsForContainment()
		{
      if (unusualSizingFacet != null)
        return unusualSizingFacet.getFullBoundsForContainment();
			return appearanceFacet.getFullBoundsForContainment();
		}
  }
  
  private class ContainedPreviewFacetImpl implements ContainedPreviewFacet
  {
	  /**
		 * @see com.hopstepjump.jumble.foundation.ContainedPreviewFacet#getPreviewFacet()
		 */
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
	}

  public BasicNodePreviewGem(FigureFacet figure, UPoint start, boolean isFocus, boolean displayPreview)
  {
    this.start = start;
    this.linked = new HashSet<LinkingPreviewFacet>();
    this.isFocus = isFocus;
    this.hasOutgoingsToPeripheral = false;  // will be adjusted later by a possible arc
    this.displayPreview = displayPreview;
    useDefaultFacets(figure.getFullBounds());
  }
  
  private void useDefaultFacets(UBounds bounds)
  {
  	BasicNodePreviewAppearanceGem defaultAppearanceGem = new BasicNodePreviewAppearanceGem(bounds);
  	connectBasicNodePreviewAppearanceFacet(defaultAppearanceGem.getBasicNodePreviewAppearanceFacet());
  	defaultAppearanceGem.connectPreviewFacet(previewFacet);
  }
  
  public void connectFigureFacet(FigureFacet figureFacet)
  {
  	previewing = figureFacet;
  }
  
  public void connectPreviewCacheFacet(PreviewCacheFacet previewCache)
  {
  	previews = previewCache;
  }
  
  public void connectBasicNodePreviewAppearanceFacet(BasicNodePreviewAppearanceFacet appearanceFacet)
  {
  	this.appearanceFacet = appearanceFacet;
  }
  
  public void connectContainerPreviewFacet(ContainerPreviewFacet containerFacet)
  {
  	this.containerFacet = containerFacet;
  }
  
  public void connectBasicNodePreviewUnusualSizingFacet(BasicNodePreviewUnusualSizingFacet unusualSizingFacet)
  {
    this.unusualSizingFacet = unusualSizingFacet;
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
}