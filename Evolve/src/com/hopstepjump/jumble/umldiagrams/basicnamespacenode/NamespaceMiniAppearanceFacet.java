package com.hopstepjump.jumble.umldiagrams.basicnamespacenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.umldiagrams.base.*;

import edu.umd.cs.jazz.*;

public interface NamespaceMiniAppearanceFacet extends Facet
{
  public boolean haveMiniAppearance();
  public UDimension getMinimumDisplayOnlyAsIconExtent();
  public ZNode formView(NamespaceDisplayType displayAsIcon, UBounds bounds);
  public Shape formShapeForPreview(UBounds bounds);  
  public void addToContextMenu(
      JPopupMenu menu,
      DiagramViewFacet diagramView,
      ToolCoordinatorFacet coordinator);
  public Set<String> getDisplayStyles(NamespaceDisplayType displayingOnlyAsIcon, boolean anchorIsTarget);
  
  /** allows the node to delegate the list selection on naming to the appearance */
  public JList formSelectionList(String textSoFar);
  public SetTextPayload setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress, Object oldMemento);
  public SetTextPayload unSetText(Object memento);
}
