package com.intrinsarc.evolve.umldiagrams.portnode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;

public interface PortAddFacet extends Facet
{
  public void addPort(FigureReference reference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location);
  public void replacePort(FigureReference reference, FigureReference toReplace, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject);
}
