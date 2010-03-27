package com.hopstepjump.idraw.arcfacilities.creation;

import com.hopstepjump.idraw.arcfacilities.creationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;


public final class ArcCreateFigureTransaction implements TransactionFacet
{
	public static void create(DiagramFacet diagram, Object useSubject, FigureReference reference, ArcCreateFacet factory, ReferenceCalculatedArcPoints referencePoints, PersistentProperties properties)
	{
		// possibly create a subject if we haven't been given one
		if (useSubject == null)
			useSubject = factory.createNewSubject(diagram, referencePoints, properties);
		factory.create(useSubject, diagram, reference.getId(), referencePoints, properties);
	}
}