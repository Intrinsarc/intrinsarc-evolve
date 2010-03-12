package com.hopstepjump.jumble.umldiagrams.featurenode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

/**
 * @author Andrew
 */
public interface FeatureAddFacet extends Facet
{
	public void addFeature(FigureReference reference, NodeCreateFacet factory, PersistentProperties properties, Object useSubject, Object relatedSubject, UPoint location);
}
