package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

public interface PortAddFacet extends Facet
{
  public Object addPort(Object memento, FigureReference reference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location);
  public void unAddPort(Object memento);
  public Object replacePort(Object memento, FigureReference reference, FigureReference toReplace, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject);
  public void unReplacePort(Object memento);

}
