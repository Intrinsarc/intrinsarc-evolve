package com.hopstepjump.jumble.importexport;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.eclipse.uml2.Package;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;
import com.hopstepjump.jumble.gui.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.*;

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
		ModelMover exporter = new ModelMover(frame, coordinator.getCurrentDiagramView());

		Set<Package> chosen = exporter.getSelectedTopLevelPackages();
		if (chosen.contains(GlobalSubjectRepository.repository.getTopLevelModel()))
		{
			popup.displayPopup(EXPORT_ICON, "Model export problem",
					"Cannot export top level model -- choose a sub-element",
					ScreenProperties.getUndoPopupColor(), Color.black, 1500);
			return;
		}
		String filename = exporter.exportPackages(monitor);
		if (filename != null)
		{
			PreferenceTypeDirectory.recent.setLastVisitedDirectory(new File(filename));
			popup.displayPopup(EXPORT_ICON, "Export successful",
					"Strata exported to " + filename, ScreenProperties.getUndoPopupColor(),
					Color.black, 3000);
		}
	}
}
