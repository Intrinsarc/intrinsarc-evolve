package com.hopstepjump.idraw.foundation;

import com.hopstepjump.idraw.*;
import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 12-Aug-02
 *
 */

public final class DiagramChange
{	
	private FigureFacet figure;
	private PersistentFigure persistentFigure;
	private DiagramChangeActionEnum modificationType;
	private String figureId;

	public DiagramChange(FigureFacet figure, DiagramChangeActionEnum modificationType)
	{
		this.figure = figure;
		if (figure != null)
			figureId = figure.getId();
		this.modificationType = modificationType;
	}

	public FigureFacet getFigure()
	{
		return figure;
	}

	public DiagramChangeActionEnum getModificationType()
	{
		return modificationType;
	}

	public int hashCode()
	{
		return figure == null ? 1 : figure.hashCode();
	}

	public boolean equals(Object other)
	{
		if (!(other instanceof DiagramChange))
			return false;

		DiagramChange otherChange = (DiagramChange) other;
		return otherChange.figure == figure && otherChange.modificationType == modificationType;
	}
  
	public String toString()
	{
		return "DiagramChange(figure=" + figure + ", " + modificationType + ")";
	}
  
	public PersistentFigure getPersistentFigure()
	{
		return persistentFigure;
	}

	/**
	 * Returns the figureId.
	 * @return String
	 */
	public String getFigureId()
	{
		return figureId;
	}
}
