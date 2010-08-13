package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 07-Aug-02
 *
 */
public interface ClassifierNodeFacet extends Facet
{
	public boolean isDisplayOnlyIcon();
	public Shape formShapeForPreview(UBounds bounds);
	public Shape formShapeForBoundaryCalculation(UBounds bounds);
	public UPoint getMiddlePointForPreview(UBounds bounds);
	
	public UBounds getContainmentBounds(UBounds newBounds);
  public UBounds getPortContainmentBounds(PreviewCacheFacet previews);
	public double getContentsHeightOffsetViaPreviews(PreviewCacheFacet previews);
	public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews, ContainedPreviewFacet[] adjusted);
	public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds newBounds);
}
