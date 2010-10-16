package com.intrinsarc.evolve.umldiagrams.classifiernode;

import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.idraw.foundation.*;

public class ImplementationClassManipulator extends FieldPopupManipulator
{
	public ImplementationClassManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView, FigureFacet figure, String implClass, boolean bad)
	{
		super(coordinator, diagramView, figure, "Class:", bad, true);
		setStartText(implClass);
		this.bad |= implClass.length() > 0 && !ElementErrorChecker.isValidClassName(implClass);
	}

	public void setTextAndFinish(String text)
	{
	}
}
