package com.hopstepjump.jumble.umldiagrams.featurenode;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.idraw.nodefacilities.creationbase.*;

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
