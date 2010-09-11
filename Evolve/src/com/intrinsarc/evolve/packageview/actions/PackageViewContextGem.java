package com.intrinsarc.evolve.packageview.actions;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.clipboardactions.*;
import com.intrinsarc.evolve.guibase.*;
import com.intrinsarc.evolve.packageview.base.*;
import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.diagramsupport.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;
import com.intrinsarc.swing.smartmenus.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;
import edu.umd.cs.jazz.util.*;

/**
 *
 * (c) Andrew McVeigh 09-Jan-03
 *
 */
public class PackageViewContextGem
{
	private static final ImageIcon GLASSES_ICON = IconLoader.loadIcon("glasses.png");
	private static final ImageIcon RELAXED = IconLoader.loadIcon("relaxed-stratum.png");
	private static final ImageIcon STRICT = IconLoader.loadIcon("strict-stratum.png");
	private static final ImageIcon RELAXED_DESTRUCTIVE = IconLoader.loadIcon("relaxed-destructive-stratum.png");
	private static final ImageIcon STRICT_DESTRUCTIVE = IconLoader.loadIcon("strict-destructive-stratum.png");

	private ToolCoordinatorFacet toolCoordinatorFacet;
	private ReusableDiagramViewContextFacet context = new ReusableDiagramViewContextFacetImpl();
	private SmartMenuContributorFacet smartMenuContributorFacet = new SmartMenuContributorFacetImpl();
	private ReusableDiagramViewFacet reusableFacet;
  private List<PackageViewSmartMenuItemMaker> menuItemMakers;
  private static final ImageIcon BROWSER_ICON = IconLoader.loadIcon("browser.png");
  private static final ImageIcon CUT_ICON =     IconLoader.loadIcon("cut_red.png");
	private static final ImageIcon COPY_ICON =    IconLoader.loadIcon("page_copy.png");
	private static final ImageIcon PASTE_ICON =   IconLoader.loadIcon("page_paste.png");
	private static final ImageIcon DELETE_ICON =  IconLoader.loadIcon("delete16.gif");
	
	public PackageViewContextGem(
						ReusableDiagramViewFacet reusableFacet,
						ToolCoordinatorFacet toolCoordinatorFacet,
						List<PackageViewSmartMenuItemMaker> menuItemMakers)
	{
		this.reusableFacet = reusableFacet;
		this.toolCoordinatorFacet = toolCoordinatorFacet;
		this.menuItemMakers = menuItemMakers;
	}

	public ReusableDiagramViewContextFacet getReusableDiagramViewContextFacet()
	{
		return context;
	}
	
