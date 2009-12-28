package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 */
public interface HideContentsFacet extends Facet
{
  public Object hideContents(boolean autoSized);
  public void unHideContents(Object memento);
}
