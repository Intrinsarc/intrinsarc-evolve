package com.intrinsarc.evolve.umldiagrams.featurenode;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.idraw.nodefacilities.creationbase.*;

/**
 * @author Andrew
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class AddFeatureTransaction implements TransactionFacet
{
	public static void add(
			FigureFacet addable,
			FigureReference featureReference,
			NodeCreateFacet factory,
			PersistentProperties properties,
			Object useSubject,
			Object relatedSubject,
			UPoint location)
	{
		addable.getDynamicFacet(FeatureAddFacet.class).addFeature(
				featureReference,
		    factory,
		    properties,
		    useSubject,
		    relatedSubject,
		    location);
	}
}
