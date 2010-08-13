package com.intrinsarc.evolve.deltaview;

import java.util.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;

public interface DelegatedDeltaAdornerFacet extends Facet
{
  public Map<FigureFacet, Integer> getDeltaDisplaysAtHome();
}
