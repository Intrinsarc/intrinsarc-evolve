package com.hopstepjump.jumble.freeform.image;

import com.hopstepjump.gem.*;


public interface ImagableFacet extends Facet
{
  public Object setImage(String type, byte[] imageData);
  public void unSetImage(Object memento);
}
