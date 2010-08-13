package com.intrinsarc.deltaengine.base;

import java.util.*;

public class DeltaIds
{
  private Map<DeltaIdTypeEnum, Set<String>> ids = new HashMap<DeltaIdTypeEnum, Set<String>>();

  public DeltaIds()
  {
  }

  public Set<String> get(DeltaIdTypeEnum type)
  {
    return ids.get(type);
  }

  public void put(DeltaIdTypeEnum type, Set<String> value)
  {
    ids.put(type, value);
  }
}
