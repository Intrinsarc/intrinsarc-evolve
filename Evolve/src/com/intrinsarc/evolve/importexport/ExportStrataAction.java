package com.intrinsarc.evolve.importexport;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.evolve.gui.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repository.modelmover.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class ExportStrataAction extends AbstractAction
{
	public static final ImageIcon EXPORT_ICON = IconLoader.loadIcon("export.png");
	private ToolCoordinatorFacet coordinator;
	private PopupMakerFacet popup;
	private JFrame frame;
	private LongRunningTaskProgressMonitor monitor;	

	public ExportStrataAction(ToolCoordinatorFacet coordinator, PopupMakerFacet popup, JFrame frame, LongRunningTaskProgressMonitor monitor)
	{
		super("Export selected strata", EXPORT_ICON);
		this.coordinator = coordinator;
		this.popup = popup;
		this.frame = frame;
		this.monitor = monitor;
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		Set<Package> chosen = getSelectedTopLevelPackages(coordinator.getCurrentDiagramView());
		ModelExporter exporter = new ModelExporter(frame, chosen);

		if (chosen.contains(GlobalSubjectRepository.repository.getTopLevelModel()))
		{
			popup.displayPopup(EXPORT_ICON, "Model export problem",
					"Cannot export top level model -- choose a sub-element",
					ScreenProperties.getUndoPopupColor(), Color.black, 1500);
			return;
		}
		try
		{
			GlobalSubjectRepository.ignoreUpdates = true;
			String filename = exporter.exportPackages(monitor);
			if (filename != null)
			{
				PreferenceTypeDirectory.recent.setLastVisitedDirectory(new File(filename));
				popup.displayPopup(EXPORT_ICON, "Export successful",
						"Strata exported to " + filename, ScreenProperties.getUndoPopupColor(),
						Color.black, 3000);
			}
		}
		finally
		{
			GlobalSubjectRepository.ignoreUpdates = true;
		}
	}
	
	public static Set<Package> getSelectedTopLevelPackages(DiagramViewFacet diagramView)
	{
	  // collect the top level elements we've been asked to export
	  DiagramFacet diagram = diagramView.getDiagram();
	  Set<String> selected = CopyToDiagramUtilities.getFigureIdsIncludedInSelection(diagramView, false);
	  Collection<String> topLevel = CopyToDiagramUtilities.getTopLevelFigureIdsOnly(diagramView.getDiagram(), selected, 0, true);
	  
	  // turn these into elements
	  Set<Package> elements = new HashSet<Package>();
	  for (String id : topLevel)
	  {
	    Element subject = (Element) diagram.retrieveFigure(id).getSubject();
	    if (subject != null && !subject.isThisDeleted() && subject instanceof Package)
	      elements.add((Package) subject);
	  }
	  return elements;
	}

}
