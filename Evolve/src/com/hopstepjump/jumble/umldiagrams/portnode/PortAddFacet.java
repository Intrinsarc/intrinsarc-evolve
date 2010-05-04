package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

public interface PortAddFacet extends Facet
{
  public void addPort(FigureReference reference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location);
  public void replacePort(FigureReference reference, FigureReference toReplace, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject);
}
