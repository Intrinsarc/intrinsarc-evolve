/*
 * Created on Jan 2, 2004 by Andrew McVeigh
 */
package com.hopstepjump.jumble.freeform.grouper;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public class LocateTextCommand extends AbstractCommand
{
	private FigureReference locatable;
	private boolean atTop;
	private Object memento;

	public LocateTextCommand(FigureReference locatable, boolean atTop, String executeDescription, String unExecuteDescription)
	{
		super(executeDescription, unExecuteDescription);
		this.locatable = locatable;
		this.atTop = atTop;
	}

	public void execute(boolean isTop)
	{
		memento = getLocatable().locateText(atTop);
	}

	public void unExecute()
	{
	}

	private LocateTextFacet getLocatable()
	{
		return (LocateTextFacet)
			GlobalDiagramRegistry.registry.retrieveFigure(locatable).getDynamicFacet(LocateTextFacet.class);
	}
}