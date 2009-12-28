package com.hopstepjump.jumble.umldiagrams.portnode;

import java.util.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.previewsupport.*;
import com.hopstepjump.layout.*;

import edu.umd.cs.jazz.*;

/**
 * @author andrew
 */
public class PortNodeContainerPreviewGem
{
  private ContainerPreviewFacetImpl containerPreviewFacet = new ContainerPreviewFacetImpl();
  private PreviewFacet previewFacet;
  private PreviewCacheFacet previews;
  private PortNodePreviewFacetImpl portNodePreviewFacet = new PortNodePreviewFacetImpl();
  private ClosestLine originalClosest;
  private UBounds originalBounds;
  private BasicNodePreviewUnusualSizingFacetImpl unusualSizingFacet =
    new BasicNodePreviewUnusualSizingFacetImpl();
  private boolean elided;
  private List<String> inferredProvided;
  private List<String> inferredRequired;
	private UBounds classBounds;
  
  private class BasicNodePreviewUnusualSizingFacetImpl implements BasicNodePreviewUnusualSizingFacet
  {
    public UBounds getFullBoundsForContainment()
    {
      // intersect the current bounds of this with any bounds of ports
      UBounds bounds = previewFacet.getFullBounds();
      ContainerFacet container = previewFacet.isPreviewFor().getContainerFacet();
      for (Iterator iter = container.getContents(); iter.hasNext();)
      {
        FigureFacet contained = (FigureFacet) iter.next();
        PreviewFacet preview = previews.getCachedPreview(contained);
        bounds = bounds.union(preview.getFullBoundsForContainment());
      }
      
      // possibly union with the inferred size
      ContainerFacet cont = previewFacet.isPreviewFor().getContainerFacet();
      if (inferredProvided != null && inferredRequired != null && cont != null)
      {
      	PreviewFacet cls = previews.getCachedPreview(cont.getFigureFacet());
		    OuterBox outer = PortNodeGem.computeInferredInterfaceBounds(
		    		cls == null ? classBounds : cls.getFullBounds(),
		    		previewFacet.getFullBounds(),
		    		inferredProvided,
		    		inferredRequired,
		    		new int[]{0});
		    bounds = bounds.union(outer.getLayoutBounds());
      }
      
      return bounds;
    }
  }
  
  private class ContainerPreviewFacetImpl implements ContainerPreviewFacet
  {
  	public ZNode formContainerHighlight(boolean showOk)
	  {
  	  return null;
	  }
	
		/**
		 * @see com.hopstepjump.idraw.foundation.ContainerPreviewFacet#recalculateSizeForContainables()
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
      // make sure that the port contraint algorithm knows if we are resizing, not to reset the size
      if (resizedNotMoved)
        setOriginalBounds(newBounds);
      
		  UDimension offset = newBounds.getTopLeftPoint().subtract(oldBounds.getTopLeftPoint());
		  // get any linked text entries
		  ContainerFacet container = previewFacet.isPreviewFor().getContainerFacet();
		  for (Iterator iter = container.getContents(); iter.hasNext();)
		  {
		    FigureFacet contained = (FigureFacet) iter.next();
		    PreviewFacet preview = previews.getCachedPreview(contained);
		    preview.setFullBounds(preview.getFullBounds().addToPoint(offset), resizedNotMoved);
		  }
		}
		
		public PreviewFacet getPreviewFacet()
		{
			return previewFacet;
		}
	}
  
  private class PortNodePreviewFacetImpl implements PortNodePreviewFacet
  {
    public ClosestLine getOriginalClosestLine()
    {
      return originalClosest;
    }

    public ClosestLine getCurrentClosestLine(UBounds newContainerBounds)
    {
      return calculateClosest(newContainerBounds);
    }

    public UBounds getOriginalBounds()
    {
      return originalBounds;
    }

    public boolean isPrivate()
    {
      PortNodeFacet portNodeFacet =
        (PortNodeFacet) previewFacet.isPreviewFor().getDynamicFacet(PortNodeFacet.class);
      return portNodeFacet.isPrivate(); 
    }

    public boolean isElided()
    {
      return elided;
    }
  }
  
  private ClosestLine calculateClosest(UBounds cls)
  {
    UBounds port = previewFacet.getFullBounds();
    
    // get the current closest line
    return classifyPoint(port.getMiddlePoint(), cls);
  }
  
  public static ClosestLine classifyPoint(UPoint point, UBounds bounds)
  {
    // if bound is null, don't bother
    if (bounds == null)
      return new ClosestLine(point, point, point, true, 0);
    
    // classify and return the closest line
    ClosestLine closest[] = new ClosestLine[4];
    closest[0] = new ClosestLine(point, bounds.getTopLeftPoint(), bounds.getTopRightPoint(), true, 0);
    closest[1] = new ClosestLine(point, bounds.getTopLeftPoint(), bounds.getBottomLeftPoint(), false, 1);
    closest[2] = new ClosestLine(point, bounds.getBottomLeftPoint(), bounds.getBottomRightPoint(), true, 2);
    closest[3] = new ClosestLine(point, bounds.getTopRightPoint(), bounds.getBottomRightPoint(), false, 3);

    return chooseBestClosestLine(closest);
  }
  
  /**
   * @param closest
   * @return
   */
  public static ClosestLine chooseBestClosestLine(ClosestLine[] closest)
  {
    ClosestLine chosen = closest[0];
    for (int lp = 1; lp < closest.length; lp++)
    {
      if (closest[lp].getDistance() < chosen.getDistance())
      {
        chosen = closest[lp];
      }
    }
    
    return chosen;
  }  
  
  private void setOriginalBounds(UBounds newOriginalBounds)
  {
    originalBounds = newOriginalBounds;
  }
  
  public PortNodeContainerPreviewGem(UBounds classBounds, boolean elided, PreviewFacet previewFacet, PreviewCacheFacet previews, List<String> inferredProvided, List<String> inferredRequired)
  {
    this.previewFacet = previewFacet;
    this.previews = previews;
    this.classBounds = classBounds;

    // register the dynamic port node preview facet
    previewFacet.registerDynamicFacet(portNodePreviewFacet, PortNodePreviewFacet.class);
    
    // the container bounds will do
    originalBounds = previewFacet.getFullBounds();
    originalClosest = calculateClosest(classBounds);
    this.elided = elided;
    
    this.inferredProvided = inferredProvided;
    this.inferredRequired = inferredRequired;
  }
  
  public ContainerPreviewFacet getContainerPreviewFacet()
  {
    return containerPreviewFacet;
  }
  
  public BasicNodePreviewUnusualSizingFacet getBasicNodePreviewUnsualSizingFacet()
  {
    return unusualSizingFacet;
  }
}
