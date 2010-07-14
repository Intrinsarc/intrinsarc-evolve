package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

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
