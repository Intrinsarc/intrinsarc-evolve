package com.hopstepjump.jumble.umldiagrams.base;

import com.hopstepjump.gem.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */
public interface DisplayAsIconFacet extends Facet
{
  public Object displayAsIcon(boolean displayAsIcon);
  public void unDisplayAsIcon(Object memento);
}
