package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

/**
 * @author Andrew
 */
public interface PortNodePreviewFacet extends Facet
{
  public ClosestLine getOriginalClosestLine();
  public ClosestLine getCurrentClosestLine(UBounds newContainer);
  public UBounds getOriginalBounds();
  public boolean isPrivate();
  public boolean isElided();
}
