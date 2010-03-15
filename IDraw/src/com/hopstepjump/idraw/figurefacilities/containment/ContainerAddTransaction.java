package com.hopstepjump.idraw.figurefacilities.containment;

import com.hopstepjump.idraw.foundation.*;


public final class ContainerAddTransaction implements TransactionFacet
{
  public static void add(ContainerFacet container, ContainedFacet[] containables)
  {
		container.getFigureFacet().aboutToAdjust();
		System.out.println("$$ adjusted container " + container.getFigureFacet().getSubject());
    container.addContents(containables);
  }

	public static void add(ContainerFacet container, FigureReference[] containedReferences)
	{
		container.getFigureFacet().aboutToAdjust();
		System.out.println("$$ adjusted container " + container.getFigureFacet().getSubject());
		ContainedFacet[] contained = new ContainedFacet[containedReferences.length];
		int lp = 0;
		for (FigureReference r : containedReferences)
			contained[lp++] = container.getFigureFacet().getDiagram().retrieveFigure(r.getId()).getContainedFacet();
    container.addContents(contained);    
	}
}