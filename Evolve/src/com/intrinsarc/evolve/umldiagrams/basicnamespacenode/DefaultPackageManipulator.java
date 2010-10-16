package com.intrinsarc.evolve.umldiagrams.basicnamespacenode;

import com.intrinsarc.evolve.umldiagrams.classifiernode.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public class DefaultPackageManipulator extends FieldPopupManipulator
{
	public DefaultPackageManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure)
	{
		super(coordinator, diagramView, figure, "Package:", false, false);
		setStartText(getPackageName());
	}

	public void setTextAndFinish(String text)
	{
		coordinator.startTransaction("Set default package", "Unset default package");
		StereotypeUtilities.setStringRawStereotypeAttribute(
				getElement(),
				CommonRepositoryFunctions.DEFAULT_PACKAGE,
				text);
		coordinator.commitTransaction();						
	}
	
  private String getPackageName()
  {
  	return StereotypeUtilities.extractStringProperty(getElement(), CommonRepositoryFunctions.DEFAULT_PACKAGE);
  }
}