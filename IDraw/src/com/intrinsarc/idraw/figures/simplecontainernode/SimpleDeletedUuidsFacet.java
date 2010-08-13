package com.intrinsarc.idraw.figures.simplecontainernode;

import java.util.*;

import com.intrinsarc.gem.*;

public interface SimpleDeletedUuidsFacet extends Facet
{
  public void addDeleted(String uuid);
  public void removeDeleted(String uuid);
  public boolean isDeleted(Set<String> visuallySuppressedUuids, String uuid);
  public void setToShowAll(Set<String> visuallySuppressedUuids);
  public void clean(Set<String> visuallySuppressedUuids, Set<String> uuids);
}
