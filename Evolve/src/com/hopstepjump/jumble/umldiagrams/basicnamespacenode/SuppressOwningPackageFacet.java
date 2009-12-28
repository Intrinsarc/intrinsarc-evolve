package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import com.hopstepjump.gem.*;

/**
 *
 * (c) Andrew McVeigh 01-Oct-02
 *
 */
public interface SuppressOwningPackageFacet extends Facet
{
  public Object setSuppressOwningPackage(boolean showOwningPackage);
  public void unSetSupressOwningPackage(Object memento);
}
