package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import javax.swing.*;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figurefacilities.textmanipulationbase.*;
import com.hopstepjump.idraw.foundation.*;

/**
 * @author andrew
 */
public interface LinkedTextFacet extends TextableFacet
{
  public ManipulatorFacet getTextEntryManipulator(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView);
  public JMenuItem getViewLabelMenuItem(ToolCoordinatorFacet coordinator, String labelType);
  public boolean isHidden();
  public String getText();
}
