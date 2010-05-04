package com.hopstepjump.jumble.umldiagrams.base;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */
public final class DisplayAsIconTransaction implements TransactionFacet
{
  public static void display(FigureFacet iconifiable, boolean displayAsIcon)
  {
  	DisplayAsIconFacet display = (DisplayAsIconFacet) iconifiable.getDynamicFacet(DisplayAsIconFacet.class);
    display.displayAsIcon(displayAsIcon);
  }
}
