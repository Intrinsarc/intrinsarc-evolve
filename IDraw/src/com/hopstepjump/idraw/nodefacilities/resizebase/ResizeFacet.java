package com.hopstepjump.idraw.nodefacilities.resizebase;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */


public interface ResizeFacet extends Facet
{
  public Object resize(UBounds bounds);
  public void unResize(Object memento);
}