package com.intrinsarc.idraw.nodefacilities.resize;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.resizebase.*;


public final class NodeAutoSizeTransaction implements TransactionFacet
{
  public static void autoSize(FigureFacet resizeable, boolean autoSized)
  {
    AutoSizedFacet auto = resizeable.getDynamicFacet(AutoSizedFacet.class);
    auto.autoSize(autoSized);
  }
}
