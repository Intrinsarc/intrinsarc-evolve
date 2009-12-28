package com.hopstepjump.idraw.foundation;

import javax.swing.*;

import com.hopstepjump.gem.*;

public interface GlobalPopupMenuFacet extends Facet
{
	public void addToContextMenu(JPopupMenu popupMenu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator, FigureFacet figure);
}
