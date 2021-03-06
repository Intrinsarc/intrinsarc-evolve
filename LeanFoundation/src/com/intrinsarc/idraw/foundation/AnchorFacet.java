package com.intrinsarc.idraw.foundation;

import java.util.*;

import com.intrinsarc.gem.*;

public interface AnchorFacet extends Facet
{
	/** return the links as linking facets */
	public Iterator<LinkingFacet> getLinks();
	public boolean hasLinks();
	public void addLinked(LinkingFacet linked);
  public void removeLinked(LinkingFacet linked);
  public boolean isInvolvedInMoving(PreviewCacheFacet previewFigures);
  public Set<String> getDisplayStyles(boolean anchorIsTarget);
  public FigureFacet getFigureFacet();

  public void persistence_addLinked(LinkingFacet linked);
	public void cleanUp();
}