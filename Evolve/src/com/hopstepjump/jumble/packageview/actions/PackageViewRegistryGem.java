package com.hopstepjump.jumble.packageview.actions;

import java.awt.*;
import java.util.*;
import java.util.List;

import org.eclipse.uml2.Package;

import com.hopstepjump.easydock.*;
import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.jumble.guibase.*;
import com.hopstepjump.jumble.packageview.base.*;

/**
 *
 * (c) Andrew McVeigh 12-Sep-02
 *
 */
public class PackageViewRegistryGem implements Gem
{
	public static final String CLIPBOARD_TYPE = "clipboard";
	private ToolCoordinatorFacet coordinator;
	private IEasyDock desktop;
	private PackageViewRegistryFacet registryFacet = new PackageViewRegistryFacetImpl();
	private List<ReusableDiagramViewFacet> views = new ArrayList<ReusableDiagramViewFacet>();
	private KeyInterpreterFacet clipboardKeysFacet = new KeyInterpreterGem().getKeyInterpreterFacet();
	private KeyInterpreterFacet diagramKeysFacet = new KeyInterpreterGem().getKeyInterpreterFacet();
	private ReusableDiagramViewFacet focussedView;
  private List<DiagramFigureAdornerFacet> adorners;
  private List<PackageViewSmartMenuItemMaker> menuItemMakers;
	
	public PackageViewRegistryGem(ToolCoordinatorFacet coordinator, List<DiagramFigureAdornerFacet> adorners, List<PackageViewSmartMenuItemMaker> menuItemMakers, IEasyDock desktop)
	{
		this.coordinator = coordinator;
		this.desktop = desktop;
		this.adorners = adorners;
		this.menuItemMakers = menuItemMakers;
	}
	
	public PackageViewRegistryFacet getPackageDiagramViewRegistryFacet()
	{
		return registryFacet;
	}
	
	private class PackageViewRegistryFacetImpl implements PackageViewRegistryFacet
	{
		/**
		 * @see packageview.PackageDiagramViewRegistryFacet#clone(PackageDiagramViewFacet)
		 */
		public void clone(ReusableDiagramViewFacet view)
		{
		}

		/**
		 * @see packageview.PackageDiagramViewRegistryFacet#close(PackageDiagramViewFacet)
		 */
		public void haveClosed(ReusableDiagramViewFacet view)
		{
			// called by the window when it closes -- make sure we remove the view (the view deregisters its own listeners etc)
			views.remove(view);
			if (focussedView == view)
				focussedView = null;
		}


		/**
		 * @see com.hopstepjump.jumble.packageview.PackageDiagramViewRegistryFacet#openClipboard(String, boolean, boolean)
		 */
		public void openClipboard(String name, boolean explicitlyMaximize, boolean closeable, List<DiagramFigureAdornerFacet> adorners, Package possibleFixedPerspective)
		{
			// see if we can find a package diagram view that is reusable -- should only be one
			ReusableDiagramViewFacet view = getType(CLIPBOARD_TYPE);
			if (view == null)
			{
				ReusableDiagramViewGem gem = new ReusableDiagramViewGem(coordinator, adorners, desktop, CLIPBOARD_TYPE, closeable, Color.WHITE, false, possibleFixedPerspective);
				gem.connectReusableDiagramViewContextFacet(new ClipboardViewContextGem().getReusableDiagramViewContextFacet());
				gem.connectKeyInterpreterFacet(clipboardKeysFacet);
				view = gem.getReusableDiagramViewFacet();
				views.add(view);				
			}
			else
				if (view.getFixedPerspective() != possibleFixedPerspective)
					view.setFixedPerspective(possibleFixedPerspective);

			view.setExplicitName(name);
			DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeClipboardDiagram(new DiagramReference(name));
			view.viewDiagram(diagram, null, true);
		}

