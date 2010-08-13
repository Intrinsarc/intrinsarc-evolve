package com.intrinsarc.evolve.guibase;

import javax.swing.*;

import org.eclipse.uml2.Package;

import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 09-Jan-03
 *
 */
public interface ReusableDiagramViewContextFacet extends Facet
{
	public boolean isModified(DiagramFacet diagram);
	public String getFrameTitle(DiagramFacet diagram);
  public void setFrameName(DiagramViewFacet diagramView, Package fixedPerspective);

	public void middleButtonPressed(DiagramFacet diagram);
	public void addToContextMenu(JPopupMenu popup, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator);
	public SmartMenuContributorFacet getSmartMenuContributorFacet();
	
	public void haveClosed(ReusableDiagramViewFacet viewFacet);
	public void haveFocus(ReusableDiagramViewFacet viewFacet);
}
