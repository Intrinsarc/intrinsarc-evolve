package com.intrinsarc.idraw.figures.simplecontainernode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

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
