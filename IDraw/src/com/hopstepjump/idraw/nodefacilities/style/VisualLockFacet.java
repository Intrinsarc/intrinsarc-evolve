package com.hopstepjump.idraw.nodefacilities.style;

import com.hopstepjump.gem.*;

public interface VisualLockFacet extends Facet
{
	Object lock(boolean lock);
	void unLock(Object memento);
	boolean isLocked();
}
