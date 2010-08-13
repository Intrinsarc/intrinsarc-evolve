package com.intrinsarc.idraw.diagramsupport;

import java.util.*;

import com.intrinsarc.idraw.foundation.persistence.*;

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

	public int getSize()
	{
		return states.size();
	}

	public void print()
	{
		for (UndoRedoState s : states)
		{
			PersistentFigure pf = s.getPersistentFigure();
			System.out.println("$$ " + s.getAction() + ", " + pf.getRecreator());
		}
	}
}
