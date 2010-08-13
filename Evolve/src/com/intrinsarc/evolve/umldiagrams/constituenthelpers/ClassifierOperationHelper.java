package com.intrinsarc.evolve.umldiagrams.constituenthelpers;

import java.util.*;

import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.umldiagrams.base.*;
import com.intrinsarc.evolve.umldiagrams.featurenode.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.nodefacilities.nodesupport.*;

public class ClassifierOperationHelper extends ClassifierConstituentHelper
{

  public ClassifierOperationHelper(BasicNodeFigureFacet classifierFigure, FigureFacet container, SimpleDeletedUuidsFacet deleted)
  {
    super(
        classifierFigure,
        container,
        container.isShowing(),
        container.getContainerFacet().getContents(),
        ConstituentTypeEnum.DELTA_OPERATION,
        deleted);
  }

  @Override
  public void makeAddTransaction(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace)
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
    
    AddFeatureTransaction.add(
        container,
        classifierFigure.getDiagram().makeNewFigureReference(),
        new OperationCreatorGem().getNodeCreateFacet(),
        null,
        addOrReplace.getConstituent().getRepositoryObject(),
        null,
        location);
  }
}
