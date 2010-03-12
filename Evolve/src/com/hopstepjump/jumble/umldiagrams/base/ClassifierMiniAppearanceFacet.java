package com.hopstepjump.jumble.umldiagrams.base;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.umldiagrams.classifiernode.*;

import edu.umd.cs.jazz.*;

/**
 *
 * (c) Andrew McVeigh 22-Aug-02
 *
 */

public interface ClassifierMiniAppearanceFacet extends Facet
{
	public boolean haveMiniAppearance();
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
  public SetTextPayload setText(TextableFacet textable, String text, Object listSelection, boolean unsuppress);
	public ToolFigureClassification getToolClassification(
			ClassifierSizes makeActualSizes,
			boolean displayOnlyIcon,
			boolean suppressAttributes,
			boolean suppressOperations,
			boolean suppressContents,
			boolean havePorts,
			UPoint point);
}
