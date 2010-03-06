package com.hopstepjump.idraw.diagramsupport;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public class UndoRedoState
{
	private UndoRedoAction action;
	private PersistentFigure state;
	
	public UndoRedoState(UndoRedoAction action, PersistentFigure state)
	{
		this.action = action;
		this.state = state;
	}
	
	public UndoRedoAction getAction()
	{
		return action;
	}

	public PersistentFigure getState()
	{
		return state;
	}
}
