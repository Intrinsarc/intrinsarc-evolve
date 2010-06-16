package com.hopstepjump.deltaengine.base;

public abstract class DERequirementsFeature extends DEElement
{
	public DERequirementsFeature()
	{
	}

	@Override
	public void clearCache(DEStratum perspective)
	{
		super.clearCache(perspective);
	}


	@Override
	public DERequirementsFeature asRequirementsFeature()
	{
		return this;
	}
}