	private class SmartMenuContributorFacetImpl implements SmartMenuContributorFacet
	{
		/*
		 * @see com.intrinsarc.swing.smartmenus.SmartMenuContributorFacet#getSmartMenuItems(com.intrinsarc.swing.smartmenus.SmartMenuBarFacet)
		 */
		public List<SmartMenuItem> getSmartMenuItems(SmartMenuBarFacet smartMenuBar)
		{
			smartMenuBar.addSectionOrderingHint("Edit", "Clipboard", "b");
			smartMenuBar.addMenuOrderingHint("View", "c");
			smartMenuBar.addSectionOrderingHint("View", "Navigation", "b");
			smartMenuBar.addSectionOrderingHint("View", "History", "c");
			smartMenuBar.addMenuOrderingHint("Object", "d");
			smartMenuBar.addSectionOrderingHint("Object", "Operations", "b");
			List<SmartMenuItem> items = new ArrayList<SmartMenuItem>();
			
			DiagramViewFacet currentView = reusableFacet.getCurrentDiagramView();
			
			// cut
			JMenuItem cutItem = new CutMenuItem(currentView, toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Edit", cutItem, "ctrl X", "The clipboard cut operation");
			cutItem.setIcon(CUT_ICON);
			items.add(new SmartMenuItemImpl("Edit", "Clipboard", cutItem));

			// copy
			JMenuItem copyItem = new CopyMenuItem(currentView, GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE), toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Edit", copyItem, "ctrl C", "The clipboard copy operation");
			copyItem.setIcon(COPY_ICON);
			items.add(new SmartMenuItemImpl("Edit", "Clipboard", copyItem));

			// paste
			JMenuItem pasteItem = new PasteMenuItem(currentView, toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Edit", pasteItem, "ctrl V", "The clipboard paste operation");
			pasteItem.setIcon(PASTE_ICON);
			items.add(new SmartMenuItemImpl("Edit", "Clipboard", pasteItem));

			// delete
			JMenuItem deleteItem = new DeleteMenuItem(currentView, toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Edit", deleteItem, "DELETE", "The delete views operation");
			deleteItem.setIcon(DELETE_ICON);
			items.add(new SmartMenuItemImpl("Edit", "Clipboard", deleteItem));

			// delete model elements
			JMenuItem deleteSubjectsItem = new DeleteSubjectsMenuItem(currentView, toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Edit", deleteSubjectsItem, "ctrl D", "The delete subjects operation");
			items.add(new SmartMenuItemImpl("Edit", "Clipboard", deleteSubjectsItem));

			// select all
			JMenuItem selectAllItem = new SelectAllMenuItem(currentView, toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Edit", selectAllItem, "ctrl A", "The select all operation");
			items.add(new SmartMenuItemImpl("Edit", "Clipboard", selectAllItem));
			
			// goto parent
			DiagramFacet current = currentView.getDiagram();
			JMenuItem gotoParentItem = new GotoParentMenuItem(toolCoordinatorFacet, false);
			GlobalPreferences.registerKeyAction("View", gotoParentItem, "ctrl U", "Open the parent diagram in the current tab");
			items.add(new SmartMenuItemImpl("View", "Navigation", gotoParentItem));
			JMenuItem tabGotoParentItem = new GotoParentMenuItem(toolCoordinatorFacet, true);
			items.add(new SmartMenuItemImpl("View", "Navigation", tabGotoParentItem));
			GlobalPreferences.registerKeyAction("View", tabGotoParentItem, null, "Open the parent diagram in a new tab");
			JMenuItem tabGotoCurrentItem = new TabGotoCurrentMenuItem(current, toolCoordinatorFacet);
			items.add(new SmartMenuItemImpl("View", "Navigation", tabGotoCurrentItem));
			GlobalPreferences.registerKeyAction("View", tabGotoCurrentItem, null, "Open the current diagram in a new tab");
			JMenuItem goBackItem = new GoBackMenuItem(toolCoordinatorFacet);
			items.add(new SmartMenuItemImpl("View", "History", goBackItem));
			GlobalPreferences.registerKeyAction("View", goBackItem, "ctrl B", "Open the previous diagram in the stack");
			JMenuItem goForwardItem = new GoForwardMenuItem(toolCoordinatorFacet);
			items.add(new SmartMenuItemImpl("View", "History", goForwardItem));
			GlobalPreferences.registerKeyAction("View", goForwardItem, "ctrl F", "Open the next diagram in the stack");
			
			// locate elements
			JMenuItem locateElementsItem = new LocateElementsMenuItem(currentView, toolCoordinatorFacet);
			GlobalPreferences.registerKeyAction("Object", locateElementsItem, "ctrl L", "Locate the selected elements in this strata");
			items.add(new SmartMenuItemImpl("Object", "Operations", locateElementsItem));
      
      // browser objects
      JMenuItem browseElementItem = new BrowseElementMenuItem(currentView, toolCoordinatorFacet);
      browseElementItem.setIcon(BROWSER_ICON);
			GlobalPreferences.registerKeyAction("Object", browseElementItem, "F2", "View the selected element in the model browser");
      items.add(new SmartMenuItemImpl("Object", "Operations", browseElementItem));
      
      for (PackageViewSmartMenuItemMaker itemMaker : menuItemMakers)
        items.add(itemMaker.makeItem(currentView, toolCoordinatorFacet));
      
      return items;
		}
	}
	
	private class ReusableDiagramViewContextFacetImpl implements ReusableDiagramViewContextFacet
	{
		public boolean isModified(DiagramFacet diagram)
		{
			return diagram.isModified();
		}
		
    public String getFrameTitle(DiagramFacet diagram)
    {
      Package pkg = (Package) diagram.getLinkedObject();
      if (pkg == null)
      {
        System.err.println("Cannot find package for diagram = " + diagram.getDiagramReference()); 
        return "No package";
      }

      return
        GlobalSubjectRepository.repository.getFullyQualifiedName(pkg, DEObject.SEPARATOR);
    }
    
    public void setFrameName(DiagramViewFacet diagramView, Package fixedPerspective)
    {
    	setFrameNameForDiagram(diagramView, fixedPerspective);
    }    	
		
		/**
		 * @see com.intrinsarc.jumble.packageview.ReusableDiagramViewContextFacet#middleButtonPressed(DiagramFacet)
		 */
		public void middleButtonPressed(DiagramFacet diagram)
		{
			// find the package corresponding to the package id
			Package parent = GotoDiagramAction.getParentDiagramPackage((Package) diagram.getLinkedObject());
			if (parent != null)
				GlobalPackageViewRegistry.activeRegistry.open(parent, true, false, null, GlobalPackageViewRegistry.activeRegistry.getFocussedView().getFixedPerspective(), true);
		}
		
		/**
		 * @see com.intrinsarc.jumble.packageview.ReusableDiagramViewContextFacet#addToContextMenu(JPopupMenu)
		 */
		public void addToContextMenu(JPopupMenu popup, final DiagramViewFacet diagramView, ToolCoordinatorFacet coordinator)
		{
			popup.add(getSaveEPSToFileItem(diagramView));
		}

    private JMenuItem getSaveEPSToFileItem(final DiagramViewFacet diagramView)
    {
      // for adding operations
      JMenuItem openParentItem = new JMenuItem("Save selection as EPS");
      openParentItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          // copy the selection over to the clipboard
          new CopyAction(
              toolCoordinatorFacet,
              diagramView,
              GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE)).actionPerformed(null);

          // diagram view -- we only need the canvas here
          ZCanvas canvas = new ZCanvas();
          DiagramViewFacet view = new BasicDiagramViewGem(
              GlobalPackageViewRegistry.activeRegistry.getClipboardDiagram(PackageViewRegistryGem.CLIPBOARD_TYPE),
              null,
              canvas,
              new UDimension(1, 1),
              Color.white,
              false).getDiagramViewFacet();

          // copy to clipboard
          ClipboardViewContextGem.askForFileAndSaveAsEPS(view.getCanvas(), view.getDrawnBounds());
        }
      });
      return openParentItem;
    }
    
		/**
		 * @see com.intrinsarc.evolve.guibase.ReusableDiagramViewContextFacet#haveClosed(ReusableDiagramViewFacet)
		 */
		public void haveClosed(ReusableDiagramViewFacet viewFacet)
		{
			GlobalPackageViewRegistry.activeRegistry.haveClosed(viewFacet);
		}

		/**
		 * @see com.intrinsarc.evolve.guibase.ReusableDiagramViewContextFacet#haveFocus(ReusableDiagramViewFacet)
		 */
		public void haveFocus(ReusableDiagramViewFacet viewFacet)
		{
			GlobalPackageViewRegistry.activeRegistry.haveFocus(viewFacet);
		}

		public SmartMenuContributorFacet getSmartMenuContributorFacet()
		{
			return smartMenuContributorFacet;
		}
	}
	
