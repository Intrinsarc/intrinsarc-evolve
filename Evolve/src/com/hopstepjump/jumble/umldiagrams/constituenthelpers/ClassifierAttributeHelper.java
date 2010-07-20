package com.hopstepjump.jumble.umldiagrams.constituenthelpers;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.nodefacilities.nodesupport.*;
import com.hopstepjump.jumble.umldiagrams.base.*;
import com.hopstepjump.jumble.umldiagrams.featurenode.*;

public class ClassifierAttributeHelper extends ClassifierConstituentHelper
{
	private boolean suppressUnlessElsewhere;
	
  public ClassifierAttributeHelper(BasicNodeFigureFacet classifierFigure, FigureFacet container, SimpleDeletedUuidsFacet deleted, boolean suppressUnlessElsewhere)
  {
    super(
        classifierFigure,
        container,
        container.isShowing(),
        container.getContainerFacet().getContents(),
        ConstituentTypeEnum.DELTA_ATTRIBUTE,
        deleted);
    this.suppressUnlessElsewhere = suppressUnlessElsewhere;
  }

  @Override
  public void makeAddTransaction(
      DEStratum perspective,
      Set<FigureFacet> currentInContainerIgnoringDeletes,
      BasicNodeFigureFacet classifierFigure,
      FigureFacet container,
      DeltaPair addOrReplace)
  {
		DEComponent component = GlobalDeltaEngine.engine.locateObject(classifierFigure.getSubject()).asComponent();
		FigureFacet[] figures = findClassAndConstituentFigure(classifierFigure, perspective, component, addOrReplace, suppressUnlessElsewhere);
		if (figures == null)
		{
			if (suppressUnlessElsewhere)
			{
				addDeletedUuid(addOrReplace.getUuid());
				return;
			}
			figures = new FigureFacet[]{classifierFigure, null};
		}
		
    // look for the location amongst existing elements which this might be replacing
    UPoint location = figures[1] == null ? classifierFigure.getFullBounds().getBottomRightPoint() : figures[1].getFullBounds().getTopLeftPoint().subtract(new UDimension(0, 1));
    
    AddFeatureTransaction.add(
        container,
        classifierFigure.getDiagram().makeNewFigureReference(),
        new AttributeCreatorGem().getNodeCreateFacet(),
        null,
        addOrReplace.getConstituent().getRepositoryObject(),
        null,
        location);
  }
}
