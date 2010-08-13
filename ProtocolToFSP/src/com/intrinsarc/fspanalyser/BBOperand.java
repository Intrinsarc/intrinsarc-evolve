package com.intrinsarc.fspanalyser;


public class BBOperand extends AbstractBBOperand
{
  public BBOperand()
  {
  }

  public boolean containsInfiniteLoop()
  {
    for (BBInteractionFragment bbFragment : constituents)
    {
      if (bbFragment.containsInfiniteLoop())
        return true;
    }
    return false;
  }
}
