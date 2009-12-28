package com.hopstepjump.idraw.figures.simplecontainernode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 06-Aug-02
 *
 */
public interface SimpleContainerPreviewFacet extends Facet
{
	public UBounds getMinimumBoundsFromPreviews(PreviewCacheFacet previews);
	public boolean isEmpty();
}
