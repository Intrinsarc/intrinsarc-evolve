/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.evolve.packageview.actions;

import com.intrinsarc.evolve.clipboardactions.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.swing.enhanced.*;

/**
 * @author Andrew
 */
public class PasteMenuItem extends UpdatingJMenuItem
{
	private ToolCoordinatorFacet toolCoordinator;
	private DiagramViewFacet diagramView;

	/**
	 * @param currentView
	 * @param toolCoordinatorFacet
	 */
	public PasteMenuItem(DiagramViewFacet diagramView, ToolCoordinatorFacet toolCoordinator)
	{
		super(new CursorWaitingAction(
		    new PasteAction(
		        toolCoordinator,
		        diagramView,
		        GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE)),
		        toolCoordinator,
		        0));
		this.diagramView = diagramView;
		this.toolCoordinator = toolCoordinator;		
	}

	/*
	 * @see com.hopstepjump.swing.enhanced.UpdatingJMenuItem#update(boolean)
	 */
	public boolean update()
	{
		return
			!GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE).isEmpty() &&
			!diagramView.getDiagram().isReadOnly();
	}
}