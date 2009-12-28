package com.hopstepjump.jumble.packageview.actions;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.swing.enhanced.*;

public class BrowseElementMenuItem extends UpdatingJMenuItem
{
  private ToolCoordinatorFacet toolCoordinator;
  private DiagramViewFacet diagramView;

  /**
   * @param currentView
   * @param toolCoordinatorFacet
   */
  public BrowseElementMenuItem(DiagramViewFacet diagramView, ToolCoordinatorFacet toolCoordinator)
  {
    super(new BrowseElementAction(toolCoordinator, diagramView));
    this.diagramView = diagramView;
    this.toolCoordinator = toolCoordinator;   
  }
  
  public boolean update()
  {
  	return true;
  }
}
