package com.hopstepjump.jumble.umldiagrams.associationarc;

import com.hopstepjump.gem.*;

public interface ChangeUnidirectionalityFacet extends Facet
{
  public Object setUnidirectionality(boolean unidirectional);
  public void unSetUnidirectionality(Object memento);
}
