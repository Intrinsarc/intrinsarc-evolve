package com.intrinsarc.idraw.nodefacilities.resizebase;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;


public interface ResizeVetterFacet extends Facet
{
	public void       startResizeVet();
  public UBounds    vetResizedBounds(DiagramViewFacet view, int corner, UBounds bounds, boolean fromCentre);
  public UDimension vetResizedExtent(UBounds bounds);
}