package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;

public class ReplacePortTransaction implements TransactionFacet
{
  public static void replace(FigureFacet addable, FigureReference featureReference, FigureReference replaced, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, String executeDescription, String unExecuteDescription)
  {
    getAddable(addable).replacePort(
        featureReference,
        replaced,
        factory,
        properties,
        useSubject,
        relatedSubject);
  }
  private static PortAddFacet getAddable(FigureFacet addable)
  {
    return addable.getDynamicFacet(PortAddFacet.class);
  }
}
