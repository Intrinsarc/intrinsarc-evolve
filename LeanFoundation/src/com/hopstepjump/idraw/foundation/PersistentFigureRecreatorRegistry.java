package com.hopstepjump.idraw.foundation;


/**
 *
 * (c) Andrew McVeigh 02-Sep-02
 *
 */
public abstract class PersistentFigureRecreatorRegistry
{
	public static PersistentFigureRecreatorRegistry registry;
	
	public abstract void registerRecreator(PersistentFigureRecreatorFacet recreatorFacet);
	public abstract PersistentFigureRecreatorFacet retrieveRecreator(String name);
}
