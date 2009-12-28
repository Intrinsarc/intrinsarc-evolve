package com.hopstepjump.jumble.umldiagrams.classifiernode;

import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.constituenthelpers.*;

public class ConcreteClassPartHelper extends ClassPartHelper
{

  public ConcreteClassPartHelper(BasicNodeFigureFacet classifierFigure, FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean top)
  {
    super(classifierFigure, container, deleted, top, new PartCreatorGem().getNodeCreateFacet());
  }

}
