package com.intrinsarc.evolve.umldiagrams.featurenode;

import com.intrinsarc.gem.*;

/**
 *
 * (c) Andrew McVeigh 01-Aug-02
 *
 */
public interface FeatureComparableFacet extends Facet
{
	public int compareTo(FeatureComparableFacet other);
	public double getY();
}
