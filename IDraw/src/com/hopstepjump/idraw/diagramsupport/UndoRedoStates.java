package com.hopstepjump.idraw.diagramsupport;

import java.util.*;

import com.hopstepjump.idraw.foundation.persistence.*;

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
			switch (s.getAction())
			{
			case ADD:
				System.out.println("$$ add " + pf.getRecreator());
				break;
			case REMOVE:
				System.out.println("$$ remove " + pf.getRecreator());
				break;
			case MODIFY:
				System.out.println("$$ modify " + pf.getRecreator());
				break;
			}
		}
	}
}
