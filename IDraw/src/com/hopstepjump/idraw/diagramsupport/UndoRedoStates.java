package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

public class UndoRedoStates
{
	private List<UndoRedoState> states = new ArrayList<UndoRedoState>();
	
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
}
