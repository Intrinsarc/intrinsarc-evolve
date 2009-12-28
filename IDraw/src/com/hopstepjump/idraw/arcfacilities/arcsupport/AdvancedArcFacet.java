package com.hopstepjump.idraw.arcfacilities.arcsupport;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface AdvancedArcFacet extends Facet
{
  void addOnePreviewFigure(
      PreviewCacheFacet previews,
      DiagramFacet diagram,
      ActualArcPoints actualArcPoints,
      UPoint start,
      boolean focus,
      boolean curved,
      boolean offsetWhenMoving);
  
  Manipulators getSelectionManipulators(
      DiagramViewFacet diagramView,
      boolean favoured,
      boolean firstSelected,
      boolean allowTYPE0Manipulators,
      CalculatedArcPoints calculatedPoints,
      boolean curved);
}
