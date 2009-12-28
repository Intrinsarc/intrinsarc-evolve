/*
 * Created on Nov 30, 2003 by Andrew McVeigh
 */
package com.hopstepjump.jumble.umldiagrams.notenode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface NoteNodeFacet extends Facet
{
	UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews);
  void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds);
}
