package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;

/**
 * @author Andrew
 */

public interface PortNodeFacet extends Facet
{
  public boolean isClassScope();
  public boolean isPrivate();
  public boolean isLinkedTextSuppressed();
  public UDimension getLinkedTextOffset();
}
