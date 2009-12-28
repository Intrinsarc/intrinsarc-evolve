package com.hopstepjump.idraw.nodefacilities.resizebase;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;


public interface ResizeVetterFacet extends Facet
{
	public void       startResizeVet();
  public UBounds    vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre);
  public UDimension vetResizedExtent(UBounds bounds);
}