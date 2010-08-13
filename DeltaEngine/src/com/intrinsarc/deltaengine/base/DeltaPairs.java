package com.intrinsarc.deltaengine.base;

import java.util.*;

public class DeltaPairs
{
  private Map<DeltaPairTypeEnum, Set<DeltaPair>> pairs = new HashMap<DeltaPairTypeEnum, Set<DeltaPair>>();
  
  public DeltaPairs()
  {
  }

  public Set<DeltaPair> get(DeltaPairTypeEnum type)
  {
    return pairs.get(type);
  }
  
  public void put(DeltaPairTypeEnum type, Set<DeltaPair> value)
  {
    pairs.put(type, value);
  }
}
