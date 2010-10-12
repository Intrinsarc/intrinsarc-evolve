package com.intrinsarc.evolve.gui;

import java.util.*;

import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.evolve.deltaview.*;
import com.intrinsarc.evolve.errorchecking.*;
import com.intrinsarc.evolve.packageview.actions.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.lbase.*;
import com.intrinsarc.repository.*;
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
			
			// show license information
			showLicenseDetails();
		}
		
		public void exitApplication()
		{
			for (ApplicationWindow window : new HashSet<ApplicationWindow>(windows))
				window.close();
			if (logger != null)
				logger.flush();
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
      
			// show license information
      showLicenseDetails();
    }
    
    public void refreshWindowTitles()
    {
      // handle any window specific component here
      for (ApplicationWindow window : windows)
        window.refreshTitle(getWindowTitle());
    }
  }
  
  private String getWindowTitle()
  {      
    String windowTitle = title;
    windowTitle += " (" + GlobalSubjectRepository.repository.getFileName() + ")";
    return windowTitle;
  }

  private void showLicenseDetails()
  {
		// if we don't have a valid license, complain and go into gpl mode
		String[] error = new String[1];
		LReal.retrieveLicense(error);
		if (error[0] != null)
		{
			LicenseAction action = new LicenseAction(
					toolCoordinator.getToolCoordinatorFacet(),
					new Runnable()
					{
						public void run()
						{
							try
							{
								String tutorial =
									GlobalPreferences.preferences.getRawVariableValue("EVOLVE") + "/tutorial/car-rental" + XMLSubjectRepositoryGem.UML2_SUFFIX;			

								RepositoryUtility.useXMLRepository(tutorial, false);
								
					      // handle all the global components here...
					      GlobalDiagramRegistry.registry.reset();
					      ToolCoordinatorGem.clearDeltaEngine();
					      
					      // handle any window specific component here
					      for (ApplicationWindow window : windows)
					        window.reset(getWindowTitle());
					      toolCoordinator.getToolCoordinatorFacet().reestablishCurrentTool();
							}
							catch (Exception ex)
							{
								ex.printStackTrace();
							}
						}
					});
			action.actionPerformed(null);
		}
  }
}
