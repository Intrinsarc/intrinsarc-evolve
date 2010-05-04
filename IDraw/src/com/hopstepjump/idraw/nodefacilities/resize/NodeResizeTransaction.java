package com.hopstepjump.idraw.nodefacilities.resize;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;


public final class NodeResizeTransaction implements TransactionFacet
{
  public static void resize(FigureFacet figure, UBounds resizedBounds)
  {
  	((ResizeFacet) figure.getDynamicFacet(ResizeFacet.class)).resize(resizedBounds);
  }
}