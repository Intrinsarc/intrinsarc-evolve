package com.intrinsarc.evolve.gui;

import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class ExportGroupedImagesAction extends AbstractAction
{
  private SubjectRepositoryFacet repository;
  private File imageDirectory;
  private ToolCoordinatorFacet coordinator;
  private LongRunningTaskProgressMonitor monitor;
  private int numPkgs;
  private RecentFileList recent;
  private JFrame frame;

  public ExportGroupedImagesAction(
      JFrame frame,
      ToolCoordinatorFacet coordinator,
      LongRunningTaskProgressMonitor monitor,
      RecentFileList recent)
  {
    super("Export grouped images");
    this.coordinator = coordinator;
    repository = GlobalSubjectRepository.repository;
    this.monitor = monitor;
    this.recent = recent;
    this.frame = frame;
  }
    
  public void actionPerformed(ActionEvent e)
  {
    numPkgs = 0;
    
    // start at the top, and extract all diagrams
    final Model model = repository.getTopLevelModel();
    
    // ask for a directory to store the images in
    JFileChooser chooser = new CustomisedFileChooser(); 
    chooser.setCurrentDirectory(recent.getLastVisitedDirectory());
    chooser.setDialogTitle("Select a directory for the images to be saved into");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

    if (chooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION)
      return;
    imageDirectory = chooser.getSelectedFile();
    recent.setLastVisitedDirectory(imageDirectory);
    imageDirectory.mkdirs();
    
    monitor.invokeActivityAndMonitorProgress(new Runnable()
    {
      public void run()
      {
        handlePackage(model);
      }
    });
    monitor.stopActivityAndDisplayPopup(
        ApplicationWindow.INFO_ICON,
        "Diagram export",
        "Diagram export completed for " + numPkgs + " packages",
        ScreenProperties.getUndoPopupColor(),
        3000,
        false);
  }

  private void handlePackage(Package current)
  {
    numPkgs++;
    
    // handle this package diagram
    DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(current.getUuid()), true);
    
    // ask each figure in turn to execute the command
    monitor.displayInterimPopup(
        ApplicationWindow.INFO_ICON,
        "Diagram export",
        "Exporting for package " + current.getName(),
        ScreenProperties.getUndoPopupColor(),
        -1);
    for (FigureFacet figure : diagram.getFigures())
    {
      figure.produceEffect(coordinator, "exportGroupedImage", new Object[]{imageDirectory});
    }
        
    // handle any child diagrams
    for (Object object : current.undeleted_getChildPackages())
    {
      Package pkg = (Package) object;
      handlePackage(pkg);
    }
  }
};
