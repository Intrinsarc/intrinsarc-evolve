package com.hopstepjump.idraw.nodefacilities.nodesupport;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 31-Jul-02
 *
 */
public interface BasicNodeFigureFacet extends FigureFacet
{
	/** a convenience method -- just delegates to the autosized facet */
	public boolean isAutoSized();

	/** get the autosized facet */
	public BasicNodeAutoSizedFacet getBasicNodeAutoSizedFacet();
	
	/** adjust on the diagram */
	public void performResizingTransaction(UBounds newSize);
}
