package com.hopstepjump.jumble.gui;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.deltaview.*;
import com.hopstepjump.jumble.errorchecking.*;
import com.hopstepjump.jumble.packageview.actions.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 15-Mar-03
 *
 */
public class ApplicationWindowCoordinatorGem
{
	private ToolCoordinatorGem toolCoordinator;
	private CommandManagerFacet commandManager;
	private ApplicationWindowCoordinatorFacet applicationWindowCoordinatorFacet = new ApplicationWindowCoordinatorFacetImpl();
  private Set<ApplicationWindow> windows = new HashSet<ApplicationWindow>();
  private ExtendedAdornerFacet errorAdorner;
	private String title;
  private ErrorRegister errors;
  private DeltaAdornerFacet deltaAdorner;

	
  public ApplicationWindowCoordinatorGem(String title)
	{
		this.title = title;
	}
	
  public void setUp(
      ToolCoordinatorGem toolCoordinator, CommandManagerFacet commandManager, ErrorRegister errors)
  {
  	this.toolCoordinator = toolCoordinator;
  	this.commandManager = commandManager;
    this.errors = errors;   
  	this.errorAdorner =
  	  new ErrorAdornerGem(toolCoordinator.getToolCoordinatorFacet(), errors).getExtendedAdornerFacet();
  	this.deltaAdorner =
  	  new DeltaAdornerGem(toolCoordinator.getToolCoordinatorFacet()).getDeltaAdornerFacet();  	
  }
  
  public ApplicationWindowCoordinatorFacet getApplicationWindowCoordinatorFacet()
  {
  	return applicationWindowCoordinatorFacet;
  }
  
  private class ApplicationWindowCoordinatorFacetImpl implements ApplicationWindowCoordinatorFacet
  {  	
		public void closingApplicationWindow(ApplicationWindow window)
		{
			// remove the window from the list
			windows.remove(window);
		}

		public int getNumberOfOpenApplicationWindows()
		{
			return windows.size();
		}

		/**
		 * @see com.hopstepjump.jumble.gui.ApplicationWindowCoordinatorFacet#openNewBrowser()
		 */
		public void openNewApplicationWindow()
		{
			ApplicationWindow window = new ApplicationWindow(getWindowTitle(), errors);
			windows.add(window);
			
			List<DiagramFigureAdornerFacet> adorners = new ArrayList<DiagramFigureAdornerFacet>();
			adorners.add(errorAdorner);
			adorners.add(deltaAdorner);
			
			PackageViewRegistryGem viewRegistryGem = new PackageViewRegistryGem(
			    toolCoordinator.getToolCoordinatorFacet(),
			    adorners,
			    new ArrayList<PackageViewSmartMenuItemMaker>(),
			    window.getDesktop());
			PackageViewRegistryFacet viewRegistryFacet = viewRegistryGem.getPackageDiagramViewRegistryFacet();
			
			window.setUp(this, toolCoordinator, commandManager, viewRegistryFacet, errorAdorner, deltaAdorner);
			window.openTopLevel(true, true);
			
			// if we don't have the native title bar, then turn on and off to make sure
			// that the colouring is correct (possibly only under linux)
			if (JFrame.isDefaultLookAndFeelDecorated())
			{
				window.setVisible(false);
	      window.setVisible(true);
			}
		}
		
		public void exitApplication()
		{
			for (ApplicationWindow window : new HashSet<ApplicationWindow>(windows))
				window.close();
			System.exit(0);
		}

    public void switchRepository(CommandManagerListenerFacet facet)
    {
      // handle all the global components here...
      commandManager.switchListener(facet);
      GlobalDiagramRegistry.registry.reset();
      
      // handle any window specific component here
      for (ApplicationWindow window : windows)
        window.reset(getWindowTitle());
      toolCoordinator.getToolCoordinatorFacet().reestablishCurrentTool();
    }
    
    private String getWindowTitle()
    {      
      String windowTitle = title;
      windowTitle += " (" + GlobalSubjectRepository.repository.getFileName() + ")";
      return windowTitle;
    }

    public void refreshWindowTitles()
    {
      // handle any window specific component here
      for (ApplicationWindow window : windows)
        window.refreshTitle(getWindowTitle());
    }
  }
}
