package com.intrinsarc.idraw.nodefacilities.previewsupport;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.moveandresize.*;
import com.intrinsarc.idraw.foundation.*;


public final class MoveNodeFigureTransaction implements TransactionFacet
{
  public static void move(FigureFacet figure, UDimension offset)
  {
  	figure.getDynamicFacet(MoveFacet.class).move(offset);
  }
}