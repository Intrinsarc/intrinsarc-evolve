package com.intrinsarc.deltaengine.base;

public abstract class DEOperation extends DEConstituent
{
  public DEOperation()
  {
    super();
  }

  @Override
  public DEOperation asOperation()
  {
    return this;
  }

}