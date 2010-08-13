/*
 * Created on Nov 30, 2003 by Andrew McVeigh
 */
package com.intrinsarc.evolve.umldiagrams.notenode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface NoteNodeFacet extends Facet
{
	UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews);
  void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds);
}
