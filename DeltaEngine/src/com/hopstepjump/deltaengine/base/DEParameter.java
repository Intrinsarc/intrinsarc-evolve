package com.hopstepjump.deltaengine.base;


public abstract class DEParameter
{
	public abstract String getLiteral();
	public abstract DEAttribute getAttribute();
	
	public DEAttribute getAttribute(DEStratum perspective, DEElement element)
	{
		return DESlot.translateOriginalToConstituent(perspective, element, getAttribute());
	}
	
	public void resolveLazyReferences()
	{
	}
}
