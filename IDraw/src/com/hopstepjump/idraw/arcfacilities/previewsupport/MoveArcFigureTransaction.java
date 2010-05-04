package com.hopstepjump.idraw.arcfacilities.previewsupport;

import com.hopstepjump.idraw.foundation.*;


public final class MoveArcFigureTransaction implements TransactionFacet
{
  public static void move(FigureFacet moveable, ReferenceCalculatedArcPoints referencePoints)
  {
    moveable.getLinkingFacet().move(new CalculatedArcPoints(referencePoints));
  }
}