package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

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
