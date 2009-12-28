package com.hopstepjump.idraw.foundation;

import com.hopstepjump.gem.*;

public interface DiagramPostProcessor extends Facet
{
	public void postProcess(DiagramFacet diagram);

	public void dispose();
}
