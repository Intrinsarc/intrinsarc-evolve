package com.hopstepjump.idraw.diagramsupport;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;

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
