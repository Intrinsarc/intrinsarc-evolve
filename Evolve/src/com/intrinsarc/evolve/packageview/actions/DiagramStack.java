package com.intrinsarc.evolve.packageview.actions;

import java.util.*;

import com.intrinsarc.idraw.foundation.*;

public class DiagramStack
{
	public static final int MAX_SIZE = 20;
	private List<DiagramReference> stack = new ArrayList<DiagramReference>();
	private int current = -1;
	
	public DiagramStack()
	{
	}
	
	public void addToStack(DiagramReference ref)
	{
		// if the current is this, then don't bother
		if (current >= 0 && stack.get(current).equals(ref))
			return;
		
		// possibly truncate the stack
		for (int size = stack.size() - 1; current != size; size--)
			stack.remove(size);
		current++;
		stack.add(ref);
		
		// have we got too many in the stack?
		if (current > MAX_SIZE - 1)
		{
			stack.remove(0);
			current--;
		}
	}
	
	public DiagramReference getCurrent()
	{
		if (current < 0)
			return null;
		return stack.get(current);
	}
	
	public void goBack()
	{
		if (current > 0)
			current--;
	}
	
	public void goForward()
	{
		if (current < stack.size() - 1)
			current++;
	}
	
	public DiagramReference getPrevious()
	{
		if (current > 0)
			return stack.get(current - 1);
		return null;
	}
	
	public DiagramReference getNext()
	{
		if (current < stack.size() - 1)
			return stack.get(current + 1);
		return null;
	}

	public int getCurrentNumber()
	{
		return current;
	}

	public int getTotalNumber()
	{
		return stack.size();
	}
}
