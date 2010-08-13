package com.intrinsarc.deltaengine.base;

import java.util.*;


public class DummyDeltas implements IDeltas
{
	public DummyDeltas()
	{
	}

  public Set<ErrorDescription> isWellFormed(DEStratum perspective)
  {
  	return new HashSet<ErrorDescription>();
  }
  
  public Set<DeltaPair> getConstituents(DEStratum perspective)
  {
  	return new HashSet<DeltaPair>();
  }

	public Set<DeltaPair> getConstituents(DEStratum perspective, boolean omitSynthetics)
  {
		return new HashSet<DeltaPair>();
  }

  public Set<DeltaPair> getPairs(DEStratum perspective, DeltaPairTypeEnum toGet)
  {
  	return new HashSet<DeltaPair>();
  }
  
	public Collection<String> getIds(DEStratum perspective, DeltaIdTypeEnum idType)
	{
		return new ArrayList<String>();
	}

	public Set<DeltaPair> getAddObjects()
	{
		return new HashSet<DeltaPair>();
	}

	public Set<String> getDeleteObjects()
	{
		return new HashSet<String>();
	}

	public Set<DeltaPair> getReplaceObjects()
	{
		return new HashSet<DeltaPair>();
	}

	public void clearCache(DEStratum perspective)
	{
	}
}
