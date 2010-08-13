package com.intrinsarc.idraw.nodefacilities.nodesupport;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

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
