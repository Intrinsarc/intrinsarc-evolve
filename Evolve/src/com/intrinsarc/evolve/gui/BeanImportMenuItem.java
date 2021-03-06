package com.intrinsarc.evolve.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.Package;

import com.intrinsarc.backbone.generator.*;
import com.intrinsarc.beanimporter.*;
import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.guibase.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.enhanced.*;

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
				final DEStratum perspective = GlobalDeltaEngine.engine.locateObject(pkg).asStratum();
				
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
									BeanAnalyzer analyzer = new BeanAnalyzer(perspective, path, monitor, countSoFar[0]);
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

						coordinator.startTransaction("", "");
						coordinator.commitTransaction();
						coordinator.clearTransactionHistory();
						GlobalSubjectRepository.ignoreUpdates = false;												

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
			}
		});
	}
	
	public boolean update()
	{
		boolean enabled = true;
		if (getPossibleSingleSelectedPackage() != null)
		{
			setText("Import beans into selected package");
			enabled = !getPossibleSingleSelectedPackage().isReadOnly();
		}
		else
		if (getPossibleCurrentPackage() != null)
		{
			setText("Import beans into current package");
			enabled = !getPossibleCurrentPackage().isReadOnly();
		}
		else
			enabled = false;
		
		return enabled;
	}
	
	public static Package getPossibleSingleSelectedPackage()
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
	
	public static Package getPossibleCurrentPackage()
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
