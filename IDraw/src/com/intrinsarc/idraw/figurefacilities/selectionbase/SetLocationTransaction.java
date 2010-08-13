package com.intrinsarc.idraw.figurefacilities.selectionbase;

import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 04-Oct-02
 *
 */
public final class SetLocationTransaction implements TransactionFacet
{
  public static void locate(FigureFacet locatable)
  {
	  getLocatable(locatable).setLocation();
	}

  private static LocationFacet getLocatable(FigureFacet figure)
  {
    return figure.getDynamicFacet(LocationFacet.class);
  }
}

