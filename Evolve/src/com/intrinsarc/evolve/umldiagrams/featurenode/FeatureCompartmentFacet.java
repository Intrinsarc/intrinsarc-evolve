package com.intrinsarc.evolve.umldiagrams.featurenode;

import java.util.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 01-Aug-02
 *
 */
public interface FeatureCompartmentFacet extends SimpleDeletedUuidsFacet
{
	public UDimension getMinimumExtent();
	public FigureFacet getFigureFacet();
	public boolean isEmpty();
	public UDimension formNewDimensions(PreviewCacheFacet previews, List<PreviewFacet> sortedOps, UPoint reference);
	public List<PreviewFacet> getSortedOperations(PreviewCacheFacet previews, ContainedPreviewFacet[] add, ContainedPreviewFacet[] remove, UPoint movePoint);
}
