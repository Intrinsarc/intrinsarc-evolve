package com.intrinsarc.idraw.nodefacilities.resize;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.resizebase.*;


public final class NodeResizeTransaction implements TransactionFacet
{
  public static void resize(FigureFacet figure, UBounds resizedBounds)
  {
  	figure.getDynamicFacet(ResizeFacet.class).resize(resizedBounds);
  }
}