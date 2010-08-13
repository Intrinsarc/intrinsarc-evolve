package com.intrinsarc.idraw.foundation;

import javax.swing.*;

import com.intrinsarc.gem.*;

public interface GlobalPopupMenuFacet extends Facet
{
	public void addToContextMenu(JPopupMenu popupMenu, DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator, FigureFacet figure);
}
