package com.intrinsarc.evolve.gui;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.evolve.deltaview.*;
import com.intrinsarc.evolve.errorchecking.*;
import com.intrinsarc.evolve.packageview.actions.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

/**
 *
 * (c) Andrew McVeigh 15-Mar-03
 *
 */
public class ApplicationWindowCoordinatorGem
{
	private ToolCoordinatorGem toolCoordinator;
	private ApplicationWindowCoordinatorFacet applicationWindowCoordinatorFacet = new ApplicationWindowCoordinatorFacetImpl();
  private Set<ApplicationWindow> windows = new HashSet<ApplicationWindow>();
  private ExtendedAdornerFacet errorAdorner;
	private String title;
  private ErrorRegister errors;
  private DeltaAdornerFacet deltaAdorner;
  private ConsoleLogger logger;
	
  public ApplicationWindowCoordinatorGem(String title)
	{
		this.title = title;
	}
	
  public void setUp(
      ToolCoordinatorGem toolCoordinator, ErrorRegister errors, boolean logToFile)
  {
  	this.toolCoordinator = toolCoordinator;
    this.errors = errors;   
  	this.errorAdorner =
  	  new ErrorAdornerGem(toolCoordinator.getToolCoordinatorFacet(), errors).getExtendedAdornerFacet();
  	this.deltaAdorner =
  	  new DeltaAdornerGem(toolCoordinator.getToolCoordinatorFacet()).getDeltaAdornerFacet();
  	
  	if (logToFile)
  	{
	  	logger = new ConsoleLogger(Evolve.EVOLVE_VERSION,
	  			new Runnable()
			  	{
	  				public void run()
	  				{
	  					for (ApplicationWindow window : windows)
	  						window.notifyLoggerModified();
	  				}
			  	});
	  	logger.redirectOutputsToLog();
  	}
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
		 * @see com.intrinsarc.evolve.gui.ApplicationWindowCoordinatorFacet#openNewBrowser()
		 */
		public void openNewApplicationWindow()
		{
			final ApplicationWindow window = new ApplicationWindow(getWindowTitle(), errors, logger);
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
			
			window.setUp(this, toolCoordinator, viewRegistryFacet, errorAdorner, deltaAdorner);
			window.openTopLevel(true, true);
			
			// if we don't have the native title bar, then turn on and off to make sure
			// that the colouring is correct (possibly only under linux)
			if (JFrame.isDefaultLookAndFeelDecorated())
			{
				window.setVisible(false);
	      window.setVisible(true);
			}
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					window.requestFocus();
				}
			});
		}
		
		public void exitApplication()
		{
			for (ApplicationWindow window : new HashSet<ApplicationWindow>(windows))
				window.close();
			if (logger != null)
				logger.close();
			System.exit(0);
		}

    public void switchRepository()
    {
      // handle all the global components here...
      GlobalDiagramRegistry.registry.reset();
      ToolCoordinatorGem.clearDeltaEngine();
      
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