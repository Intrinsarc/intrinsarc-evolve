package com.hopstepjump.jumble.umldiagrams.connectorarc;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;

public interface ConnectorStyleFacet extends Facet
{
	/** get the offset for a single point: e.g. CalculatedArcPoints.MAJOR_POINT_START */
	public UDimension getLinkedTextOffset(int point);
	public boolean isLinkedTextSuppressed(int point);
	public boolean isCurved();
}
