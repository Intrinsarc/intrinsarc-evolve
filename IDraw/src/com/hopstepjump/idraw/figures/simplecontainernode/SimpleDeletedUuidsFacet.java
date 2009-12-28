package com.hopstepjump.idraw.figures.simplecontainernode;

import java.util.*;

import com.hopstepjump.gem.*;

public interface SimpleDeletedUuidsFacet extends Facet
{
	public Set<String>[] getAddedAndDeleted();
	public void setAddedAndDeleted(Set<String>[] addedAndDeletedUuids);
	
  public void addDeleted(String uuid);
  public void removeDeleted(String uuid);
  public boolean isDeleted(Set<String> visuallySuppressedUuids, String uuid);
  public void setToShowAll(Set<String> visuallySuppressedUuids);
  public void resetToDefaults();
  public void clean(Set<String> visuallySuppressedUuids, Set<String> uuids);
}
