package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;


public interface ContainerFacet extends Facet
{
	public void persistence_addContained(FigureFacet contained);
	public void cleanUp();
	
	public boolean insideContainer(UPoint point);
  /** returns true if area sweep in the container bounds is supported */
  public boolean isWillingToActAsBackdrop();
  
  /** get a possible subcontainer willing to accept the drop payload */
  public ContainerFacet getAcceptingSubcontainer(ContainedFacet[] containables);

  /** get the containables for this container -- but get them as FigureFacets, not as ContainedFacets */
  public Iterator<FigureFacet> getContents();
	
  public void removeContents(ContainedFacet[] containables);
  public void addContents(ContainedFacet[] containables);
  
  /** add the child previews to the cache */
  public void addChildPreviewsToCache(PreviewCacheFacet previewCache);
  
  public FigureFacet getFigureFacet();
  /** many containers can also be contained */
  public ContainedFacet getContainedFacet();
  
  /**
   * indicates that this container doesn't directly accept items.  if this is the case,
   * then this container won't show up when trying to move items around.
   * @return
   */
  public boolean directlyAcceptsItems();
  
	public void setShowingForChildren(boolean showing);
}