  public static void setFrameNameForDiagram(DiagramViewFacet diagramView, Package fixedPerspective)
  {
    ZTransformGroup backdrop = new ZTransformGroup();
    double widthSoFar = 0;
    ZNode fixed = null;
    if (fixedPerspective != null)
    {
    	fixed = setPerspectiveNameName(diagramView, fixedPerspective);
    	widthSoFar = fixed.getBounds().width;
    }

    Package linked = (Package) diagramView.getDiagram().getLinkedObject(); 
    String stratumName = GlobalSubjectRepository.repository.getFullStratumNames(linked);
    Package pkg = GlobalSubjectRepository.repository.findVisuallyOwningStratum(diagramView.getDiagram(), null);
    
    boolean readonly = diagramView.getDiagram().isReadOnly();

    // find the current package, which represents the diagram name
    String diagramName = getCurrentPackageName(diagramView.getDiagram());
    
    if (pkg != GlobalSubjectRepository.repository.getTopLevelModel())
    {
      ZGroup group = new ZGroup();
      
      ZText text = new ZText(stratumName.replace('\n', ' '));
      ZTransformGroup textGroup = new ZTransformGroup(new ZVisualLeaf(text));
      textGroup.setTranslateX(8 + 24);
      textGroup.setTranslateY(4);
      
      String name = diagramName == null ? "" : diagramName;
      if (readonly)
        name += " (readonly)";
      ZText dText = new ZText(name.replace('\n', ' '));
      dText.setPenPaint(Color.BLUE);
      ZTransformGroup dTextGroup = new ZTransformGroup(new ZVisualLeaf(dText));
      ImageIcon icon = null;
      if (UMLTypes.isDestructiveStratum(pkg))
      	icon = UMLTypes.isRelaxedStratum(pkg) ? RELAXED_DESTRUCTIVE : STRICT_DESTRUCTIVE;
    	else
      	icon = UMLTypes.isRelaxedStratum(pkg) ? RELAXED : STRICT;
      		
      ZImage image = new ZImage(icon.getImage());
      image.setTranslation(8, 4);
      
      double textWidth = textGroup.getBounds().getWidth() + 10;
      dTextGroup.setTranslateX(8 + textWidth + 24);
      dTextGroup.setTranslateY(4);
              
      UBounds bounds = new UBounds(dText.getBounds()).addToExtent(new UDimension(textWidth + 10 + 24, 0));
      double width = bounds.width;
      double height = Math.max(bounds.height, textGroup.getBounds().height);
      
      ZCoordList poly = new ZPolyline();
      poly.setPenPaint(Color.LIGHT_GRAY);

      int zero = fixed != null ? -8 : 0;
      int pushedZero = fixed != null ? -1 : 0;
      poly.add(new UPoint(zero, 0));
      poly.add(new UPoint(width + 4, 0));
      poly.add(new UPoint(width + 12, 8));
      poly.add(new UPoint(width + 12, height + 8));
      poly.add(new UPoint(pushedZero, height + 8));
      if (fixed != null)
      	poly.add(new UPoint(pushedZero, 8));
      poly.setFillPaint(new Color(255, 255, 200));
      
      group.addChild(new ZVisualLeaf(poly));
      group.addChild(textGroup);
      group.addChild(dTextGroup);
      group.addChild(new ZVisualLeaf(image));
      
      ZTransformGroup shift = new ZTransformGroup(group);
      shift.translate(widthSoFar, 0);
      backdrop.addChild(shift);
    }

    if (fixed != null)
    	backdrop.addChild(fixed);        
    backdrop.translate(0, diagramView.getCanvas().getHeight() - backdrop.getBounds().height);
    ZFadeGroup fade = new ZFadeGroup(backdrop);
    fade.setAlpha(0.7);
    diagramView.setBackdrop(fade);
  }
  
