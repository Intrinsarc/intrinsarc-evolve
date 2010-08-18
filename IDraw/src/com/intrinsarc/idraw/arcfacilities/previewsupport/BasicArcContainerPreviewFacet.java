package com.intrinsarc.idraw.arcfacilities.previewsupport;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 * @author Andrew
 */
public interface BasicArcContainerPreviewFacet extends ContainerPreviewFacet
{
  void newPointsHaveBeenSet(ActualArcPoints actualPoints, UPoint originalMiddle, boolean curved);
}
