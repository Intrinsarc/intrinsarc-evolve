package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 */
public interface HideLinkedTextFacet extends Facet
{
  public Object hideLinkedText(boolean linkedText);
  public void unHideLinkedText(Object memento);
}
