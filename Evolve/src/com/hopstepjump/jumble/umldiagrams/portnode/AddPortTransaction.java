package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

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
