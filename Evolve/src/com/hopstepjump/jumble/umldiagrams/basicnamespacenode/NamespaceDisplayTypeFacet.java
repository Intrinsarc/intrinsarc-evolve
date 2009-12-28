package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import com.hopstepjump.gem.*;

public interface NamespaceDisplayTypeFacet extends Facet
{
  public Object setDisplayType(NamespaceDisplayType type);
  public void unSetDisplayType(Object memento);
  public NamespaceDisplayType getDisplayType();
}
