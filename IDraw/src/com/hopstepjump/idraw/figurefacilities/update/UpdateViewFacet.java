package com.hopstepjump.idraw.figurefacilities.update;

import com.hopstepjump.gem.*;

public interface UpdateViewFacet extends Facet
{
  public Object updateViewAfterSubjectChanged(boolean isTop);
  public void unUpdateViewAfterSubjectChanged(Object memento);
}
