package com.hopstepjump.idraw.foundation;

import java.io.*;

import com.hopstepjump.idraw.foundation.persistence.*;

/**
 *
 * (c) Andrew McVeigh 12-Aug-02
 *
 */
public final class DiagramChange
{
	public static final int MODIFICATIONTYPE_ADD    = 0;
	public static final int MODIFICATIONTYPE_ADJUST = 1;
	public static final int MODIFICATIONTYPE_REMOVE = 2;
	public static final int MODIFICATIONTYPE_RESYNC = 3;
	
	public static final String[] names = {"MODIFICATIONTYPE_ADD", "MODIFICATIONTYPE_ADJUST",
																				"MODIFICATIONTYPE_REMOVE", "MODIFICATIONTYPE_RESYNC"};

	private FigureFacet figure;
	private PersistentFigure persistentFigure;
	private int modificationType;
	private String figureId;

	public DiagramChange(FigureFacet figure, int modificationType)
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

	public int getModificationType()
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
		return "DiagramChange(figure=" + figure + ", " + names[modificationType] + ")";
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
