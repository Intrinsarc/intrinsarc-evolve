package com.hopstepjump.jumble.umldiagrams.associationarc;

import com.hopstepjump.gem.*;

public interface ChangeAssociationTypeFacet extends Facet
{
  public Object setAssociationType(int associationType);
  public void unSetAssociationType(Object memento);
}
