package com.intrinsarc.idraw.nodefacilities.previewsupport;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;

/**
 *
 * (c) Andrew McVeigh 06-Aug-02
 *
 */
public interface BasicNodePreviewAppearanceFacet
{
	public void setBounds(UBounds bounds, boolean resizedNotMoved);
	public void restoreOriginalBounds();
	public UDimension getOffsetFromOriginal();
	public UBounds getFullBounds();
	public UBounds getFullBoundsForContainment();

  public ZNode formView(boolean debugOnly);
  public ZNode formAnchorHighlight(boolean showOk);
 	public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, OrientedPoint oriented,  boolean linkFromContained, UPoint boxPoint, UPoint insidePoint, boolean linkStart);
}