  public static ZGroup setPerspectiveNameName(DiagramViewFacet diagramView, Package fixedPerspective)
  {
    String stratumName = GlobalSubjectRepository.repository.getFullStratumNames(fixedPerspective);
    if (fixedPerspective == GlobalSubjectRepository.repository.getTopLevelModel())
    	stratumName = "Top of model";
    
    ZGroup group = new ZGroup();
    
    ZText text = new ZText(stratumName.replace('\n', ' '));
    ZTransformGroup textGroup = new ZTransformGroup(new ZVisualLeaf(text));
    textGroup.setTranslateX(8 + 16 + 8);
    textGroup.setTranslateY(4);
    ZImage image = new ZImage(GLASSES_ICON.getImage());
    image.setTranslateX(8);
    image.setTranslateY(4);
    
    // add in a possible perspective
    UBounds bounds = new UBounds(text.getBounds()).addToExtent(new UDimension(10 + 16 + 16, 0));
    double width = bounds.width;
    double height = Math.max(bounds.height, textGroup.getBounds().getHeight());
    
    ZPolygon poly = new ZPolygon();
    poly.setPenPaint(Color.LIGHT_GRAY);
    poly.add(new UPoint(0, 0));
    poly.add(new UPoint(width + 4, 0));
    poly.add(new UPoint(width + 12, 8));
    poly.add(new UPoint(width + 12, height + 8));
    poly.add(new UPoint(0, height + 8));

    poly.setFillPaint(Color.ORANGE);
    
    group.addChild(new ZVisualLeaf(poly));
    group.addChild(textGroup);
    group.addChild(new ZVisualLeaf(image));
    return group;
  }
  
  private static String getCurrentPackageName(DiagramFacet diagram)
  {
    Package pkg = (Package) diagram.getLinkedObject();
    if (pkg == null)
    {
      System.err.println("Cannot find package for diagram = " + diagram.getDiagramReference()); 
      return "No package";
    }
    
    if (UMLTypes.isStratum(pkg))
      return null;
    
    return pkg.getName();      
  }
}
