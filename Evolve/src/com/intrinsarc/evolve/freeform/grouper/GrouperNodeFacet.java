/*
 * Created on Jan 1, 2004 by Andrew McVeigh
 */
package com.intrinsarc.evolve.freeform.grouper;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface GrouperNodeFacet extends Facet
{
	public UBounds getContainmentBounds(UBounds newBounds);
	public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews);
  public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds);
}
