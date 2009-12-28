/*
 * Created on Jan 1, 2004 by Andrew McVeigh
 */
package com.hopstepjump.jumble.freeform.grouper;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface GrouperNodeFacet extends Facet
{
	public UBounds getContainmentBounds(UBounds newBounds);
	public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews);
  public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds);
}
