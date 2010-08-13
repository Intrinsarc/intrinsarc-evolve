package com.intrinsarc.evolve.umldiagrams.featurenode;

import com.intrinsarc.gem.*;

/**
 *
 * (c) Andrew McVeigh 07-Aug-02
 *
 */

public interface FeaturePreviewComparableFacet extends Facet
{
	public void setOriginalSortPosition(double sortPosition);
	public double getSortPosition();
	public double getOriginalSortPosition();
	public void setSortPosition(double sortPosition);
	public int compareTo(FeaturePreviewComparableFacet other);
}
