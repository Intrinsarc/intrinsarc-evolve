package com.hopstepjump.idraw.arcfacilities.previewsupport;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 07-Aug-02
 *
 */
public interface BasicArcPreviewFacet extends Facet
{

  public void formBetterVirtualPoint(UPoint oldVirtual);
  public void addInternalPoint(UPoint point);

  public void setActualPoints(ActualArcPoints actualPoints);
  public void setLinkablePreviews(AnchorPreviewFacet preview1, AnchorPreviewFacet preview2);
  public void setNode2(AnchorFacet node2);
  public void setNode2Preview(AnchorPreviewFacet preview2);

  public ActualArcPoints getActualPoints();
  public ReferenceCalculatedArcPoints getReferenceCalculatedPoints(DiagramFacet diagram);
  public CalculatedArcPoints getCalculatedPoints();
	public AnchorPreviewFacet getPreview2();

	public PreviewFacet getPreviewFacet();
}
