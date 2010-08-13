package com.intrinsarc.idraw.foundation;

import java.util.*;

import edu.umd.cs.jazz.*;

public interface DiagramFigureAdornerFacet
{
  public Map<FigureFacet, Integer> determineAdornments(DiagramFacet diagram, Set<FigureFacet> figures);
  public ZNode adornFigure(FigureFacet figure, int style);
  public boolean isEnabled();
}
