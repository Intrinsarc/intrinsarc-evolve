package com.hopstepjump.idraw.diagramsupport;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.*;

/**
 *
 * (c) Andrew McVeigh 17-Sep-02
 *
 */
public interface DiagramViewContextFacet extends Gem
{
  public JPopupMenu makeContextMenu(ToolCoordinatorFacet coordinator);
  public SmartMenuContributorFacet getSmartMenuContributorFacet();
  public void middleButtonPressed();
  public void refreshViewAttributes();
}
