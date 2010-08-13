package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 02-Sep-02
 *
 */
public interface PersistentFigureRecreatorFacet extends Facet
{
	public FigureFacet createFigure(DiagramFacet diagram, PersistentFigure figure);
	public String getRecreatorName();
}
