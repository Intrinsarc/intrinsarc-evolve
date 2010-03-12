package com.hopstepjump.jumble.gui;

import java.io.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.emf.common.util.*;

import com.hopstepjump.backbonegenerator.*;
import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.idraw.diagramsupport.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.jumble.gui.lookandfeel.*;
import com.hopstepjump.jumble.html.*;
import com.hopstepjump.jumble.management.*;
import com.hopstepjump.jumble.repositorybrowser.*;
import com.hopstepjump.notifications.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;

/**
 * 
 * (c) Andrew McVeigh 13-Feb-03
 * 
 */
public class Evolve
{
  public static final String LEAST_VERSION = "1.5";
  public static final String BEST_VERSION = "1.6";
	public static final int MAX_UNMODIFIED_UNVIEWED_DIAGRAMS = 5;

	private ToolCoordinatorGem toolManager;
	/** the application window coordinator manages a number of windows */
	private ApplicationWindowCoordinatorGem windowCoordinator;
	private ErrorRegister errors = new ErrorRegister();
	private static Preference OPEN_DIAGRAMS = new Preference("Advanced", "Maximum unmodified or unviewed diagrams",
			new PersistentProperty(5));

	public static void main(String args[])
  {
    registerPreferenceSlots();
    // handle any preferences
    RegisteredGraphicalThemes themes = RegisteredGraphicalThemes.getInstance(); 
    themes.registerPreferenceSlots();
    themes.interpretPreferences();
    BackboneWriter.registerPreferenceSlots();
    HTMLDocumentationGenerator.registerPreferenceSlots();
    PreferenceTypeDirectory.registerPreferenceSlots();
    
    String version = System.getProperty("java.specification.version");
    System.out.println("Evolve running");
    System.out.println("Detected JRE " + (version == null ? "(unknown)" : version));
    RegisteredGraphicalThemes.getInstance().setLookAndFeel();

    // make sure we have the correct version of java
    if (version == null || version.compareTo(LEAST_VERSION) < 0)
    {
      if (version == null)
      	version = "(unknown)";
      
      // complain if we don't have a least version of the runtime
    	Object[] options = {"Run Evolve anyway",
    	                    "Quit"};
    	int n = JOptionPane.showOptionDialog(null,
    	    "You are using version " + version + " of the Java runtime." +
    	    "\nEvolve needs version " + LEAST_VERSION + " or greater." +
    	    "\n\nPlease quit the application and install a newer version.",
    	    "Java version mismatch",
    	    JOptionPane.YES_NO_CANCEL_OPTION,
    	    JOptionPane.ERROR_MESSAGE,
    	    null,
    	    options,
    	    options[1]);

    	if (n == 1)
    		System.exit(0);
    }
    else    
    // complain if we don't have a least version of the runtime
    if (version.compareTo(BEST_VERSION) < 0)
    {
	  	Object[] options = {"Run Evolve anyway",
	  	                    "Quit"};
	  	int n = JOptionPane.showOptionDialog(null,
	  	    "You are using version " + version + " of the Java runtime." +
	  	    "\nEvolve prefers at version " + BEST_VERSION + " or greater.\nIt functions correctly with this version, but the graphical performance is not optimal." +
	  	    "\n\nYou might consider quitting and installing a newer version.",
	  	    "Java version mismatch",
	  	    JOptionPane.YES_NO_CANCEL_OPTION,
	  	    JOptionPane.INFORMATION_MESSAGE,
	  	    null,
	  	    options,
	  	    options[1]);
	
	  	if (n == 1)
	  		System.exit(0);
    }
    
    setUpUUIDGenerator();
    Evolve application = new Evolve();
    application.setUpServices();
    application.setUpGUI();
    application.showGUI();
    
    // register some mbeans
    new GUIManager().register();
    
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;    
  }

	private static void registerPreferenceSlots()
	{
		// add location slots
		GlobalPreferences.preferences.addPreferenceSlot(new Preference("Locations", "Initial XML repository"),
				new PreferenceTypeFile(), "The XML file that will be loaded on startup.");
		GlobalPreferences.preferences.addPreferenceSlot(OPEN_DIAGRAMS, new PreferenceTypeInteger(),
				"The maximum number of unviewed, but unmodified, diagrams that are allowed to be open.");
		GlobalPreferences.preferences.addPreferenceSlot(ViewUpdateWrapperCommand.BACKGROUND_VIEW_UPDATES, new PreferenceTypeBoolean(),
				"Perform any view updates that might be slow in the background.  Gives a faster response time, but is less tested.");
		BrowserInvoker.registerPreferenceSlots();
		RepositoryBrowserGem.registerPreferenceSlots();
	}

	private static void setUpUUIDGenerator()
	{
		// set up the global uuid generator
		GlobalUUIDGenerator.GlOBAL_UUID_GENERATOR = new IUUIDGenerator()
		{
			public String generateUUID(Object object)
			{
				return "" + UUID.randomUUID();
			}
		};
	}

	public Evolve()
	{
		windowCoordinator = new ApplicationWindowCoordinatorGem("Evolve");
	}

	private void setUpGUI()
	{
		windowCoordinator.setUp(toolManager, errors);
	}

	private void showGUI()
	{
		windowCoordinator.getApplicationWindowCoordinatorFacet().openNewApplicationWindow();
	}

	private void setUpServices()
	{
		// make the diagram registry
		int max = GlobalPreferences.preferences.getRawPreference(OPEN_DIAGRAMS).asInteger();
		max = Math.max(Math.min(max, 20), 0);
		BasicDiagramRegistryGem registryGem = new BasicDiagramRegistryGem(max);
		registryGem.connectBasicDiagramRecreatorFacet(new BasicDiagramRecreatorGem().getBasicDiagramRecreatorFacet());
		GlobalDiagramRegistry.registry = registryGem.getDiagramRegistryFacet();

		// start with an empty XML repository
		try
		{
			String initialXMLRepository = GlobalPreferences.preferences.getRawPreference(
					new Preference("Locations", "Initial XML repository")).asString();
			// we need the delta engine for setting up the model
			DeltaEngineCommandWrapper.clearDeltaEngine();
			RepositoryUtility.useXMLRepository((initialXMLRepository != null && new File(initialXMLRepository)
					.exists()) ? initialXMLRepository : null);
			// the delta engine needs to be cleared, as it gets into an odd state
			// during CommonRepositoryFunctions.initializeModel()
			DeltaEngineCommandWrapper.clearDeltaEngine();
		}
		catch (RepositoryOpeningException e)
		{
			// will never happen with a new XML repository
		}

		// make the tool manager and connect it up
		toolManager = new ToolCoordinatorGem();
	}
}
