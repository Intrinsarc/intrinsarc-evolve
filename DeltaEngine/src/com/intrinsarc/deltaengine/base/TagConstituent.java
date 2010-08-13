package com.intrinsarc.deltaengine.base;

import java.util.*;

public class TagConstituent extends DEConstituent
{
	private String UUID;
	private String tag;
	
	public TagConstituent(String UUID, String tag)
	{
		this.UUID = UUID;
		this.tag = tag;
	}
	
	@Override
	public String getName()
	{
		return tag;
	}
	
	@Override
	public DEObject getParent()
	{
		return null;
	}

	@Override
	public Object getRepositoryObject()
	{
		return null;
	}

	@Override
	public String getUuid()
	{
		return UUID;
	}

	@Override
	public List<DEAppliedStereotype> getAppliedStereotypes()
	{
		return null;
	}
	
  public TagConstituent asTag()
  {
  	return this;
  }

  @Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof TagConstituent))
			return false;
		TagConstituent other = (TagConstituent) obj;
		return UUID.equals(other.UUID) && tag.equals(other.tag);
	}

	@Override
	public int hashCode()
	{
		return UUID.hashCode() ^ tag.hashCode();
	}
}
