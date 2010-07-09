package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.backbone.generator.*;
import com.hopstepjump.beanimporter.*;
import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.guibase.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

public class BeanImportMenuItem extends UpdatingJMenuItem
{
	public static final ImageIcon BEAN_ADD_ICON = IconLoader.loadIcon("bean_add.png");
	public static final ImageIcon CANCEL_ICON = IconLoader.loadIcon("icon_cancel.png");
	
	public BeanImportMenuItem(
			final ToolCoordinatorFacet coordinator,
			final PopupMakerFacet popupMaker,
			final LongRunningTaskProgressMonitorFacet monitor)
	{
		super("Import beans", BEAN_ADD_ICON);
		
		addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Package pkg = getPossibleSingleSelectedPackage();
				if (pkg == null)
					pkg = getPossibleCurrentPackage();
				if (pkg == null)
					return;
				
				try
				{
					final List<String> paths = BackboneGenerator.getClasspathsOfStratum(pkg);
					
					// look in the repository first
					final BeanFinder finder = new BeanFinder(GlobalDeltaEngine.engine.locateObject(pkg).asStratum(), monitor);
					monitor.invokeActivityAndMonitorProgress(new Runnable()
					{
						public void run()
						{
							finder.findBeans();
						}
					});
					
					// analyze the classpath next
					final Map<String, BeanPackage> roots = new LinkedHashMap<String, BeanPackage>();
					final int countSoFar[] = new int[]{0};
					monitor.invokeActivityAndMonitorProgress(new Runnable()
					{
						public void run()
						{
							try
							{
								for (String path : paths)
								{
									BeanAnalyzer analyzer = new BeanAnalyzer(path, monitor, countSoFar[0]);
									countSoFar[0] = analyzer.analyzeClasses(finder);								
									roots.put(path, analyzer.getRoot());
								}
							}
							catch (IOException e)
							{
								monitor.stopActivityAndDisplayDialog("IO error while analyzing classes", e.getMessage(), true);
							}
						}
					});
					
					// now load these into the finder
					monitor.invokeActivityAndMonitorProgress(new Runnable()
					{
						public void run()
						{
							finder.examineBeanPackages(roots.values());
						}
					});
					
					monitor.displayInterimPopup(null, "Bean importer...", "Processed jar and class files successfully", null, 1500);
					
					BeanImporter importer = new BeanImporter(coordinator, pkg, roots, finder);
					int button = importer.importBeansAndLeaves();
					
					if (button == 0)
					{
						popupMaker.displayPopup(BEAN_ADD_ICON, "Bean Import...", "Import cancelled",
								ScreenProperties.getUndoPopupColor(), Color.black, 1500);					
					}
					else
					{
						// create (import) the beans in the package
						BeanSubjectCreator creator = new BeanSubjectCreator(importer.getImportList(), pkg, finder, monitor);
						GlobalSubjectRepository.ignoreUpdates = true;
						BeanCreatedSubjects created = creator.createSubjects();

						popupMaker.displayPopup(BEAN_ADD_ICON, "Bean Import...",
								"Imported " + created.getTotalMade() + " elements; cleared command history",
								ScreenProperties.getUndoPopupColor(), Color.black, 3000, true,
								coordinator.getTransactionPosition(), coordinator.getTotalTransactions());
					}
				}
				catch (BackboneGenerationException ex)
				{
					coordinator.invokeErrorDialog("Exception when looking for classpath of stratum", ex.getMessage());
				}
				catch (VariableNotFoundException ex)
				{
					coordinator.invokeErrorDialog("Environment variable not found", ex.getMessage());
				}
				finally
				{
					coordinator.startTransaction("", "");
					coordinator.commitTransaction();
					coordinator.clearTransactionHistory();
					GlobalSubjectRepository.ignoreUpdates = false;					
				}
			}
		});
	}
	
	public boolean update()
	{
		boolean enabled = true;
		if (getPossibleSingleSelectedPackage() != null)
			setText("Import beans into selected package");
		else
		if (getPossibleCurrentPackage() != null)
			setText("Import beans into current package");
		else
			enabled = false;
		return enabled;
	}
	
	private Package getPossibleSingleSelectedPackage()
	{
		// get a possible single selected package
		ReusableDiagramViewFacet reusable = GlobalPackageViewRegistry.activeRegistry.getFocussedView();
		if (reusable != null)
		{
			DiagramViewFacet current = reusable.getCurrentDiagramView();
			if (current != null)
			{
				FigureFacet first = current.getSelection().getFirstSelectedFigure();
				if (first != null && first.getSubject() instanceof Package)
					return (Package) first.getSubject();
			}
		}
		return null;
	}
	
	private Package getPossibleCurrentPackage()
	{
		ReusableDiagramViewFacet reusable = GlobalPackageViewRegistry.activeRegistry.getFocussedView();
		if (reusable != null)
		{
			DiagramViewFacet current = reusable.getCurrentDiagramView();
			if (current != null)
				return (Package) current.getDiagram().getLinkedObject();
		}
		return null;
	}
}
