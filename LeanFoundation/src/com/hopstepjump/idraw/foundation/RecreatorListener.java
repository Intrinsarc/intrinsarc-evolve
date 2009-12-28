package com.hopstepjump.idraw.foundation;


/**
 *
 * (c) Andrew McVeigh 05-Mar-03
 *
 */
public interface RecreatorListener
{
	public void addedFigure(FigureFacet figure);
	public void addedLink(LinkingFacet link);
	public void addedToContainer(ContainerFacet container);
}

