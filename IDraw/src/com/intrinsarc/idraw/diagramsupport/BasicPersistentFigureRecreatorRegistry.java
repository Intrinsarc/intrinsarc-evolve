package com.intrinsarc.idraw.diagramsupport;

import java.util.*;

import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 02-Sep-02
 *
 */
public class BasicPersistentFigureRecreatorRegistry extends PersistentFigureRecreatorRegistry
{
	private HashMap<String, PersistentFigureRecreatorFacet> recreators =
	  new HashMap<String, PersistentFigureRecreatorFacet>();

	/**
	 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorRegistry#registerRecreator(PersistentFigureRecreatorFacet)
	 */
	public void registerRecreator(PersistentFigureRecreatorFacet recreatorFacet)
	{
	  String recreatorName = recreatorFacet.getRecreatorName();
	  if (recreators.containsKey(recreatorName))
	    throw new IllegalStateException("Found a duplicate recreator name: " + recreatorName);
		recreators.put(recreatorName, recreatorFacet);
	}

	/**
	 * @see com.intrinsarc.idraw.foundation.PersistentFigureRecreatorRegistry#retrieveRecreator(String)
	 */
	public PersistentFigureRecreatorFacet retrieveRecreator(String name)
	{
		PersistentFigureRecreatorFacet recreatorFacet = recreators.get(name);
		if (recreatorFacet == null)
			throw new PersistentFigureRecreatorNotFoundException("Cannot find recreator " + name);
		return recreatorFacet;
	}

}
