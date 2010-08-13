package com.intrinsarc.idraw.diagramsupport;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 * This is mainly for the scroller.
 * (c) Andrew McVeigh 08-Aug-02
 *
 */
public interface BasicDiagramViewFacet extends Facet
{
	public UDimension getDimensionsOfView();
	public UBounds getViewPort();
	public void requestFocus();
	
	public DiagramViewFacet getDiagramViewFacet();
}
