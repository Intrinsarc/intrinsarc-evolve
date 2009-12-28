package com.hopstepjump.deltaengine.base;

import java.util.*;

public abstract class DEAttribute extends DEConstituent
{
  public DEAttribute()
  {
    super();
  }

  @Override
  public DEAttribute asAttribute()
  {
    return this;
  }
  
  public abstract DEElement getType();
  public abstract List<DEParameter> getDefaultValue();
  public abstract boolean isReadOnly();
  public abstract boolean isWriteOnly();
  public abstract boolean isSuppressGeneration();
}
