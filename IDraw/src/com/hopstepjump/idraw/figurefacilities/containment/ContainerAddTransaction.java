package com.hopstepjump.idraw.figurefacilities.containment;

import com.hopstepjump.idraw.foundation.*;

public final class ContainerAddTransaction implements TransactionFacet
{
	public static void add(ContainerFacet container, ContainedFacet[] containables)
	{
		container.addContents(containables);
	}

	public static void add(ContainerFacet container, FigureReference[] containedReferences)
	{
		ContainedFacet[] contained = new ContainedFacet[containedReferences.length];
		int lp = 0;
		for (FigureReference r : containedReferences)
			contained[lp++] = container.getFigureFacet().getDiagram().retrieveFigure(r.getId()).getContainedFacet();
    container.addContents(contained);
    notifyContainers(container);
	}
	
	public static void notifyContainers(ContainerFacet container)
	{
    DiagramFacet diagram = container.getFigureFacet().getDiagram();
		while (container != null)
		{
			diagram.forceAdjust(container.getFigureFacet());
			ContainedFacet cont = container.getFigureFacet().getContainedFacet();
			if (cont != null)
				container = cont.getContainer();
			else
				container = null;
		}		
	}
}