package com.intrinsarc.idraw.foundation;

import com.intrinsarc.gem.*;

public interface DiagramPostProcessor extends Facet
{
	public void postProcess(DiagramFacet diagram);

	public void dispose();
}
