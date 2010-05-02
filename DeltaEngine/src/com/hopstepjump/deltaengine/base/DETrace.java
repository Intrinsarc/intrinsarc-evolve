package com.hopstepjump.deltaengine.base;

public abstract class DETrace extends DEConstituent
{
  public abstract DEElement getTarget();
  
  public DETrace()
  {
    super();
  }

  @Override
  public DETrace asTrace()
  {
    return this;
  }  
}