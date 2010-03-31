package com.hopstepjump.idraw.figurefacilities.selectionbase;

import com.hopstepjump.idraw.foundation.*;

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
    return (LocationFacet) figure.getDynamicFacet(LocationFacet.class);
  }
}

