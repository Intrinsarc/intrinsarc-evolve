package com.intrinsarc.evolve.guibase;

import java.awt.event.*;

import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 10-Jan-03
 *
 */
public interface KeyInterpreterFacet
{
	public void addAction(KeyActionFacet action);
	public void keyReleased(KeyEvent e, ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView);
}
