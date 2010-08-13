package com.intrinsarc.idraw.figurefacilities.textmanipulation;

import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;


public final class SetTextTransaction implements TransactionFacet
{
  public static void set(FigureFacet textable, String text, Object listSelection, boolean unsuppress)
  {
  	textable.getDynamicFacet(TextableFacet.class).setText(text, listSelection, unsuppress);
  }
}