		/**
		 * @see packageview.PackageDiagramViewRegistryFacet#open(String, String)
		 */
		public void open(final Package pkg, final boolean closeable, boolean openNewTab, UBounds openRegionHint, Package possibleFixedPerspective, boolean addToStack)
		{
			// open in the current view
			ReusableDiagramViewFacet view = focussedView;
			if (view == null || view.getType().equals(CLIPBOARD_TYPE) || openNewTab)
			{
				ReusableDiagramViewGem gem = new ReusableDiagramViewGem(coordinator, adorners, desktop, "diagram", closeable, Color.WHITE, true, possibleFixedPerspective);
				view = gem.getReusableDiagramViewFacet();
				gem.connectReusableDiagramViewContextFacet(
					new PackageViewContextGem(view, coordinator, menuItemMakers).getReusableDiagramViewContextFacet());
				gem.connectKeyInterpreterFacet(diagramKeysFacet);
				
				views.add(view);
			}
			else
				if (view.getFixedPerspective() != possibleFixedPerspective)
					view.setFixedPerspective(possibleFixedPerspective);
			
			// find the element in the repository
			DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(pkg.getUuid()), true);
			view.viewDiagram(diagram, openRegionHint, addToStack);
			focussedView = view;
      coordinator.changedDiagram(diagram);
      final ReusableDiagramViewFacet test = view;
      
      // make sure we take the focus, so that keyboard presses will work
      // the pause allows the menu choice to clear if that is how we got here...
      new Thread()
      {
      	public void run()
      	{
      		try
					{
						Thread.sleep(250);
					}
      		catch (InterruptedException e)
					{
					}
          test.getCurrentDiagramView().getCanvas().requestFocus();
      	}
      }.start();
      GlobalDiagramRegistry.registry.enforceMaxUnmodifiedUnviewedDiagramsLimit();
		}

		private ReusableDiagramViewFacet getType(String type)
		{
			// look through the list for a particular type of view
		  for (ReusableDiagramViewFacet view : views)
		  {
				if (view.getType().equals(type))
					return view;
			}
			return null;
		}
		
		/**
		 * @see com.hopstepjump.jumble.packageview.PackageDiagramViewRegistryFacet#isDiagramEmpty(String)
		 */
		public boolean isDiagramEmpty(Package pkg)
		{
			DiagramFacet diagram = GlobalDiagramRegistry.registry.retrieveOrMakeDiagram(new DiagramReference(pkg.getUuid()), true);
			return diagram.isEmpty();
		}
		
		/**
		 * @see com.hopstepjump.jumble.packageview.PackageDiagramViewRegistryFacet#getClipboardKeyInterpreter()
		 */
		public KeyInterpreterFacet getClipboardKeyInterpreter()
		{
			return clipboardKeysFacet;
		}

		/**
		 * @see com.hopstepjump.jumble.packageview.PackageDiagramViewRegistryFacet#getDiagramKeyInterpreter()
		 */
		public KeyInterpreterFacet getDiagramKeyInterpreter()
		{
			return diagramKeysFacet;
		}

		/**
		 * @see com.hopstepjump.jumble.packageview.PackageDiagramViewRegistryFacet#getClipboardDiagram(String)
		 */
		public DiagramFacet getClipboardDiagram(String name)
		{
			return GlobalDiagramRegistry.registry.retrieveOrMakeClipboardDiagram(new DiagramReference(name));
		}

		/**
		 * @see com.hopstepjump.jumble.packageview.base.PackageViewRegistryFacet#aboutToClose()
		 */
		public void aboutToClose()
		{
		  for (ReusableDiagramViewFacet view : views)
				view.removeAsDiagramListener();
		}

		/**
		 * @see com.hopstepjump.jumble.packageview.base.PackageViewRegistryFacet#haveFocus(ReusableDiagramViewFacet)
		 */
		public void haveFocus(ReusableDiagramViewFacet view)
		{
			focussedView = view;
		}

		/**
		 * @see com.hopstepjump.jumble.packageview.base.PackageViewRegistryFacet#reattachToCoordinator()
		 */
		public void reattachToCoordinator()
		{
			if (focussedView != null)
				focussedView.attachToCoordinator();
		}

    public void reset()
    {
      views = new ArrayList<ReusableDiagramViewFacet>();
    }

    public List<DiagramFacet> getViewedDiagrams()
    {
      List<DiagramFacet> viewed = new ArrayList<DiagramFacet>();
      for (ReusableDiagramViewFacet view : views)
        viewed.add(view.getCurrentDiagramView().getDiagram());
      return viewed;
    }

		public ReusableDiagramViewFacet getFocussedView()
		{
			return focussedView;
		}
	}

}
