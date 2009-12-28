package com.hopstepjump.jumble.packageview.actions;

import java.awt.event.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.packageview.base.*;

public class BrowseElementAction extends AbstractAction
{
  private DiagramViewFacet diagramView;
  
  public BrowseElementAction(ToolCoordinatorFacet coordinator, DiagramViewFacet diagramView)
  {
    super("Browse element");
    this.diagramView = diagramView;
  }

  public void actionPerformed(ActionEvent e)
  {
    // get the primary selected 
    FigureFacet first = diagramView.getSelection().getFirstSelectedFigure();
    openInBrowser(first == null ? null : first.getSubject());
  }

  private void openInBrowser(Object subject)
  {
    // if the subject is null, browse to the package
    if (subject == null)
      subject = diagramView.getDiagram().getLinkedObject();
    
    GlobalPackageViewRegistry.activeBrowserRegistry.openBrowser(subject);
  }
}
