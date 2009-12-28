package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;

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
