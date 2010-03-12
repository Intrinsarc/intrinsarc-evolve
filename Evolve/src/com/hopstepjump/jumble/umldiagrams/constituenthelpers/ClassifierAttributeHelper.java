package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;

public class ClassifierAttributeHelper extends ClassifierConstituentHelper
{
  public ClassifierAttributeHelper(BasicNodeFigureFacet classifierFigure, FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean top)
  {
    super(
        classifierFigure,
        container,
        container.isShowing(),
        container.getContainerFacet().getContents(),
        ConstituentTypeEnum.DELTA_ATTRIBUTE,
        deleted,
        top);
  }

  @Override
  public Command makeAddCommand(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace,
      boolean top)
  {
    // look for the location amongst existing elements which this might be replacing
    UPoint location = null;
    
    for (FigureFacet f : currentInContainerIgnoringDeletes)
    {
      // don't delete if this is deleted -- this is covered elsewhere
      Element originalSubject = getOriginalSubject(f.getSubject());
      
      if (addOrReplace.getUuid().equals(originalSubject.getUuid()))
      {
        location = f.getFullBounds().getTopLeftPoint().subtract(new UDimension(0, 1));
        break;
      }
    }
    
    AddFeatureCommand.add(
        container,
        classifierFigure.getDiagram().makeNewFigureReference(),
        new AttributeCreatorGem().getNodeCreateFacet(),
        null,
        addOrReplace.getConstituent().getRepositoryObject(),
        null,
        location);
    return null;
  }
}
