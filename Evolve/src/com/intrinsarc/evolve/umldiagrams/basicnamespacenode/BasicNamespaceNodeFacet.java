package com.intrinsarc.evolve.umldiagrams.basicnamespacenode;

import java.awt.*;

import org.eclipse.uml2.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 06-Aug-02
 *
 */
public interface BasicNamespaceNodeFacet extends Facet
{
	public boolean isDisplayOnlyIcon();
	public UPoint getMiddlePointForPreview(UBounds bounds);

	public UPoint calculateBoundaryPoint(PreviewCacheFacet previews, UBounds bounds, OrientedPoint oriented,  boolean linkFromContained, UPoint boxPoint, UPoint insidePoint);
	public Shape formShapeForPreview(UBounds bounds);

	public UBounds getContainmentBounds(UBounds newBounds);
	public UBounds getBoundsForDiagramZooming();
	public UBounds getBoundsAfterExistingContainablesAlter(PreviewCacheFacet previews);
	public void tellContainedAboutResize(PreviewCacheFacet previews, UBounds bounds);
	public Namespace getSubject();
}
