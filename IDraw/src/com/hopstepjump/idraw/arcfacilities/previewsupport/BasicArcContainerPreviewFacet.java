package com.hopstepjump.idraw.arcfacilities.previewsupport;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface BasicArcContainerPreviewFacet extends ContainerPreviewFacet
{
  void newPointsHaveBeenSet(ActualArcPoints actualPoints, UPoint originalMiddle);
}
