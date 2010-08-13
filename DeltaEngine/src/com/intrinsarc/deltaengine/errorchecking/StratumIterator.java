package com.intrinsarc.deltaengine.errorchecking;

import java.util.*;

import com.intrinsarc.deltaengine.base.*;

public abstract class StratumIterator
{
	private List<? extends DEStratum> toCheck;
	private int startFrom;
	private boolean deepCheck;

	public StratumIterator(List<? extends DEStratum> toCheck, int startFrom, boolean deepCheck)
	{
		this.toCheck = toCheck;
		this.startFrom = startFrom;
		this.deepCheck = deepCheck;
	}
	
	public void iterate()
	{
  	int size = toCheck.size();
  	final int count[] = {0};
  	for (int lp = startFrom; lp < size; lp++)
  	{
  		// treat the current package as the perspective
  		DEStratum perspective = toCheck.get(lp);
  		// if this is destructive, check all that came before the topmost
  		if (deepCheck || perspective.isDestructive() || joinsDestructive(perspective))
  		{
	  		for (int before = 0; before <= lp; before++)
	  		{
	  			DEStratum current = toCheck.get(before);
	  			if (perspective.getTransitivePlusMe().contains(current))
	  			{
	  				process(perspective, current);
	  				count[0]++;
	  			}
	  		}
  		}
  		else
  		{
  			process(perspective, perspective);
  			count[0]++;
  		}
  	}
	}

	public static boolean joinsDestructive(DEStratum perspective)
	{
		// if this perspective joins 2 previously unconnected destructive strata, then force a full check
		Set<DEStratum> pkgs = perspective.getTransitive();
		for (DEStratum p : pkgs)
			if (p.isDestructive())
			{
				// look for another package that depends on this
				boolean another = false;
				for (DEStratum q : pkgs)
					if (q != p && q.getTransitive().contains(p))
					{
						another = true;
						break;
					}
				if (!another)
					return true;
			}
		return false;
	}

	protected abstract void process(DEStratum perspective, DEStratum current);
}

