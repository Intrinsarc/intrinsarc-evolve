/*
 * Created on Jan 2, 2004 by Andrew McVeigh
 */
package com.hopstepjump.jumble.freeform.grouper;

import com.hopstepjump.gem.*;

/**
 * @author Andrew
 */
public interface LocateTextFacet extends Facet
{
	public Object locateText(boolean inTop);
	public void unLocateText(Object memento);
}
