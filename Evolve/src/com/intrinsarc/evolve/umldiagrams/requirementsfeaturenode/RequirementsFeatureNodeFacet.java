package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import java.awt.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

/**
 *
 * (c) Andrew McVeigh 07-Aug-02
 *
 */
public interface RequirementsFeatureNodeFacet extends Facet
{
	public boolean isDisplayOnlyIcon();
	public Shape formShapeForPreview(UBounds bounds);
	public Shape formShapeForBoundaryCalculation(UBounds bounds);
	public UPoint getMiddlePointForPreview(UBounds bounds);	
	public UBounds getContainmentBounds(UBounds newBounds);
}
