package com.hopstepjump.idraw.nodefacilities.style;

import java.awt.*;

import com.hopstepjump.gem.*;

public interface StylableFacet extends Facet
{
  public Object setFill(Color fill);
  public void unSetFill(Object memento);
}
