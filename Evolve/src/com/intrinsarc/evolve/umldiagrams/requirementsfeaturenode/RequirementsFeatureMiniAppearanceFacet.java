package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;

import edu.umd.cs.jazz.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */

public interface RequirementsFeatureMiniAppearanceFacet extends Facet
{
	public boolean haveMiniAppearance(boolean displayAsIcon);
	public UDimension getMinimumDisplayOnlyAsIconExtent();
	public ZNode formView(boolean displayAsIcon, UBounds bounds);
	public Shape formShapeForPreview(UBounds bounds);  
  public void addToContextMenu(
      JPopupMenu menu,
      DiagramViewFacet diagramView,
      ToolCoordinatorFacet coordinator);
  public Set<String> getDisplayStyles(boolean displayingOnlyAsIcon, boolean anchorIsTarget);
  
  /** allows the node to delegate the list selection on naming to the appearance */
  public JList formSelectionList(String textSoFar);
  public Object setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress);
	public ToolFigureClassification getToolClassification(
			RequirementsFeatureSizes makeActualSizes,
			boolean displayOnlyIcon,
			UPoint point);
}
