package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;

public interface ArcAcceptanceFacet extends Facet
{
  /**
   * returns true if the ends are acceptable.
   * if start is set, but end is null, this is just asking for whether the 
   * start alone is ok, when constructing
   * @param start the start of the arc
   * @param end the end of the arc
   * @return true if ok
   */
  public boolean acceptsAnchors(AnchorFacet start, AnchorFacet end);
}
