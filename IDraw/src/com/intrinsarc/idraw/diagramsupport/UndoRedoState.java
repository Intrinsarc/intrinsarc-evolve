package com.intrinsarc.idraw.diagramsupport;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;

public class UndoRedoState
{
	private DiagramChangeActionEnum action;
	private PersistentFigure persistentFigure;
	private PersistentFigure afterPersistentFigure;
	
	public UndoRedoState(DiagramChangeActionEnum action, PersistentFigure state)
	{
		this.action = action;
		this.persistentFigure = state;
	}
	
	public DiagramChangeActionEnum getAction()
	{
		return action;
	}

	public PersistentFigure getPersistentFigure()
	{
		return persistentFigure;
	}

	public void setAfterPersistentFigure(PersistentFigure afterPersistentFigure)
	{
		this.afterPersistentFigure = afterPersistentFigure;
	}

	public PersistentFigure getPersistentFigureAfter()
	{
		return afterPersistentFigure;
	}
}
