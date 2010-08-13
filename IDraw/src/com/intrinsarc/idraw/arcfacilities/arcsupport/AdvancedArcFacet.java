package com.intrinsarc.idraw.arcfacilities.arcsupport;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

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
  		ToolCoordinatorFacet coordinator,
      DiagramViewFacet diagramView,
      boolean favoured,
      boolean firstSelected,
      boolean allowTYPE0Manipulators,
      CalculatedArcPoints calculatedPoints,
      boolean curved);
}
