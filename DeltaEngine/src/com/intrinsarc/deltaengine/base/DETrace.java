package com.intrinsarc.deltaengine.base;

public abstract class DETrace extends DEConstituent
{
  public abstract DERequirementsFeature getTarget();
  
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