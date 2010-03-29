package com.hopstepjump.jumble.gui;

/**
 * Copyright A. McVeigh 2002
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;
import org.eclipse.uml2.impl.*;

import com.hopstepjump.backbonegenerator.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.easydock.*;
import com.hopstepjump.easydock.dockingframes.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.deltaview.*;
import com.hopstepjump.jumble.errorchecking.*;
import com.hopstepjump.jumble.gui.lookandfeel.*;
import com.hopstepjump.jumble.html.*;
import com.hopstepjump.jumble.importexport.*;
import com.hopstepjump.jumble.packageview.actions.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.jumble.repositorybrowser.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;
import com.hopstepjump.swing.smartmenus.*;

public class ApplicationWindow extends SmartJFrame
{
	/** the docking framework */
	private IEasyDock desktop;

	// the services
	private ToolCoordinatorGem toolCoordinator;
	private ToolCoordinatorFacet coordinator;
	private TransactionManagerFacet commandManager;
	private ApplicationWindowCoordinatorFacet applicationWindowCoordinator;
	private PackageViewRegistryFacet viewRegistry;
	private String title;
	private SmartMenuContributorFacet smartMenu = new SmartMenuContributorFacetImpl();
	private SmartMenuBarFacet smartMenuBar;
	private List<RepositoryBrowserFacet> browsers = new ArrayList<RepositoryBrowserFacet>();
	private Map<RepositoryBrowserFacet, IEasyDockable> browserDockables = new HashMap<RepositoryBrowserFacet, IEasyDockable>();
	private List<BackboneRunnerFacet> runners = new ArrayList<BackboneRunnerFacet>();
	private RepositoryBrowserRegistryFacet browserRegistry = new RepositoryBrowserRegistryFacetImpl();
	private BackboneGenerationChoice choice;
	private ErrorLocatorFacet errorLocator = new ErrorLocatorFacetImpl();
	private PaletteManagerFacet paletteFacet;
	private RecentFileList recent = PreferenceTypeDirectory.recent;
	private LongRunningTaskProgressMonitor monitor;
	private JFrame frame = this;
	private PopupMakerFacet popup;
	private ExtendedAdornerFacet errorAdorner;
	private DeltaAdornerFacet deltaAdorner;
	private ErrorRegister errors;
	private JMenuBar menuBar;

	// icons
	public static final ImageIcon FULLSCREEN_ICON = IconLoader.loadIcon("fullscreen.png");
	public static final ImageIcon GARBAGE_ICON = IconLoader.loadIcon("garbage.png");
	public static final ImageIcon APPLICATION_ICON = IconLoader.loadIcon("brick.png");
	public static final ImageIcon MAIN_FRAME_ICON = IconLoader.loadIcon("monkey-icon.png");
	public static final ImageIcon UNDO_ICON = IconLoader.loadIcon("arrow_undo.png");
	public static final ImageIcon REDO_ICON = IconLoader.loadIcon("arrow_redo.png");
	public static final ImageIcon SAVE_ICON = IconLoader.loadIcon("disk.png");
	public static final ImageIcon TAG_ICON = IconLoader.loadIcon("tag_purple.png");
	public static final ImageIcon RUN_ICON = IconLoader.loadIcon("control_play.png");
	public static final ImageIcon WORLD_ICON = IconLoader.loadIcon("world.png");
	public static final ImageIcon PREFERENCES_ICON = IconLoader.loadIcon("preferences.png");
	public static final ImageIcon REFRESH_ICON = IconLoader.loadIcon("arrow_refresh.png");
	public static final ImageIcon XML_ICON = IconLoader.loadIcon("xml.png");
	public static final ImageIcon LOCALDB_ICON = IconLoader.loadIcon("database.png");
	public static final ImageIcon REMOTEDB_ICON = IconLoader.loadIcon("database_connect.png");
	public static final ImageIcon BROWSER_REUSABLE_ICON = IconLoader.loadIcon("browser.png");
	public static final ImageIcon BROWSER_NON_REUSABLE_ICON = IconLoader.loadIcon("browser-nonreusable.png");
	public static final ImageIcon HELP_ICON = IconLoader.loadIcon("help.png");
	public static final ImageIcon WARNING_ICON = IconLoader.loadIcon("warning.png");
	public static final ImageIcon ERROR_ICON = IconLoader.loadIcon("error.png");
	public static final ImageIcon BLANK_ICON = IconLoader.loadIcon("blank.png");
	public static final ImageIcon CHECK_ONE_ICON = IconLoader.loadIcon("check-one.png");
	public static final ImageIcon CHECK_ALL_ICON = IconLoader.loadIcon("check-all.png");
	public static final ImageIcon INFO_ICON = IconLoader.loadIcon("information.png");
	public static final ImageIcon DELTA_ICON = IconLoader.loadIcon("delta.png");
	public static final ImageIcon PALETTE_ICON = IconLoader.loadIcon("palette.png");
	public static final ImageIcon IMPORT_ICON = IconLoader.loadIcon("import.png");
	public static final ImageIcon EXPORT_ICON = IconLoader.loadIcon("export.png");	
	public static final ImageIcon FOLDER_ICON = IconLoader.loadIcon("folder.png");
	public static final ImageIcon CANCEL_ICON = IconLoader.loadIcon("icon_cancel.png");
	public static final ImageIcon KEYBOARD_ICON = IconLoader.loadIcon("keyboard.png");
	public static final ImageIcon COG_ICON = IconLoader.loadIcon("cog.png");
	public static final ImageIcon VARIABLES_ICON = IconLoader.loadIcon("variables.png");

	public ApplicationWindow(String title, ErrorRegister errors)
	{
		super(title);
		this.title = title;
		this.errors = errors;

		// instantiate the docking framework
		desktop = new DockingFramesDock(this, true);
		setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

		// allow files to be dropped onto the frame
		new FileDropTarget(this,
				new FileDropTarget.Listener()
				{
					public boolean acceptFile(File file)
					{
						// if this doesn't end an acceptable prefix, complain
						openFile(file.toString(), true);
						return true;
					}
				});
	}

	public IEasyDock getDesktop()
	{
		return desktop;
	}

	public void setUp(
			ApplicationWindowCoordinatorFacet applicationWindowCoordinatorFacet,
			ToolCoordinatorGem toolManagerGem,
			final PackageViewRegistryFacet viewRegistryFacet,
			ExtendedAdornerFacet errorAdorner, DeltaAdornerFacet deltaAdorner)
	{
		this.applicationWindowCoordinator = applicationWindowCoordinatorFacet;
		this.toolCoordinator = toolManagerGem;
		popup = coordinator = toolCoordinator.getToolCoordinatorFacet();
		this.viewRegistry = viewRegistryFacet;
		this.commandManager = toolManagerGem.getToolCoordinatorFacet();
		this.errorAdorner = errorAdorner;
		this.deltaAdorner = deltaAdorner;

		monitor = new LongRunningTaskProgressMonitor(coordinator, this, popup);

		// change the registries over -- have to do this here and in the listener
		GlobalPackageViewRegistry.activeRegistry = viewRegistry;
		GlobalPackageViewRegistry.activeBrowserRegistry = browserRegistry;

		// set the default close operation to do nothing
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setupMenus();

		// set up the palette manager
		final PaletteManagerGem paletteMgr = new PaletteManagerGem();
		paletteMgr.connectToolCoordinatorFacet(coordinator);
		paletteMgr.connectSmartMenuBarFacet(smartMenuBar);
		paletteFacet = paletteMgr.getPaletteManagerFacet();

		// Support exiting application
		addWindowFocusListener(new WindowAdapter()
		{
			@Override
	    public void windowGainedFocus(WindowEvent e)
	    {
	  		GlobalPackageViewRegistry.activeRegistry = viewRegistry;
	  		GlobalPackageViewRegistry.activeBrowserRegistry = browserRegistry;
	  		coordinator.attachToFrame(frame, paletteFacet, desktop);
	  		viewRegistry.reattachToCoordinator();	    	
	    }			
		});
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (applicationWindowCoordinator.getNumberOfOpenApplicationWindows() == 1)
					askAboutSaveAndPossiblyExit();
				else
					close();
			}
		});

		// tell the coordinator
		GlobalPackageViewRegistry.activeRegistry = viewRegistry;
		GlobalPackageViewRegistry.activeBrowserRegistry = browserRegistry;
		coordinator.attachToFrame(frame, paletteFacet, desktop);
		viewRegistry.reattachToCoordinator();

		// Set up basic frame
		PreferencesFacet prefs = GlobalPreferences.preferences;
		int x = prefs.getRawPreference(RegisteredGraphicalThemes.INITIAL_X_POS).asInteger();
		int y = prefs.getRawPreference(RegisteredGraphicalThemes.INITIAL_Y_POS).asInteger();
		int width = prefs.getRawPreference(RegisteredGraphicalThemes.INITIAL_WIDTH).asInteger();
		int height = prefs.getRawPreference(RegisteredGraphicalThemes.INITIAL_HEIGHT).asInteger();
		setBounds(x, y, width, height);
		setResizable(true);
		setIconImage(MAIN_FRAME_ICON.getImage());

		// a panel to hold the desktop and palette
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// make the palette
		JComponent palette = paletteFacet.getPaletteComponent();

		int pwidth = prefs.getRawPreference(
				new Preference("Appearance", "Palette width")).asInteger();
		pwidth = Math.max(50, Math.min(pwidth, 300));

		palette.setPreferredSize(new Dimension(1000, 100));
		desktop.createEmbeddedPaletteDockable(
				"Palette",
				PALETTE_ICON,
				EasyDockSideEnum.WEST,
				new Dimension(pwidth, 100),
				false,
				false,
				palette);

		// set the title
		setTitle(title);		

		int fSize = menuBar.getFont().getSize();
		setFullScreenScrollDown(fSize * 2 + 30);		
		setVisible(true);
		desktop.finishSetup();
	}

	private class ErrorLocatorFacetImpl implements ErrorLocatorFacet
	{
		public void locateErrorInBrowser(NamedElement elementInError)
		{
			GlobalPackageViewRegistry.activeBrowserRegistry.openBrowser(elementInError);
		}

		public void locateErrorInDiagram(NamedElement elementInError)
		{
			// look for the package, and open the diagram
			Package owner = (Package) GlobalSubjectRepository.repository.findOwningElement(elementInError, PackageImpl.class);

			// if this isn't null, start up the diagram, and type to select the
			// elements
			if (owner != null)
			{
				GlobalPackageViewRegistry.activeRegistry.open(owner, true, false, null, null, true);
				DiagramViewFacet view = coordinator.getCurrentDiagramView();
				SelectionFacet selection = view.getSelection();
				selection.clearAllSelection();
				for (FigureFacet figure : view.getDiagram().getFigures())
				{
					if (figure.getSubject() == elementInError)
						selection.addToSelection(figure);
				}
			}
		}
	}

	private class PackageDiagramOpenerFacetImpl implements PackageDiagramOpenerFacet
	{
		public void openDiagramForPackage(Package pkg)
		{
			GlobalPackageViewRegistry.activeRegistry.open(pkg, true, false, null, null, true);
		}
	}

	private RepositoryBrowserFacet openModelBrowser(SubjectRepositoryFacet repository)
	{
		// make the browser component
		RepositoryBrowserGem browserGem = new RepositoryBrowserGem(coordinator,
				repository, new PackageDiagramOpenerFacetImpl(), errors);
		final RepositoryBrowserFacet browserFacet = browserGem.getRepositoryBrowserFacet();

		// add a browser dockable
		JComponent internalComponent = browserFacet.getVisualComponent();

		int x = coordinator.getFrameXPreference(RegisteredGraphicalThemes.INITIAL_BROWSER_X_POS);
		int y = coordinator.getFrameYPreference(RegisteredGraphicalThemes.INITIAL_BROWSER_Y_POS);
		int width = coordinator.getIntegerPreference(RegisteredGraphicalThemes.INITIAL_BROWSER_WIDTH);
		int height = coordinator.getIntegerPreference(RegisteredGraphicalThemes.INITIAL_BROWSER_HEIGHT);

		final IEasyDockable dockable = desktop.createExternalPaletteDockable(
				"Browser", BROWSER_REUSABLE_ICON, new Point(x, y), new Dimension(width, height), true,
				true, internalComponent);

		dockable.addListener(new IEasyDockableListener()
		{
			public void hasClosed()
			{
				browserFacet.haveClosed();
				browsers.remove(browserFacet);
				browserDockables.remove(browserFacet);
			}

			public void hasFocus()
			{
			}			
		});

		browserGem.connectRepositoryBrowserListenerFacet(new RepositoryBrowserListenerFacet()
				{
					public void requestClose()
					{
						// close the browser, and remove it from the list
						dockable.close();
						browserFacet.haveClosed();
					}

					public void haveSetReusable(boolean reusable)
					{
						if (reusable)
						{
							enforceMaximumOfOneReusableBrowser(browserFacet);
							dockable.setTitleIcon(BROWSER_REUSABLE_ICON);
						} else
							dockable.setTitleIcon(BROWSER_NON_REUSABLE_ICON);
					}

					public void outerSelectionChanged(Element element)
					{
						if (element == null)
							dockable.setTitleText("");
						else
							dockable.setTitleText(GlobalSubjectRepository.repository
									.getFullyQualifiedName(element, DEObject.SEPARATOR));
					}
				});
		browserGem.getRepositoryBrowserFacet().selectFirstElements();

		browserFacet.setTreeDivider(0.5);
		browserFacet.setDetailDivider(0.30);

		browsers.add(browserFacet);
		browserDockables.put(browserFacet, dockable);
		enforceMaximumOfOneReusableBrowser(browserFacet);
		return browserFacet;
	}

	private void enforceMaximumOfOneReusableBrowser(
			RepositoryBrowserFacet browserFacet)
	{
		// only allow the browserFacet to be reusable
		for (RepositoryBrowserFacet browser : browsers)
		{
			if (browser != browserFacet && browser.isReusable())
				browser.forceReusable(false);
		}
	}

	public void close()
	{
		// tell all the browsers about the close so they can disconnect themselves
		// from the repository
		for (RepositoryBrowserFacet browser : browsers)
			browser.haveClosed();

		// tell all the runners to close so we terminate any active processes
		for (BackboneRunnerFacet runner : runners)
			runner.haveClosed();

		// tell the coordinator, and close the
		applicationWindowCoordinator.closingApplicationWindow(ApplicationWindow.this);
		viewRegistry.aboutToClose();
		dispose();
	}

	private void setupMenus()
	{
		menuBar = new JMenuBar();
		menuBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY.brighter()));
		JMenu fakeMenu = new JMenu("File");
		fakeMenu.setEnabled(false);
		menuBar.add(fakeMenu);
		SmartMenuBarGem smartMenuBarGem = new SmartMenuBarGem(menuBar);
		smartMenuBar = smartMenuBarGem.getSmartMenuBarFacet();
		smartMenuBar.addMenuOrderingHint("File", "a");
		smartMenuBar.addMenuOrderingHint("Edit", "b");
		smartMenuBar.addMenuOrderingHint("Windows", "z");
		smartMenuBar.addSmartMenuContributorFacet("ApplicationWindow", smartMenu);
		setJMenuBar(menuBar);
	}

	class ExportPreferencesAction extends AbstractAction
	{
		public ExportPreferencesAction()
		{
			super("Export preferences...");
		}

		public void actionPerformed(ActionEvent e)
		{
			int chosen = askAboutSave("Export preferences...", false);
			if (chosen == JOptionPane.CANCEL_OPTION)
				return;

			// let the user choose the filename
			String fileName = RepositoryUtility.chooseFileNameToCreate(frame,
					"Select file to export to preferences to",
					"Evolve preferences", "evolveprefs", recent.getLastVisitedDirectory());
			if (fileName == null)
				return;

			recent.setLastVisitedDirectory(new File(fileName));
			try
			{
				PreferencesFacet modelPrefs = GlobalPreferences.preferences;
				modelPrefs.exportTo(new File(fileName));
				popup.displayPopup(SAVE_ICON, "Preferences",
						"Preferences exported", ScreenProperties.getUndoPopupColor(),
						Color.black, 1000);
			} catch (Exception ex)
			{
				popup.displayPopup(ERROR_ICON, "Problem exporting preferences",
						ex.getMessage(), ScreenProperties.getUndoPopupColor(), Color.black,
						3000);
			}
		}
	}

	class ImportPreferencesAction extends AbstractAction
	{
		public ImportPreferencesAction()
		{
			super("Import preferences...");
		}

		public void actionPerformed(ActionEvent e)
		{
			int chosen = askAboutSave("Import preferences...", false);
			if (chosen == JOptionPane.CANCEL_OPTION)
				return;

			// let the user choose the filename
			String fileName = RepositoryUtility.chooseFileNameToOpen(frame,
					"Select model preferences file to load",
					"Evolve preferences", "evolveprefs", recent
							.getLastVisitedDirectory());
			if (fileName == null)
				return;

			recent.setLastVisitedDirectory(new File(fileName));
			try
			{
				GlobalPreferences.importPreferences(fileName);
				popup.displayPopup(SAVE_ICON, "Preferences",
						"Preferences imported", ScreenProperties.getUndoPopupColor(),
						Color.black, 1000);
			} catch (Exception ex)
			{
				popup.displayPopup(ERROR_ICON, "Problem importing preferences",
						ex.getMessage(), ScreenProperties.getUndoPopupColor(), Color.black,
						3000);
			}
		}
	}

	class GarbageCollectRepositoryAction extends AbstractAction
	{
		private int diagramCount;
		private boolean displayFinalMessage;

		public GarbageCollectRepositoryAction(boolean displayFinalMessage)
		{
			super("Garbage collect repository");
			this.displayFinalMessage = displayFinalMessage;
		}

		public void actionPerformed(ActionEvent e)
		{
			// save the project, and then refresh all diagrams in the repository
			if (askAboutSave("Save before collection...", false) != JOptionPane.OK_OPTION)
			{
				popup.displayPopup(
						GARBAGE_ICON,
						"Garbage collection not attempted",
						"Model must be saved before collection",
						ScreenProperties.getUndoPopupColor(),
						Color.black,
						3000);
				return;
			}

			monitor.displayInterimPopup(GARBAGE_ICON,
					"Garbage collection in progress...", "Started collecting.",
					ScreenProperties.getUndoPopupColor(), -1);

			// use an app modal dialog to show the status and prevent further input
			monitor.invokeActivityAndMonitorProgress(new Runnable()
			{
				public void run()
				{
					collect(monitor);
				}
			});
		}

		public void collect(final LongRunningTaskProgressMonitor monitor)
		{
			// fix up each diagram in turn
			SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;
			diagramCount = 0;
			fixDiagrams(monitor, repository.getTopLevelModel());

			final int collected = GlobalSubjectRepository.repository.collectGarbage(
					new GarbageUpdaterFacet()
					{
						public void update(String update)
						{
							monitor.displayInterimPopup(GARBAGE_ICON,
									"Garbage collection in progress...", update,
									ScreenProperties.getUndoPopupColor(), -1);
						}						
					});
			GlobalSubjectRepository.repository.save(frame, true, getLastVisitedDirectory());
			GlobalDiagramRegistry.registry.enforceMaxUnmodifiedUnviewedDiagramsLimit();
			commandManager.clearTransactionHistory();

			if (displayFinalMessage)
				monitor.stopActivityAndDisplayPopup(GARBAGE_ICON,
						"Garbage collection successful", "Collected " + collected
								+ " elements; cleared command history", ScreenProperties
								.getUndoPopupColor(), 3000, false);
		}

		private void fixDiagrams(LongRunningTaskProgressMonitor monitor,
				Package current)
		{
			DiagramRegistryFacet registry = GlobalDiagramRegistry.registry;
			SubjectRepositoryFacet repository = GlobalSubjectRepository.repository;

			// handle this package diagram
			try
			{
				final DiagramFacet diagram = registry.retrieveOrMakeDiagram(
						new DiagramReference(current.getUuid()), false);
				popup.displayPopup(GARBAGE_ICON, "Garbage collection in progress...",
						"Diagram updated for package "
								+ ((Package) diagram.getLinkedObject()).getName(),
						ScreenProperties.getUndoPopupColor(), Color.black, -1);
				if (diagram.isModified())
				{
					diagramCount++;
					if ((diagramCount % 10) == 0)
					{
						SaveInformation save = repository.getSaveInformation();
						if (save.projectNeedsSaving())
						{
							GlobalSubjectRepository.repository.save(frame, true,
									getLastVisitedDirectory());
							registry.enforceMaxUnmodifiedUnviewedDiagramsLimit();

							monitor.displayInterimPopup(GARBAGE_ICON,
									"Garbage collection in progress...", "Saved repository",
									ScreenProperties.getUndoPopupColor(), -1);
						}
					}
				}
			} catch (PersistentFigureRecreatorNotFoundException e)
			{
				e.printStackTrace();
				return;
			}

			// handle any child diagrams
			for (Object object : current.undeleted_getChildPackages())
			{
				Package pkg = (Package) object;
				fixDiagrams(monitor, pkg);
			}
		}
	};

	class RefreshAction extends AbstractAction
	{
		public RefreshAction()
		{
			super("Refresh from repository");
		}

		public void actionPerformed(ActionEvent e)
		{
			GlobalSubjectRepository.repository.refreshAll();
			GlobalDiagramRegistry.registry.refreshAllDiagrams();
			coordinator.clearTransactionHistory();
			popup.displayPopup(REFRESH_ICON, "Refresh",
					"Refreshed from database; cleared undo/redo history", ScreenProperties
							.getUndoPopupColor(), Color.black, 1500, true, commandManager
							.getTransactionPosition(), commandManager.getTotalTransactions());
		}
	};

	class ExitAction extends AbstractAction
	{
		public ExitAction()
		{
			super("Exit");
		}

		public void actionPerformed(ActionEvent e)
		{
			askAboutSaveAndPossiblyExit();
		}
	};

	class OpenNewTabAction extends AbstractAction
	{
		public OpenNewTabAction()
		{
			super("Open new tab");
		}

		public void actionPerformed(ActionEvent e)
		{
			viewRegistry.open(
					GlobalSubjectRepository.repository.getTopLevelModel(),
					true,
					true,
					null,
					null,
					true);
		}
	};

	class AnalyseProtocolAction extends AbstractAction
	{
		public AnalyseProtocolAction()
		{
			super("Analyse protocol");
		}

		public void actionPerformed(ActionEvent e)
		{
//			BackboneGenerationChoice choice = new BackboneGenerationChoice(coordinator);
//			try
//			{
//				choice.adjustSelectionForProtocolAnalysis();
//			}
//			catch (BackboneGenerationException ex)
//			{
//				coordinator.invokeErrorDialog("Protocol analysis problem...", ex.getMessage());
//				return;
//			}
//			DEComponent comp = choice.getSingleComponent();
//			
//			try
//			{
//				String fsp = new ProtocolToFSPTranslator(
//						comp.getHomeStratum()).produceFSPForProtocol(
//								new BBGlobalProtocolContext(),
//								"top",
//								comp);
//				System.out.println("$$ fsp = " + fsp);
//			}
//			catch (BackboneFSPTranslationException e1)
//			{
//				e1.printStackTrace();
//			}
		}
	}

	class GenerateHTMLAction extends AbstractAction
	{
		public GenerateHTMLAction()
		{
			super("Generate HTML documentation");
		}

		public void actionPerformed(ActionEvent e)
		{
			final HTMLDocumentationGenerator generator = new HTMLDocumentationGenerator(
					coordinator);

			monitor.invokeActivityAndMonitorProgress(new Runnable()
			{
				public void run()
				{
					try
					{
						generator.generateHTML(popup, MAIN_FRAME_ICON);
					} catch (HTMLGenerationException ex)
					{
						monitor.stopActivityAndDisplayDialog(
								"HTML generation problem...",
								ex.getMessage(),
								true);
					} catch (final PreferenceNotFoundException ex)
					{
						monitor.stopActivityAndDisplayDialog(
								"HTML generation problem...",
								ex.getMessage(),
								true);
					}
				}
			});
		}
	}

	class TagBackboneAction extends AbstractAction
	{
		public TagBackboneAction()
		{
			super("Tag Backbone stratum");
		}

		public void actionPerformed(ActionEvent e)
		{
			ToolCoordinatorFacet toolFacet = coordinator;

			// save the selection
			choice = new BackboneGenerationChoice(toolFacet);

			// ensure we have a single stratum
			try
			{
				choice.adjustSelectionForSingleStratum();
			}
			catch (BackboneGenerationException ex)
			{
				toolFacet.invokeErrorDialog("Backbone tagging problem...", ex.getMessage());
				choice = null;
				return;
			}

			// if we got here we were successful, so show a nice popup
			popup.displayPopup(TAG_ICON, "Backbone tagging",
					"Stratum tagged successfully", null, null, 1200);
		}
	}

	class GenerateMixedAction extends AbstractAction
	{
		public GenerateMixedAction()
		{
			super("Generate Backbone");
		}

		public void actionPerformed(ActionEvent e)
		{
			monitor.invokeActivityAndMonitorProgress(new Runnable()
			{
				public void run()
				{
					try
					{
						int modified[] = {0};
						generateBackbone(choice, null, null, true, true, modified);
					}
					catch (BackboneGenerationException ex)
					{
						monitor.stopActivityAndDisplayDialog("Generation problem...", ex.getMessage(), true);
					}
				}
			});
		}
	}

	class GenerateFullImplementationAction extends AbstractAction
	{
		public GenerateFullImplementationAction()
		{
			super("Generate full implementation");
		}

		public void actionPerformed(ActionEvent e)
		{
			monitor.invokeActivityAndMonitorProgress(new Runnable()
			{
				public void run()
				{
					try
					{
						int modified[] = {0};
						generateBackbone(choice, null, null, true, false, modified);
					}
					catch (BackboneGenerationException ex)
					{
						monitor.stopActivityAndDisplayDialog("Generation problem...", ex.getMessage(), true);
					}
				}
			});
		}
	}

	class RunBackboneAction extends AbstractAction
	{
		public RunBackboneAction()
		{
			super("Run Backbone");
		}

		public void actionPerformed(ActionEvent e)
		{
			ToolCoordinatorFacet toolFacet = coordinator;
			// complain if nothing has been tagged
			if (choice == null)
			{
				toolFacet.invokeErrorDialog("Backbone running problem...", "No elements have been tagged");
				return;
			}

			makeBackboneRunnerWindow("Runner", choice, false);
		}
	};

	private void makeBackboneRunnerWindow(String name,
			BackboneGenerationChoice choice, boolean analyseProtocol)
	{
		// add an internal frame for running
		BackboneRunner runner = new BackboneRunner(name, choice, analyseProtocol);
		runner.setBackboneGeneratorFacet(new BackboneGeneratorFacet()
		{
			public File generate(BackboneGenerationChoice choice,
					List<String> classpaths, List<String> untranslatedClasspaths, boolean mixed, int modified[])
					throws BackboneGenerationException
			{
				return generateBackbone(choice, classpaths, untranslatedClasspaths, false, mixed, modified);
			}
		});
		runner.connectErrorLocatorFacet(errorLocator);
		final BackboneRunnerFacet runnerFacet = runner.getBackboneRunnerFacet();

		JComponent internalComponent = runnerFacet.getVisualComponent();
		int x = GlobalPreferences.preferences.getRawPreference(
				RegisteredGraphicalThemes.INITIAL_RUNNER_X_POS).asInteger()
				+ frame.getLocationOnScreen().x;
		int y = GlobalPreferences.preferences.getRawPreference(
				RegisteredGraphicalThemes.INITIAL_RUNNER_Y_POS).asInteger()
				+ frame.getLocationOnScreen().y;

		int width = GlobalPreferences.preferences.getRawPreference(RegisteredGraphicalThemes.INITIAL_RUNNER_WIDTH).asInteger();
		int height = GlobalPreferences.preferences.getRawPreference(RegisteredGraphicalThemes.INITIAL_RUNNER_HEIGHT).asInteger();

		IEasyDockable dockable = desktop.createExternalPaletteDockable(
				"Backbone runner", APPLICATION_ICON, new Point(x, y), new Dimension(width, height), true,
				true, internalComponent);
		runner.setDockable(desktop, dockable);

		dockable.addListener(new IEasyDockableListener()
		{
			public void hasClosed()
			{
					runnerFacet.haveClosed();
					runners.remove(runnerFacet);
			}

			public void hasFocus()
			{
			}
		});

		runners.add(runnerFacet);

		// tell the runner to start
		monitor.invokeActivityAndMonitorProgress(new Runnable()
		{
			public void run()
			{
				runnerFacet.run();
			}
		});
	}
	
	private boolean prepareForGeneration(
			BackboneGenerationChoice choice,
			boolean showPopups) throws BackboneGenerationException
	{
		// complain if nothing has been tagged
		if (choice == null)
		{
			monitor.stopActivityAndDisplayDialog("Generation problem...", "No elements have been tagged", true);
			return false;
		}

		// save the selection
		List<DEStratum> strata;
		
		// display a popup at the start
		if (showPopups)
			monitor.displayInterimPopup(SAVE_ICON, "Generation", "<html>" + "Checking model...", null, -1);

		try
		{
			ToolCoordinatorGem.clearDeltaEngine();
			strata = choice.extractRelatedStrata();
			if (strata.get(strata.size() - 1) != GlobalDeltaEngine.engine.getRoot())
			{
				DEStratum root = GlobalDeltaEngine.engine.forceArtificialParent(new HashSet<DEStratum>(strata));
				strata.add(root);
			}

			// perform an error check without the diagrams
			// clear any current errors and recheck
			final StratumErrorDetector detector = new EnhancedPackageErrorDetector(errors);
			errors.clear();
			errorAdorner.showErrors();
			
			// check only at the top level strata
			detector.checkAllInOrder(strata, -1, true, null);

			int count = errors.countErrors();
			if (count != 0)
			{
				if (showPopups)
					monitor.displayInterimPopup(SAVE_ICON, "Generation", "Found errors; doing deep checking", null, -1);

				// check all at home
				errors.clear();
				detector.checkAllAtHome(strata, 0, strata.size() - 2, null);
				// check at the top level
				detector.checkAllInOrder(strata, -1, true, null);

				// resync diagrams to adorn with the newly discovered errors
				resyncDiagramsAndBrowser();
				if (showPopups)
				{
					monitor.stopActivityAndDisplayDialog("Generation problem...", "Found " + count + " errors in the strata subset", true);
				}
				else
				{
					throw new BackboneGenerationException(
							"Found " + count + " errors in the strata subset",
							strata.size() != 0 ? strata.get(0).getRepositoryObject() : null);
				}
				return false;
			}
			else
			{
				ToolCoordinatorGem.clearDeltaEngine();
			}
		}
		catch (BackboneGenerationException ex)
		{
			if (showPopups)
				monitor.stopActivityAndDisplayDialog("Generation problem...", ex.getMessage(), true);
			else
				throw ex;
			return false;
		}
		return true;
	}

	private File generateBackbone(
			BackboneGenerationChoice choice,
			List<String> classpaths,
			List<String> untranslatedClasspaths,
			boolean showPopups,
			boolean mixed,
			int modified[]) throws BackboneGenerationException
	{
		long start = System.currentTimeMillis();
		if (prepareForGeneration(choice, showPopups))
		{
			if (showPopups)
				monitor.displayInterimPopup(SAVE_ICON, "Generation", "<html>" + "Generating now...", null, -1);
			BackboneGenerator generator = new BackboneGenerator();
			File translated =
				generator.getBackboneGeneratorFacet().generate(choice, classpaths, untranslatedClasspaths, mixed, modified);
			long end = System.currentTimeMillis();
	
			// if we got here we were successful, so show a nice popup
			String gen = mixed ? "Backbone" : "Full implementation";
			
			int count = modified[0];
			String mods = "<div color='red'><b>Modified " + modified[0] + " file" + (count > 1 ? "s" : "") + "</b></div>";
			if (count == 0)
				mods = "No files modified";
			if (showPopups)
				monitor.displayInterimPopup(SAVE_ICON, gen + " generation", "<html>"
						+ "Generation completed successfully" + " in " + (end - start) + "ms<br>" + mods + "</html>",
						null, 2000);
	
			// resync to pick up cleared errors
			resyncDiagramsAndBrowser();
			return translated;
		}
		return null;
	}

	class OpenModelBrowserAction extends AbstractAction
	{
		public OpenModelBrowserAction()
		{
			super("Open browser");
		}

		public void actionPerformed(ActionEvent e)
		{
			openModelBrowser(GlobalSubjectRepository.repository);
		}
	};

	class RepositoryBrowserRegistryFacetImpl implements
			RepositoryBrowserRegistryFacet
	{
		public void openBrowser(Object subject)
		{
			// find a possible reusable browser
			RepositoryBrowserFacet reusable = null;
			for (RepositoryBrowserFacet browser : browsers)
				if (browser.isReusable())
				{
					reusable = browser;
					IEasyDockable dockable = browserDockables.get(browser);
					dockable.restore();
					break;
				}

			// if we haven't found a reusable browser, make a new internal one
			if (reusable == null)
				reusable = openModelBrowser(GlobalSubjectRepository.repository);
			reusable.browseTo(subject);
		}
	}

	class UndoAction extends AbstractAction
	{
		public UndoAction()
		{
			super("Undo");
		}

		public void actionPerformed(ActionEvent e)
		{
			if (commandManager.getTransactionPosition() > 0)
			{
				String undoName = commandManager.getUndoTransactionDescription();
				
				// display a small popup for 2 seconds
				popup.displayPopup(UNDO_ICON, "Undo", undoName,
						ScreenProperties.getUndoPopupColor(), Color.black, 1500, true,
						commandManager.getTransactionPosition() - 1,
						commandManager.getTotalTransactions());

				toolCoordinator.getToolCoordinatorFacet().undoTransaction();
				paletteFacet.refreshEnabled();
			}
		}
	}

	class RedoAction extends AbstractAction
	{
		public RedoAction()
		{
			super("Redo");
		}

		public void actionPerformed(ActionEvent e)
		{
			if (commandManager.getTransactionPosition() < commandManager.getTotalTransactions())
			{
				String redoName = commandManager.getRedoTransactionDescription();
				
				// display a small popup for 2 seconds
				popup.displayPopup(REDO_ICON, "Redo", redoName,
						ScreenProperties.getUndoPopupColor(), Color.black, 1500, true,
						commandManager.getTransactionPosition() + 1,
						commandManager.getTotalTransactions());

				toolCoordinator.getToolCoordinatorFacet().redoTransaction();
				paletteFacet.refreshEnabled();
			}
		}
	}

	class CreateNewXMLRepositoryAction extends AbstractAction
	{
		public CreateNewXMLRepositoryAction()
		{
			super("Create new XML repository");
		}

		public void actionPerformed(ActionEvent e)
		{
			int chosen = askAboutSave("Create new XML repository...", false);
			if (chosen == JOptionPane.CANCEL_OPTION)
				return;
			try
			{
				RepositoryUtility.useXMLRepository(null);
				applicationWindowCoordinator.switchRepository();
			}
			catch (RepositoryOpeningException ex)
			{
				// will never happen with an empty standard XML repository
			}
		}
	};

	class OpenExistingXMLRepositoryAction extends AbstractAction
	{
		public OpenExistingXMLRepositoryAction()
		{
			super("Existing XML repository");
		}

		public void actionPerformed(ActionEvent e)
		{
			int chosen = askAboutSave("Open existing XML repository...", false);
			if (chosen == JOptionPane.CANCEL_OPTION)
				return;

			// let the user choose the filename
			String fileName = RepositoryUtility.chooseFileNameToOpen(
					frame,
					"Select file to open",
					XMLSubjectRepositoryGem.EXTENSION_DESCRIPTION,
					XMLSubjectRepositoryGem.EXTENSION_TYPES,
					XMLSubjectRepositoryGem.UML2Z_SUFFIX_NO_DOT,
					recent.getLastVisitedDirectory());

			// don't go further if it hasn't been selected
			if (fileName == null)
				return;

			recent.setLastVisitedDirectory(new File(fileName));
			Cursor old = coordinator.displayWaitCursor();
			try
			{
				RepositoryUtility.useXMLRepository(fileName);
				applicationWindowCoordinator.switchRepository();
				recent.addFile(fileName);
			} catch (RepositoryOpeningException ex)
			{
				popup.displayPopup(SAVE_ICON, "Problem opening XML repository", ex
						.getMessage(), ScreenProperties.getUndoPopupColor(), Color.black,
						3000);
				recent.removeFile(fileName);
			} finally
			{
				coordinator.restoreCursor(old);
			}
		}
	};

	class OpenLocalDbRepositoryAction extends AbstractAction
	{
		public OpenLocalDbRepositoryAction()
		{
			super("Local database repository");
		}

		public void actionPerformed(ActionEvent e)
		{
			int chosen = askAboutSave("Open local database repository...", false);
			if (chosen == JOptionPane.CANCEL_OPTION)
				return;

			// let the user choose the filename
			String fileName = RepositoryUtility.chooseFileNameToOpen(frame,
					"Select database file to open",
					ObjectDbSubjectRepositoryGem.UML2DB_SUFFIX_DESCRIPTION,
					ObjectDbSubjectRepositoryGem.UML2DB_SUFFIX_NO_DOT,
					recent.getLastVisitedDirectory());
			if (fileName == null)
				return;

			recent.setLastVisitedDirectory(new File(fileName));
			try
			{
				RepositoryUtility.useObjectDbRepository(null, fileName);
				applicationWindowCoordinator.switchRepository();
				recent.addFile(fileName);
			} catch (RepositoryOpeningException ex)
			{
				popup.displayPopup(SAVE_ICON,
						"Problem opening local database repository", ex.getMessage(),
						ScreenProperties.getUndoPopupColor(), Color.black, 3000);
				recent.removeFile(fileName);
			}
		}
	};

	class OpenRemoteDbRepositoryAction extends AbstractAction
	{
		public OpenRemoteDbRepositoryAction()
		{
			super("Remote database repository");
		}

		public void actionPerformed(ActionEvent e)
		{
			int chosen = askAboutSave("Open remote database repository...", false);
			if (chosen == JOptionPane.CANCEL_OPTION)
				return;

			// let the user choose the filename
			String name = "";
			try
			{
				String[] info = RepositoryUtility.chooseRemoteDatabase();
				if (info == null)
					return;

				RepositoryUtility.useObjectDbRepository(info[0], info[1]);
				applicationWindowCoordinator.switchRepository();
				name = info[0] + ":" + info[1];
				recent.addFile(name);
			} catch (RepositoryOpeningException ex)
			{
				popup.displayPopup(SAVE_ICON,
						"Problem opening remote database repository", ex.getMessage(),
						ScreenProperties.getUndoPopupColor(), Color.black, 3000);
				recent.removeFile(name);
			}
		}
	};

	class SaveAsAction extends AbstractAction
	{
		public SaveAsAction()
		{
			super("Save as...");
		}

		public void actionPerformed(ActionEvent e)
		{
			Cursor current = coordinator.displayWaitCursor();
			try
			{
				String saveAsFileName = GlobalSubjectRepository.repository.saveAs(
						frame, getLastVisitedDirectory());
				if (saveAsFileName != null)
				{
					recent.setLastVisitedDirectory(new File(saveAsFileName));
					recent.addFile(saveAsFileName);
					recent.setLastVisitedDirectory(new File(saveAsFileName));
					GlobalDiagramRegistry.registry
							.enforceMaxUnmodifiedUnviewedDiagramsLimit();
					popup.displayPopup(null, null, new JLabel("Saved as " + GlobalSubjectRepository.repository.getFileName(), SAVE_ICON, JLabel.LEADING),
							ScreenProperties.getUndoPopupColor(), Color.black, 1500);
					// ask the window coordinator to refresh all window titles
					applicationWindowCoordinator.refreshWindowTitles();
				}
			} finally
			{
				coordinator.restoreCursor(current);
			}
		}
	};

	class SaveToAction extends AbstractAction
	{
		public SaveToAction()
		{
			super("Save to...");
		}

		public void actionPerformed(ActionEvent e)
		{
			Cursor current = coordinator.displayWaitCursor();
			try
			{
				String saveToFileName = GlobalSubjectRepository.repository.saveTo(
						frame, null, null, getLastVisitedDirectory());
				if (saveToFileName != null)
				{
					popup.displayPopup(null, null, new JLabel("Saved to " + saveToFileName, SAVE_ICON, JLabel.LEADING),
							ScreenProperties.getUndoPopupColor(),
							Color.black, 1500);
				}
			} finally
			{
				coordinator.restoreCursor(current);
			}
		}
	};

	class SaveAction extends AbstractAction
	{
		public SaveAction()
		{
			super("Save");
		}

		public void actionPerformed(ActionEvent e)
		{
			SaveInformation save = GlobalSubjectRepository.repository
					.getSaveInformation();

			if (save.projectNeedsSaving())
			{
				String text = "";
				if (save.getRepositoryToSave())
				{
					text += "repository";
				}
				int diagrams = save.getDiagramsToSave();
				if (diagrams > 0)
				{
					if (text.length() > 0)
						text += " and ";
					if (diagrams > 1)
						text += "" + diagrams + " diagrams";
					else
						text += "1 diagram";
				}

				Cursor current = coordinator.displayWaitCursor();
				try
				{
					String fileName = GlobalSubjectRepository.repository.save(frame,
							false, getLastVisitedDirectory());
					if (fileName != null)
					{
						if (fileName.length() != 0)
						{
							recent.setLastVisitedDirectory(new File(fileName));
							recent.addFile(fileName);
						}
						GlobalDiagramRegistry.registry
								.enforceMaxUnmodifiedUnviewedDiagramsLimit();
						popup.displayPopup(null, null, new JLabel("Saved " + text, SAVE_ICON, JLabel.LEADING),
								ScreenProperties.getUndoPopupColor(), Color.black, 1500);
					}
				} finally
				{
					coordinator.restoreCursor(current);
				}
			} else
			{
				popup.displayPopup(null, null, new JLabel("No save required", SAVE_ICON, JLabel.LEADING),
						ScreenProperties.getUndoPopupColor(), Color.black, 500);
			}

			// ask the window coordinator to refresh all window titles
			applicationWindowCoordinator.refreshWindowTitles();
		}
	};

	class OpenNewWindowAction extends AbstractAction
	{
		public OpenNewWindowAction()
		{
			super("Open new window");
		}

		public void actionPerformed(ActionEvent e)
		{
			applicationWindowCoordinator.openNewApplicationWindow();
		}
	};

	class FullScreenAction extends AbstractAction
	{
		public FullScreenAction()
		{
			super("Toggle full screen");
		}

		public void actionPerformed(ActionEvent e)
		{
			toggleFullScreen();
		}				
	};

	class OpenClipboardDiagramAction extends AbstractAction
	{
		public OpenClipboardDiagramAction()
		{
			super("Open clipboard diagram");
		}

		public void actionPerformed(ActionEvent e)
		{
			List<DiagramFigureAdornerFacet> adorners = new ArrayList<DiagramFigureAdornerFacet>();
			adorners.add(deltaAdorner);
			adorners.add(errorAdorner);
			viewRegistry.openClipboard(PackageViewRegistryGem.CLIPBOARD_TYPE, false, true, adorners, null);
		}
	};

	private class ToggleDeltaViewAction extends AbstractAction
	{
		public ToggleDeltaViewAction()
		{
			super("Toggle delta view");
		}

		public void actionPerformed(ActionEvent e)
		{
			deltaAdorner.toggleEnabled();

			boolean showing = deltaAdorner.isEnabled();
			popup.displayPopup(null, null, showing ? new JLabel(
					"Show delta view", DELTA_ICON, JLabel.LEADING) : new JLabel(
					"Hide delta view", BLANK_ICON, JLabel.LEADING), ScreenProperties
					.getUndoPopupColor(), Color.black, 1000);

			// resync just in case a change was made
			resyncDiagramsAndBrowser();
		}
	}

	private class HideErrorsAction extends AbstractAction
	{
		public HideErrorsAction()
		{
			super("Hide errors");
		}

		public void actionPerformed(ActionEvent e)
		{
			errorAdorner.hideErrors();
			popup.displayPopup(null, null,
					new JLabel("Hide errors", BLANK_ICON, JLabel.LEADING),
					ScreenProperties.getUndoPopupColor(),
					Color.black, 1000);

			// resync just in case a change was made
			resyncDiagramsAndBrowser();
		}
	}

	private void resyncDiagramsAndBrowser()
	{
		// make a new delta engine, just in case we had a previous one (e.g. with a backbone artificial parent)
		ToolCoordinatorGem.clearDeltaEngine();
		GlobalDiagramRegistry.resyncViews();
		for (RepositoryBrowserFacet browser : browsers)
			browser.refresh();
	}

	private class CheckStratumItem extends AbstractAction
	{
		public CheckStratumItem()
		{
			super("Check current stratum");
		}

		public void actionPerformed(ActionEvent e)
		{
			DiagramViewFacet view = coordinator.getCurrentDiagramView();
			Package current = (Package) view.getDiagram().getLinkedObject();
			DEStratum currentPackage = GlobalDeltaEngine.engine.locateObject(current).asStratum();

			// expand out and check everything
			final List<DEStratum> toCheck = currentPackage.determineOrderedPackages(false);

			// clear any current errors and recheck
			final EnhancedPackageErrorDetector detector = new EnhancedPackageErrorDetector(errors);
			errors.clear();
			errorAdorner.showErrors();
			lookForErrors("Checking current stratum for errors...", detector, toCheck, -1, true, true, true);
		}
	}

	private class CheckAllItem extends AbstractAction
	{
		public CheckAllItem()
		{
			super("Check everything");
		}

		public void actionPerformed(ActionEvent e)
		{
			DEStratum currentPackage = GlobalDeltaEngine.engine.locateObject(
					GlobalSubjectRepository.repository.getTopLevelModel()).asStratum();

			// expand out and check everything
			final EnhancedPackageErrorDetector detector = new EnhancedPackageErrorDetector(errors);
			final List<DEStratum> toCheck = currentPackage.determineOrderedPackages(false);

			// clear any current errors
			errors.clear();
			errorAdorner.showErrors();			
			lookForErrors("Checking entire system for errors...", detector, toCheck, 0, false, true, true);
		}
	}

	private void lookForErrors(
			final String message,
			final EnhancedPackageErrorDetector detector,
			final List<DEStratum> toCheck,
			final int startFrom,
			final boolean deepCheck,
			final boolean includeDiagrams,
			final boolean showPopups)
	{
		// use an app modal dialog to show the status and prevent further input
		monitor.invokeActivityAndMonitorProgress(new Runnable()
		{
			private int deltaClear = 0;

			public void run()
			{
				final int totalPermutations = detector.calculatePerspectivePermutations(toCheck, startFrom, deepCheck);
				long start = System.currentTimeMillis();
				final int clearCycle = includeDiagrams ? 10 : 50;
				final int popupCycle = 4;

				detector.checkAllInOrderAllowingDeltaEngineClearing(toCheck, startFrom, deepCheck, includeDiagrams,
						new IDetectorListener()
						{
							private int count = 0;

							public void haveChecked(DEStratum perspective, DEStratum current)
							{
								// report on any status
								if (showPopups && count % popupCycle == 0)
									monitor.displayInterimPopupWithProgress(CHECK_ALL_ICON,
											message, "Checking " + totalPermutations + " permutations",
											ScreenProperties.getUndoPopupColor(), -1, count,
											totalPermutations);

								if (count != 0 && count % clearCycle == 0)
								{
									ToolCoordinatorGem.clearDeltaEngine();
									deltaClear++;
									GlobalDiagramRegistry.registry.enforceMaxUnmodifiedUnviewedDiagramsLimit();
								}

								count++;
							}
						});
				long end = System.currentTimeMillis();

				if (showPopups)
				{
					monitor.stopActivityAndDisplayPopup(CHECK_ALL_ICON,
							"Error checking finished", "Found " + errors.countErrors()
									+ " errors in " + (((int) (end - start) / 100) / 10.0)
									+ " seconds, " + totalPermutations + " permutations",
							ScreenProperties.getUndoPopupColor(), 3000, true);
				}
				else
					monitor.stopActivity();

				// resync just in case a change was made
				resyncDiagramsAndBrowser();
			}
		});
		monitor.waitForFinish();
	}

	private class EditEnvironmentPreferencesAction extends AbstractAction
	{
		public EditEnvironmentPreferencesAction()
		{
			super("Edit environment preferences");
		}


		public void actionPerformed(ActionEvent e)
		{
			Dimension size = new Dimension(750, 500);
			String prefix = GlobalPreferences.preferences.getPrefix();
			String prefixTitle = (prefix == null || prefix.length() == 0) ? ""
					: (" (" + prefix + ")");
			final JDialog dialog = new JDialog(frame, "Environment Preferences" + prefixTitle, true);
			dialog.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
			dialog.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);

			dialog.setPreferredSize(size);
			dialog.setLocation(frame.getLocationOnScreen().x + frame.getWidth() / 2
					- size.width / 2, frame.getLocationOnScreen().y + frame.getHeight()
					/ 2 - size.height / 2);
			dialog.add(GlobalPreferences.preferences.getVisualComponent(dialog, true));
			dialog.pack();
			dialog.setVisible(true);

			// resync all diagrams just in case a change was made to something visual
			GlobalDiagramRegistry.resyncViews();
		}
	}

	private class SmartMenuContributorFacetImpl implements
			SmartMenuContributorFacet
	{
		private UpdatingJMenu openRecent;

		public List<SmartMenuItem> getSmartMenuItems(SmartMenuBarFacet smartMenuBar)
		{
			List<SmartMenuItem> entries = new ArrayList<SmartMenuItem>();

			JMenuItem openNewWindow = new JMenuItem(new OpenNewWindowAction());
			GlobalPreferences.registerKeyAction("File", openNewWindow, "ctrl W", "Open a new window");
			entries.add(new SmartMenuItemImpl("File", "Windows", openNewWindow));

			JMenuItem openTopLevelDiagram = new JMenuItem(new OpenNewTabAction());
			entries.add(new SmartMenuItemImpl("File", "Windows", openTopLevelDiagram));
			GlobalPreferences.registerKeyAction("File", openTopLevelDiagram, null, "Open a new tab with the toplevel model in");
			
			JMenuItem fullScreen = new JMenuItem(new FullScreenAction());
			fullScreen.setIcon(FULLSCREEN_ICON);
			entries.add(new SmartMenuItemImpl("File", "Windows", fullScreen));
			GlobalPreferences.registerKeyAction("File", fullScreen, "F11", "Toggle fullscreen mode");
			
			JMenuItem openClipboard = new JMenuItem(new OpenClipboardDiagramAction());
			entries.add(new SmartMenuItemImpl("File", "Windows", openClipboard));
			GlobalPreferences.registerKeyAction("File", openClipboard, null, "Open the clipboard diagram");

			JMenuItem exportGroupedImages = new JMenuItem(
					new ExportGroupedImagesAction(frame, coordinator, monitor, recent));
			entries.add(new SmartMenuItemImpl("File", "Windows", exportGroupedImages));
			GlobalPreferences.registerKeyAction("File", exportGroupedImages, "control E", "Export all the grouped images in the model");

			// add some open options
			JMenuItem createXMLRepositoryAction = new JMenuItem(new CreateNewXMLRepositoryAction());
			createXMLRepositoryAction.setIcon(BLANK_ICON);
			entries.add(new SmartMenuItemImpl("File", "Create", createXMLRepositoryAction));
			GlobalPreferences.registerKeyAction("File", createXMLRepositoryAction, null, "Create a new XML-based model");
			
			JMenuItem open = new JMenu("Open");
			entries.add(new SmartMenuItemImpl("File", "Open", open));

			JMenuItem openExistingXMLRepositoryAction = new JMenuItem(new OpenExistingXMLRepositoryAction());
			openExistingXMLRepositoryAction.setIcon(XML_ICON);
			open.add(openExistingXMLRepositoryAction);
			GlobalPreferences.registerKeyAction("File/Open", openExistingXMLRepositoryAction, null, "Open an existing XML model");

			// only add the database entries if the database jars are present
			try
			{
				ClassLoader.getSystemClassLoader().loadClass("com.objectdb.jdo.PMImpl");
				{
					JMenuItem openLocalDbRepositoryAction = new JMenuItem(
							new OpenLocalDbRepositoryAction());
					openLocalDbRepositoryAction.setIcon(LOCALDB_ICON);
					open.add(openLocalDbRepositoryAction);
					GlobalPreferences.registerKeyAction("File/Open", openLocalDbRepositoryAction, null, "Open a local database model");

					JMenuItem openRemoteDbRepositoryAction = new JMenuItem(
							new OpenRemoteDbRepositoryAction());
					openRemoteDbRepositoryAction.setIcon(REMOTEDB_ICON);
					open.add(openRemoteDbRepositoryAction);
					GlobalPreferences.registerKeyAction("File/Open", openRemoteDbRepositoryAction, null, "Open a remote database model");
				}
			} catch (ClassNotFoundException e)
			{
			}
			
			// add in a recently opened items list
			openRecent = new UpdatingJMenu("Open recent")
			{
				public boolean update()
				{
					// remove all the options
					openRecent.removeAll();
					// add them back
					List<String> recentFiles = recent.getRecentFiles();
					for (final String name : recentFiles)
					{
						JMenuItem item = new JMenuItem(name);
						openRecent.add(item);

						item.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								openFile(name, true);
							}
						});
					}
					return !recentFiles.isEmpty();
				}
			};
			entries.add(new SmartMenuItemImpl("File", "Open", openRecent));

			// add in an item to load the last opened file
			UpdatingJMenuItem openLast = new UpdatingJMenuItem("Open last model")
			{
				public boolean update()
				{
					return !recent.getRecentFiles().isEmpty();
				}				
			};
			entries.add(new SmartMenuItemImpl("File", "Open", openLast));
			openLast.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					List<String> recentFiles = recent.getRecentFiles();
					if (!recentFiles.isEmpty())
						openFile(recentFiles.get(0), true);
				}
			});
			GlobalPreferences.registerKeyAction("File", openLast, "ctrl shift R", "Open the last opened model");
			
			// add the save menu entries
			JMenuItem save = new UpdatingJMenuItem(new SaveAction())
			{
				public boolean update()
				{
					SaveInformation save = GlobalSubjectRepository.repository.getSaveInformation();
					return save.projectNeedsSaving();
				}

				@Override
				protected void fireActionPerformed(ActionEvent event)
				{
					super.alwaysFireActionPerformed(event);
				}
			};
			save.setIcon(SAVE_ICON);
			GlobalPreferences.registerKeyAction("File", save, "ctrl S", "Saves the model");
			entries.add(new SmartMenuItemImpl("File", "Save", save));

			JMenuItem saveAs = new UpdatingJMenuItem(new SaveAsAction())
			{
				public boolean update()
				{
					return GlobalSubjectRepository.repository.supportsSaveAs();
				}
			};
			GlobalPreferences.registerKeyAction("File", saveAs, null, "Copies over to a new model and opens that");
			entries.add(new SmartMenuItemImpl("File", "Save", saveAs));
			JMenuItem saveTo = new UpdatingJMenuItem(new SaveToAction())
			{
				public boolean update()
				{
					return GlobalSubjectRepository.repository.supportsSaveTo();
				}
			};
			GlobalPreferences.registerKeyAction("File", saveTo, null, "Saves the model to another file, but remains on the original");
			entries.add(new SmartMenuItemImpl("File", "Save", saveTo));

			JMenuItem refresh = new JMenuItem(new RefreshAction());
			refresh.setIcon(REFRESH_ICON);
			GlobalPreferences.registerKeyAction("File", refresh, "F5", "Refresh the model from the repository");
			entries.add(new SmartMenuItemImpl("File", "Maintenance", refresh));

			JMenuItem collect = new JMenuItem(new GarbageCollectRepositoryAction(true));
			collect.setIcon(GARBAGE_ICON);
			GlobalPreferences.registerKeyAction("File", collect, null, "Perform garbage collection on the model");
			entries.add(new SmartMenuItemImpl("File", "Maintenance", collect));

			JMenuItem openBrowser = new JMenuItem(new OpenModelBrowserAction());
			openBrowser.setIcon(BROWSER_REUSABLE_ICON);
			GlobalPreferences.registerKeyAction("File", openBrowser, null, "Open the browser on the top level element");
			entries.add(new SmartMenuItemImpl("File", "Browser", openBrowser));

			// add the model import / export features
			JMenuItem examineExport = new JMenuItem(new InspectAndImportStrataAction(coordinator, popup, frame, monitor,
				new ISaver()
				{
					public int askAboutSave(String message)
					{
						return ApplicationWindow.this.askAboutSave(message, true);
					}

					public AbstractAction makeGarbageCollectAction()
					{
						return new GarbageCollectRepositoryAction(false);
					}

					public AbstractAction makeRefreshAction()
					{
						return new RefreshAction();
					}
				}));
			entries.add(new SmartMenuItemImpl("File", "ImportExport", examineExport));
			GlobalPreferences.registerKeyAction("File", examineExport, null, "Inspect and possibly import an external model");
			JMenuItem exportItem = new UpdatingJMenuItem(new ExportStrataAction(coordinator, popup, frame, monitor))
			{
				public boolean update()
				{
					return !new ModelMover(frame, coordinator.getCurrentDiagramView()).getSelectedTopLevelPackages().isEmpty();
				}
			};
			entries.add(new SmartMenuItemImpl("File", "ImportExport", exportItem));

			JMenuItem exit = new JMenuItem(new ExitAction());
			entries.add(new SmartMenuItemImpl("File", "Exit", exit));
			GlobalPreferences.registerKeyAction("File", exit, null, "Exit from Evolve");
			
			JMenuItem undo = new UpdatingJMenuItem(new UndoAction())
			{
				public boolean update()
				{
					return commandManager.getTransactionPosition() > 0;
				}
			};
			
			GlobalPreferences.registerKeyAction("Edit", undo, "ctrl Z", "Undo the last command");
			undo.setIcon(UNDO_ICON);
			entries.add(new SmartMenuItemImpl("Edit", "UndoRedo", undo));

			JMenuItem redo = new UpdatingJMenuItem(new RedoAction())
			{
				public boolean update()
				{
					return commandManager.getTransactionPosition() < commandManager.getTotalTransactions();
				}
			};
			GlobalPreferences.registerKeyAction("Edit", redo, "ctrl Y", "Redo the last command");
			redo.setIcon(REDO_ICON);
			entries.add(new SmartMenuItemImpl("Edit", "UndoRedo", redo));

			// html
			JMenuItem htmlItem = new JMenuItem(new GenerateHTMLAction());
			htmlItem.setIcon(WORLD_ICON);
			GlobalPreferences.registerKeyAction("Tools", htmlItem, "ctrl H", "Generate HTML from the model");
			entries.add(new SmartMenuItemImpl("Tools", "HTML", htmlItem));

			// add the backbone tagger
			JMenuItem tagItem = new JMenuItem(new TagBackboneAction());
			tagItem.setIcon(TAG_ICON);
			GlobalPreferences.registerKeyAction("Tools", tagItem, "ctrl T", "Tag elements for generation or running");
			entries.add(new SmartMenuItemImpl("Tools", "Backbone", tagItem));

			// add the backbone source generator
			JMenuItem generateItem = new JMenuItem(new GenerateMixedAction());
			GlobalPreferences.registerKeyAction("Tools",generateItem, "ctrl G", "Generate Backbone code and implementation code for the model");
			entries.add(new SmartMenuItemImpl("Tools", "Backbone", generateItem));

			// add the backbone full implementation generator
			JMenuItem fullGenerateItem = new JMenuItem(new GenerateFullImplementationAction());
			GlobalPreferences.registerKeyAction("Tools",fullGenerateItem, "ctrl shift G", "Generate hardcoded classes and leaf implementations for the model");
			entries.add(new SmartMenuItemImpl("Tools", "Backbone", fullGenerateItem));

			// add the backbone rerunner
			JMenuItem rerunItem = new JMenuItem(new RunBackboneAction());
			rerunItem.setIcon(RUN_ICON);
			GlobalPreferences.registerKeyAction("Tools",rerunItem, "ctrl R", "Run the Backbone model");
			entries.add(new SmartMenuItemImpl("Tools", "Backbone", rerunItem));
			
      // add the bean importer
      JMenuItem beanImport = new BeanImportMenuItem(coordinator, popup, monitor); 
      entries.add(new SmartMenuItemImpl("Tools", "Beans", beanImport));      		
			GlobalPreferences.registerKeyAction("Tools",beanImport, null, "Analyse and import JavaBeans from the classpath of the selected stratum");

			// add the protocol analyser
			JMenuItem protocolItem = new JMenuItem(new AnalyseProtocolAction());
			GlobalPreferences.registerKeyAction("Tools",protocolItem, "control P", "Analyse the LTSA protocol of the tagged element");
			entries.add(new SmartMenuItemImpl("Tools", "Backbone", protocolItem));

			// order the menus
			smartMenuBar.addSectionOrderingHint("File", "Create", "a");
			smartMenuBar.addSectionOrderingHint("File", "Open", "aa");
			smartMenuBar.addSectionOrderingHint("File", "Save", "b");
			smartMenuBar.addSectionOrderingHint("File", "ImportExport", "c");
			smartMenuBar.addSectionOrderingHint("File", "Maintenance", "d");
			smartMenuBar.addSectionOrderingHint("File", "Browser", "e");
			smartMenuBar.addSectionOrderingHint("File", "Windows", "f");
			smartMenuBar.addSectionOrderingHint("Edit", "UndoRedo", "a");

			// add the help entries
			smartMenuBar.addSectionOrderingHint(">Help", "Contents", "a");
			smartMenuBar.addSectionOrderingHint(">Help", "About", "b");

			JMenuItem aboutItem = new JMenuItem(new HelpAboutAction(coordinator, popup));			
			entries.add(new SmartMenuItemImpl(">Help", "About", aboutItem));
			GlobalPreferences.registerKeyAction("Help", aboutItem, null, "Display the help about dialog");

			JMenuItem contentsItem = new JMenuItem(
					new HelpContentsAction(coordinator));
			contentsItem.setIcon(HELP_ICON);
			entries.add(new SmartMenuItemImpl(">Help", "Contents", contentsItem));
			GlobalPreferences.registerKeyAction("Help", contentsItem, null, "Display the help documentation");

			// add the error checking items
			JMenuItem clearErrorsItem = new JMenuItem(new HideErrorsAction());
			clearErrorsItem.setIcon(BLANK_ICON);
			GlobalPreferences.registerKeyAction("Checking", clearErrorsItem, "F7", "Remove any error markers");
			entries.add(new SmartMenuItemImpl("Checking", "ClearErrors", clearErrorsItem));

			JMenuItem stratumCheckItem = new JMenuItem(new CheckStratumItem());
			stratumCheckItem.setIcon(CHECK_ONE_ICON);
			GlobalPreferences.registerKeyAction("Checking", stratumCheckItem, "F8", "Check the current stratum for errors");
			entries.add(new SmartMenuItemImpl("Checking", "Errors", stratumCheckItem));

			JMenuItem checkAllItem = new JMenuItem(new CheckAllItem());
			checkAllItem.setIcon(CHECK_ALL_ICON);
			GlobalPreferences.registerKeyAction("Checking", checkAllItem, "shift F8", "Check the entire model (and all permutations) for errors");
			entries.add(new SmartMenuItemImpl("Checking", "Errors", checkAllItem));

			JMenuItem toggleDeltaViewItem = new JMenuItem(new ToggleDeltaViewAction());
			toggleDeltaViewItem.setIcon(DELTA_ICON);
			GlobalPreferences.registerKeyAction("View", toggleDeltaViewItem, "F6", "Toggle the delta indicators");
			entries.add(new SmartMenuItemImpl("View", "Deltas",
					toggleDeltaViewItem));

			smartMenuBar.addSectionOrderingHint("Checking", "ClearErrors", "a");
			smartMenuBar.addSectionOrderingHint("Checking", "Errors", "b");
			smartMenuBar.addSectionOrderingHint("Checking", "Deltas", "c");

			// add the import and export model preferences actions
			// add the environment editor
			JMenu preferences = new JMenu("Preferences");
			JMenuItem editPreferences = new JMenuItem(
					new EditEnvironmentPreferencesAction());
			editPreferences.setIcon(PREFERENCES_ICON);
			preferences.add(editPreferences);
			JMenuItem importPreferences = new JMenuItem(
					new ImportPreferencesAction());
			JMenuItem exportPreferences = new JMenuItem(
					new ExportPreferencesAction());
			GlobalPreferences.registerKeyAction("File/Preferences", editPreferences, "F4", "Edit environment preferences");
			preferences.add(importPreferences);
			GlobalPreferences.registerKeyAction("File/Preferences", importPreferences, null, "Import environment preferences");
			preferences.add(exportPreferences);
			GlobalPreferences.registerKeyAction("File/Preferences", exportPreferences, null, "Export environment preferences");
			entries.add(new SmartMenuItemImpl("File", "Maintenance", preferences));
			
			// register some nice icons for the preference tabs
			GlobalPreferences.preferences.registerTabIcon("Keys", KEYBOARD_ICON);
			GlobalPreferences.preferences.registerTabIcon("Advanced", COG_ICON);
			GlobalPreferences.preferences.registerTabIcon("Variables", VARIABLES_ICON);
			GlobalPreferences.preferences.registerTabIcon("Backbone", null);
			
			return entries;
		}

	}

	public void askAboutSaveAndPossiblyExit()
	{
		int chosen = askAboutSave("Evolve", false);
		if (chosen != JOptionPane.CANCEL_OPTION)
			applicationWindowCoordinator.exitApplication();
	}

	public int askAboutSave(String text, boolean force)
	{
		// if we need saving, bring up a dialog
		// if we are a remote client, leave immediately
		SaveInformation save = GlobalSubjectRepository.repository.getSaveInformation();
		if (save.projectNeedsSaving() || force)
		{
			int diagramsToSave = save.getDiagramsToSave();

			// form an appropriate information string
			String status = "";
			if (diagramsToSave == 0)
				status = "(repository has been modified)";
			else if (diagramsToSave == 1)
			{
				if (save.getRepositoryToSave())
					status = "(repository and 1 diagram have been modified)";
				else
					status = "(1 diagram has been modified)";
			} else
			{
				if (save.getRepositoryToSave())
					status = "(repository and " + diagramsToSave
							+ " diagrams have been modified)";
				else
					status = "(" + diagramsToSave + " diagrams have been modified)";
			}

			int chosen = coordinator.invokeYesNoCancelDialog(
					text,
					new JLabel("<html><b>Save modified project?</b><p><p>" + status + "<p><p></html>"));

			// if yes, save the diagram
			if (chosen == JOptionPane.OK_OPTION)
			{
				String fileName = GlobalSubjectRepository.repository.save(frame, false, getLastVisitedDirectory());
				if (fileName == null)
					return JOptionPane.CANCEL_OPTION;
				if (fileName.length() != 0)
				{
					recent.setLastVisitedDirectory(new File(fileName));
					recent.addFile(fileName);
				}
				GlobalDiagramRegistry.registry.enforceMaxUnmodifiedUnviewedDiagramsLimit();
			}

			return chosen;
		}

		return JOptionPane.YES_OPTION;
	}

	private File getLastVisitedDirectory()
	{
		return recent.getLastVisitedDirectory();
	}

	JInternalFrame current;

	public void openTopLevel(boolean explicitlyMaximize, boolean closeable)
	{
		viewRegistry.open(
				GlobalSubjectRepository.repository.getTopLevelModel(),
				true,
				false,
				null,
				null,
				true);
	}

	public void reset(String windowTitle)
	{
		viewRegistry.reset();
		setTitle(windowTitle);

		// close all content windows, leaving the palette
		desktop.closeRememberedDockables();

		viewRegistry.open(
				GlobalSubjectRepository.repository.getTopLevelModel(),
				true,
				false,
				null,
				null,
				true);

		// clear out the previous backbone generation options
		choice = null;
	}

	public void refreshTitle(String windowTitle)
	{
		setTitle(windowTitle);
	}

	private void openFile(final String name, final boolean setLastVisited)
	{
		// before opening, ask if the current file needs saving
		int chosen = askAboutSave("Open recent file", false);
		if (chosen == JOptionPane.CANCEL_OPTION)
			return;

		// open the file
		monitor.invokeActivityAndMonitorProgress(new Runnable()
		{
			public void run()
			{
				boolean remote = false;
				long start = System.currentTimeMillis();
				try
				{
					if (name.endsWith(XMLSubjectRepositoryGem.UML2_SUFFIX) || name.endsWith(XMLSubjectRepositoryGem.UML2Z_SUFFIX) || name.endsWith(".xml"))
					{
						monitor.displayInterimPopup(SAVE_ICON, "Loading XML repository", name, null, -1);
						RepositoryUtility.useXMLRepository(name);
						applicationWindowCoordinator.switchRepository();
					}
					else if (name.contains(":") && name.endsWith(".odb"))
					{
						remote = true;
						// parse out the host and database name
						monitor.displayInterimPopup(SAVE_ICON, "Loading remote database repository", name, null, -1);
						StringTokenizer tokens = new StringTokenizer(name, ":");
						List<String> bits = new ArrayList<String>();
						while (tokens.hasMoreTokens())
						{
							String bit = tokens.nextToken();
							bits.add(bit);
						}
						if (bits.size() != 2)
						{
							monitor.stopActivityAndDisplayPopup(
									SAVE_ICON,
									"Repository loading problem",
									"Problem interpreting recent file name " + name,
									null,
									3000,
									false);
							recent.removeFile(name);
							return;
						}
						String hostName = bits.get(0);
						String dbName = bits.get(1);
		
						RepositoryUtility.useObjectDbRepository(hostName, dbName);
						applicationWindowCoordinator.switchRepository();
					}
					else if (name.endsWith(ObjectDbSubjectRepositoryGem.UML2DB_SUFFIX))
					{
						monitor.displayInterimPopup(SAVE_ICON, "Loading local database repository", name, null, -1);
						RepositoryUtility.useObjectDbRepository(null, name);
						applicationWindowCoordinator.switchRepository();
					}
					else
					{
						monitor.stopActivityAndDisplayPopup(
								SAVE_ICON,
								"Repository loading problem",
								"File " + name + " must be of form \n\t*.uml2, *.uml2z *.xml, *.uml2db or host:*.odb",
								null,
								3000,
								false);
						recent.removeFile(name);
						return;
					}
					
					long end = System.currentTimeMillis();
					long msecs = ((end - start) / 100) * 100;
					monitor.stopActivityAndDisplayPopup(
							SAVE_ICON,
							"Loaded repository",
							"Repository loaded in " + msecs / 1000.0 + " seconds",
							null,
							1000,
							false);
		
					if (!remote && setLastVisited)
						recent.setLastVisitedDirectory(new File(name));
					recent.addFile(name);
				}
				catch (RepositoryOpeningException ex)
				{
					monitor.stopActivityAndDisplayPopup(
							SAVE_ICON, "Repository loading problem", ex.getMessage(),
							null, 3000, false);
					recent.removeFile(name);
				}
			}
		});
	}
}
