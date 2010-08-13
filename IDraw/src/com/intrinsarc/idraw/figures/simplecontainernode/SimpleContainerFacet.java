package com.intrinsarc.idraw.figures.simplecontainernode;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 27-Jul-02
 *
 */
public interface SimpleContainerFacet extends SimpleDeletedUuidsFacet
{
	public UBounds getMinimumBounds();
	public boolean isEmpty();
	public UBounds getMinimumResizeBounds(ContainerSizeCalculator calculator, int corner, UBounds bounds, UPoint topLeftPoint, boolean fromCentre);
	public SimpleContainerSizeInfo makeCurrentSizeInfo(UBounds bounds);
	
	public FigureFacet getFigureFacet();
}
