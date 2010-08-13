package com.intrinsarc.evolve.umldiagrams.base;

import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */
public final class DisplayAsIconTransaction implements TransactionFacet
{
  public static void display(FigureFacet iconifiable, boolean displayAsIcon)
  {
  	DisplayAsIconFacet display = iconifiable.getDynamicFacet(DisplayAsIconFacet.class);
    display.displayAsIcon(displayAsIcon);
  }
}
