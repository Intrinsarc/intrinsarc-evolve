/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.hopstepjump.jumble.packageview.actions;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.guibase.*;
import com.hopstepjump.jumble.packageview.base.*;
import com.hopstepjump.swing.*;
import com.hopstepjump.swing.enhanced.*;

/**
 * @author Andrew
 */
public class GotoParentMenuItem extends UpdatingJMenuItem
{
	private static final ImageIcon UP_ICON = IconLoader.loadIcon("uptoparent.png");
	private static final ImageIcon TAB_UP_ICON = IconLoader.loadIcon("tab_up.png");
	private boolean newTab;
	private ToolCoordinatorFacet coordinator;
	
	public GotoParentMenuItem(ToolCoordinatorFacet coordinator, boolean newTab)
	{
		super("");
		this.newTab = newTab;
		this.coordinator = coordinator;
		setTextAndIcon();
	}
	
	/*
	 * @see com.hopstepjump.swing.enhanced.UpdatingJMenuItem#update(boolean)
	 */
	public boolean update()
	{
		ReusableDiagramViewFacet reusableView = GlobalPackageViewRegistry.activeRegistry.getFocussedView();
		if (reusableView == null)
			return false;

		DiagramFacet diagram = reusableView.getCurrentDiagramView().getDiagram();
		org.eclipse.uml2.Package parent = GotoDiagramAction.getParentDiagramPackage((org.eclipse.uml2.Package) diagram.getLinkedObject());
		if (parent == null)
			return false;
		
		KeyStroke accelerator = getAccelerator();
		setAction(new CursorWaitingAction(
				new GotoDiagramAction(
						diagram,
						"",
						newTab,
						true,
						true),
				coordinator,
				0));
		setTextAndIcon();
		setAccelerator(accelerator);

		return true;
	}
	
	private void setTextAndIcon()
	{
		setIcon(newTab ? TAB_UP_ICON : UP_ICON);
		setText(newTab ? "Open parent in new tab" : "Open parent");
	}
}	    
