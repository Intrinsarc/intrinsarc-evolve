package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.persistence.*;

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
