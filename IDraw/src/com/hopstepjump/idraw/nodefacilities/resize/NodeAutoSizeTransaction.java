package com.hopstepjump.idraw.nodefacilities.resize;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.resizebase.*;


public final class NodeAutoSizeTransaction implements TransactionFacet
{
  public static void autoSize(FigureFacet resizeable, boolean autoSized)
  {
    AutoSizedFacet auto = resizeable.getDynamicFacet(AutoSizedFacet.class);
    auto.autoSize(autoSized);
  }
}
