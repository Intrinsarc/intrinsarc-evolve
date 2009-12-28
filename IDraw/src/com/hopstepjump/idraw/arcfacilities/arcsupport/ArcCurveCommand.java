/*
 * Created on Dec 27, 2003 by Andrew McVeigh
 */
package com.hopstepjump.idraw.arcfacilities.arcsupport;

import com.hopstepjump.idraw.foundation.*;

/**
 * @author Andrew
 */
public class ArcCurveCommand extends AbstractCommand
{
	private FigureReference curvable;
	private boolean curved;
	private Object memento;

	public ArcCurveCommand(FigureReference curvable, boolean curved,
												 String executeDescription, String unExecuteDescription)
	{
		super(executeDescription, unExecuteDescription);
		this.curvable = curvable;
		this.curved = curved;	
	}

	/*
	 * @see com.hopstepjump.idraw.foundation.Command#execute()
	 */
	public void execute(boolean isTop)
	{
		memento = getCurvable().curve(curved);
	}

	/*
	 * @see com.hopstepjump.idraw.foundation.Command#unExecute()
	 */
	public void unExecute()
	{
		getCurvable().unCurve(memento);
	}

	private CurvableFacet getCurvable()
	{
		return (CurvableFacet) GlobalDiagramRegistry.registry.retrieveFigure(curvable).getDynamicFacet(CurvableFacet.class);
	}
}
