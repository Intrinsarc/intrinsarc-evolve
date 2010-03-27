package com.hopstepjump.idraw.figurefacilities.textmanipulation;

import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;


public final class SetTextTransaction implements TransactionFacet
{
  public static void set(FigureFacet textable, String text, Object listSelection, boolean unsuppress)
  {
  	((TextableFacet) textable.getDynamicFacet(TextableFacet.class)).setText(text, listSelection, unsuppress);
  }
}