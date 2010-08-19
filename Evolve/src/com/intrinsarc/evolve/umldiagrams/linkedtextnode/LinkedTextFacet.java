package com.intrinsarc.evolve.umldiagrams.linkedtextnode;

import javax.swing.*;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figurefacilities.textmanipulationbase.*;
import com.intrinsarc.idraw.foundation.*;

/**
 * @author andrew
 */
public interface LinkedTextFacet extends TextableFacet
{
  public ManipulatorFacet getTextEntryManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView);
  public JMenuItem getViewLabelMenuItem(ToolCoordinatorFacet coordinator, String labelType);
  public boolean isHidden();
  public String getText();
  public UPoint getNewPoint(CalculatedArcPoints calculated, UPoint previewPoint, UDimension middleOffset, UDimension extent);
}
