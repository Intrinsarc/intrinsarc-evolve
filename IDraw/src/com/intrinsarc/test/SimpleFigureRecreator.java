package com.intrinsarc.test;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

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
