package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;

public class AddPortTransaction implements TransactionFacet
{
  public static void add(FigureFacet addable, FigureReference featureReference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location)
  {
  	getAddable(addable).addPort(
        featureReference,
        factory,
        properties,
        useSubject,
        relatedSubject,
        location);
  }

  private static PortAddFacet getAddable(FigureFacet addable)
  {
    return addable.getDynamicFacet(PortAddFacet.class);
  }
}
