package com.intrinsarc.idraw.arcfacilities.previewsupport;

import com.intrinsarc.idraw.foundation.*;


public final class MoveArcFigureTransaction implements TransactionFacet
{
  public static void move(FigureFacet moveable, ReferenceCalculatedArcPoints referencePoints)
  {
    moveable.getLinkingFacet().move(new CalculatedArcPoints(referencePoints));
  }
}