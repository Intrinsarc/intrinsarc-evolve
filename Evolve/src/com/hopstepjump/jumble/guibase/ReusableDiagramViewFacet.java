package com.hopstepjump.jumble.guibase;

import org.eclipse.uml2.Package;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.packageview.actions.*;

/**
 * Manages a diagram view, and its associated windows
 * (c) Andrew McVeigh 12-Sep-02
 */

public interface ReusableDiagramViewFacet extends Facet
{
	public void setExplicitName(String name);
	
	/** replaced the diagram view with another for the specified diagram.  also attaches as a listener */
	public void viewDiagram(DiagramFacet diagram, UBounds openRegionHint, boolean addToStack);
	public String getId();
	
	/** discard detached as a listener from the diagram -- does not close -- only called by registry */
	public void removeAsDiagramListener();
	
	/** connect to the coordinator */
	public void attachToCoordinator();
	
	public String getType();
	
	/** can get the diagram view that this is using */
	public DiagramViewFacet getCurrentDiagramView();
	public void setFixedPerspective(Package possibleFixedPerspective);
	public Package getFixedPerspective();
	public void resetToMovingPerspective();
	public DiagramStack getDiagramStack();
}
