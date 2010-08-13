package com.intrinsarc.evolve.umldiagrams.featurenode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;

/**
 * @author Andrew
 */
public interface FeatureAddFacet extends Facet
{
	public void addFeature(FigureReference reference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location);
}
