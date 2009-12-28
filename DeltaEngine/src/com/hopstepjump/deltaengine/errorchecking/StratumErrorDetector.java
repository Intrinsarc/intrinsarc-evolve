package com.hopstepjump.deltaengine.errorchecking;

import java.util.*;

import com.hopstepjump.deltaengine.base.*;

public class StratumErrorDetector
{
  protected ErrorRegister errors;

  public StratumErrorDetector(
      ErrorRegister errors)
  {
    this.errors = errors;
  }
  
  public int calculatePerspectivePermutations(List<? extends DEStratum> toCheck, int startFrom, boolean deepCheck)
  {
  	final int check[] = {0};
  	new StratumIterator(toCheck, startFrom == -1 ? toCheck.size() - 1 : startFrom, deepCheck)
  	{
			@Override
			protected void process(DEStratum perspective, DEStratum current)
			{
				check[0]++;
			}
  	}.iterate();
  	return check[0];
  }

	public void checkAllAtHome(List<? extends DEStratum> toCheck, int startFrom, int endAt, final IDetectorListener listener)
  {
		final int realStartFrom = startFrom == -1 ? toCheck.size() - 1 : startFrom;
		final int realEndAt = endAt == -1 ? toCheck.size() - 1 : endAt;
		
		for (int lp = realStartFrom; lp <= realEndAt; lp++)
		{
			DEStratum current = toCheck.get(lp);
			if (listener != null)
				listener.haveChecked(current, current);
			determineErrors(current, current);
		}
  }
  
	public void checkAllInOrder(List<? extends DEStratum> toCheck, int startFrom, boolean deepCheck, final IDetectorListener listener)
  {
  	new StratumIterator(toCheck, startFrom == -1 ? toCheck.size() - 1 : startFrom, deepCheck)
  	{
			@Override
			protected void process(DEStratum perspective, DEStratum current)
			{
				if (listener != null)
					listener.haveChecked(perspective, current);
  			determineErrors(perspective, current);
			}
  	}.iterate();  	
  }
  
  public void determineErrors(DEStratum perspective, DEStratum current)
  {
  	// if this is check once, and it has been checked already, don't bother
  	if (!CheckOnceStrata.isOmitCheck(current))
  	{
  		int atStart = errors.getAllErrors().size();
  		findErrors(perspective, current);
  		int atEnd = errors.getAllErrors().size();
  		
  		// if we have had no further errors and it is check once, then we have now checked correctly
  		if (atEnd == atStart)
  			CheckOnceStrata.possiblySetOmitCheck(current);
  	}
  }
  
  protected void findErrors(DEStratum perspective, DEStratum current)
  {
  	// look through the package for all elements, and see if there is an error
  	ErrorChecker checker = new ErrorChecker(errors);
  	checker.performCheck(perspective, current, false);
  	for (DEElement element : current.getChildElements())
  		checker.performCheck(perspective, element, false);  	
  }
}