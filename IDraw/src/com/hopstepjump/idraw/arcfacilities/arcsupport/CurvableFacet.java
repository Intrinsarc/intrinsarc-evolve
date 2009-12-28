/*
 * Created on Dec 27, 2003 by Andrew McVeigh
 */
package com.hopstepjump.idraw.arcfacilities.arcsupport;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 */
public interface CurvableFacet extends Facet
{
	public Object curve(boolean curved);
	public void unCurve(Object memento);
  public boolean isCurved();
}
