package com.hopstepjump.test;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public class SimpleFigureRecreator implements PersistentFigureRecreatorFacet
{
	public static final String NAME = "Simple";
	
	public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure)
	{
		SimpleFigure fig = new SimpleFigure(diagram, figure);
		return fig;
	}

	public String getRecreatorName()
	{
		return NAME;
	}
}
