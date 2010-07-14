package com.hopstepjump.idraw.nodefacilities.previewsupport;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.diagramsupport.moveandresize.*;
import com.hopstepjump.idraw.foundation.*;


public final class MoveNodeFigureTransaction implements TransactionFacet
{
  public static void move(FigureFacet figure, UDimension offset)
  {
  	figure.getDynamicFacet(MoveFacet.class).move(offset);
  }
}