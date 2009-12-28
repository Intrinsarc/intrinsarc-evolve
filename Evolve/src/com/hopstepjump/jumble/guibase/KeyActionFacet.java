package com.hopstepjump.jumble.guibase;

import java.awt.event.*;

import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 10-Jan-03
 *
 */
public interface KeyActionFacet
{
	public boolean wantsKey(KeyEvent event);
	public void actOnKeyRelease(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView);
}
