package com.intrinsarc.deltaengine.base;

import java.util.*;

public abstract class DESlot extends DEObject
{
	public abstract DEAttribute getAttribute();
	public abstract List<DEParameter> getValue();
	
	public DEAttribute getAttribute(DEStratum perspective, DEElement element)
	{
		return translateOriginalToConstituent(perspective, element, getAttribute());
	}
	
	public static DEAttribute translateOriginalToConstituent(DEStratum perspective, DEElement element, DEAttribute original)
	{
    // find the attribute in the component which we reference (as the original)
    for (DeltaPair pair : element.getDeltas(ConstituentTypeEnum.DELTA_ATTRIBUTE).getConstituents(perspective))
    {
      if (pair.getOriginal() == original)
      	return pair.getConstituent().asAttribute();
    }
    return null;
	}
}
