package com.hopstepjump.idraw.diagramsupport;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

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
