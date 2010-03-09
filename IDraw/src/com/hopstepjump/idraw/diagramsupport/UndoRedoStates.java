package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

public class UndoRedoStates
{
	private List<UndoRedoState> states = new ArrayList<UndoRedoState>();
	private boolean sealed;
	
	public UndoRedoStates()
	{
	}
	
	public void addState(UndoRedoState state)
	{
		states.add(state);
	}
	
	public List<UndoRedoState> getStates()
	{
		return states;
	}

	public void setSealed(boolean sealed)
	{
		this.sealed = sealed;
	}

	public boolean isSealed()
	{
		return sealed;
	}
}
