package com.hopstepjump.idraw.foundation;

import java.util.*;

import com.hopstepjump.gem.*;

public interface AnchorFacet extends Facet
{
	/** return the links as linking facets */
	public Iterator<LinkingFacet> getLinks();
	public boolean hasLinks();
  public void persistence_addLinked(LinkingFacet linked);
	public void addLinked(LinkingFacet linked);
  public void removeLinked(LinkingFacet linked);
  public boolean isInvolvedInMoving(PreviewCacheFacet previewFigures);
  public Set<String> getDisplayStyles(boolean anchorIsTarget);
  public FigureFacet getFigureFacet();
}