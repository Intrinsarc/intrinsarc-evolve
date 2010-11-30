package com.intrinsarc.evolve.importexport;

import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.beanimporter.*;
import com.intrinsarc.evolve.gui.*;
import com.intrinsarc.evolve.repositorybrowser.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.utility.*;
import com.intrinsarc.repository.*;
import com.intrinsarc.repository.modelmover.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;

public class InspectAndImportStrataAction extends AbstractAction
{
	public static final ImageIcon IMPORT_ICON = IconLoader.loadIcon("import.png");
	public static final ImageIcon BROKEN_LINK_ICON = IconLoader.loadIcon("broken_link.png");
	public static final ImageIcon DETAILS_ICON = IconLoader.loadIcon("details.png");
	public static final ImageIcon FOLDER_ICON = IconLoader.loadIcon("folder.png");
	public static final ImageIcon CANCEL_ICON = IconLoader.loadIcon("icon_cancel.png");
	public static final ImageIcon WARNING_ICON = IconLoader.loadIcon("warning.png");

	private ToolCoordinatorFacet coordinator;
	private PopupMakerFacet popup;
	private JFrame frame;
	private LongRunningTaskProgressMonitor monitor;
	private ISaver saver;

	public InspectAndImportStrataAction(ToolCoordinatorFacet coordinator, PopupMakerFacet popup, JFrame frame, LongRunningTaskProgressMonitor monitor, ISaver saver)
	{
		super("Inspect exported file", IMPORT_ICON);
		this.coordinator = coordinator;
		this.popup = popup;
		this.frame = frame;
		this.monitor = monitor;
		this.saver = saver;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			GlobalSubjectRepository.ignoreUpdates = true;
			internalActionPerformed(e);
		}
		finally
		{
			GlobalSubjectRepository.ignoreUpdates = false;			
		}
	}

	public void internalActionPerformed(ActionEvent e)
	{
		// save the state of the model
		// open the exported file into a different model
  	// ask for the file to import
    final String fileName = RepositoryUtility.chooseFileNameToOpen(
        frame,
        "Select exported file to inspect",
        XMLSubjectRepositoryGem.UML2_EXPORT_FILES, XMLSubjectRepositoryGem.UML2_EXPORT_NO_DOT,
        PreferenceTypeDirectory.recent.getLastVisitedDirectory());
    if (fileName == null)
    	return;
    EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = true;
    XMLSubjectRepositoryGem repositoryGem;
		try
		{
			repositoryGem = XMLSubjectRepositoryGem.openFile(fileName, false, true);
		}
		catch (RepositoryOpeningException ex)
		{
			coordinator.invokeErrorDialog("File cannot be loaded for browsing", ex.getMessage());
			return;
		}
		finally
		{
			EMFOptions.CREATE_LISTS_LAZILY_FOR_GET = false;
		}
		
		// start up a read-only browser with this repository
		SubjectRepositoryFacet export = repositoryGem.getSubjectRepositoryFacet();
		try
		{
			Model exportTop = export.getTopLevelModel();
			repositoryGem.getSubjectRepositoryFacet().getTopLevelModel().setReadOnly(true);
			
			UMLTreeMediator tree = new UMLTreeMediator(export, coordinator, true, false, exportTop, null, new JMenuItem[0], null, null, new HashSet<String>());
			final JSplitPane panel = new JSplitPane();
			panel.setPreferredSize(new Dimension(600, 400));
			final JTree t = tree.getJTree();
			addExpandAndCollapseMenuItems(t);
			panel.setLeftComponent(new JScrollPane(t));
			final JTree contents = new JTree();
			addExpandAndCollapseMenuItems(contents);
			panel.setRightComponent(new JScrollPane(contents));
			
			// listen to mouse events and show the contents
	    tree.addListener(new UMLTreeMediatorListener()
	    {
	      public void selectionChanged(TreeSelectionEvent event)
	      {
	        // remake the inline tree, using the selected item from the top tree
	        DefaultMutableTreeNode selected = RepositoryBrowserGem.findSingleSelectedNode(t);
	  			final DefaultMutableTreeNode root = new DefaultMutableTreeNode();
	  			contents.setModel(new DefaultTreeModel(root));
	        // add all containments
	        if (selected != null)
	        {
	        	Element top = ((UMLTreeUserObject) selected.getUserObject()).getElement();
	        	addAllContained(top, top, root);
	        }
	        contents.setRootVisible(true);
	        contents.expandRow(0);
	        contents.expandRow(1);
	        contents.setRootVisible(false);
	      }
	
				private void addAllContained(Element top, Element element, DefaultMutableTreeNode node)
				{
					if (element instanceof Package && element != top)
						return;
					DefaultMutableTreeNode next = new DefaultMutableTreeNode(ModelMover.getSensibleDetail(element)); 
					node.add(next);
			    // handle any containments
			    for (Object containedObject : element.eClass().getEAllContainments())
			    {
			      EReference contained = (EReference) containedObject;
			      if (ModelMover.containmentCanBeSet(contained))
			      {
			      	if (element.eGet(contained) != null)
			      	{
				        if (contained.isMany())
			        		for (Element e : (List<Element>) element.eGet(contained))
			        			addAllContained(top, e, next);
				        else
				        	addAllContained(top, (Element) element.eGet(contained), next);
			      	}
			      }
			    }    
				}
	    });
	    
	    // open the window
	    JButton imp = new JButton("Import all into model", IMPORT_ICON);
	    JButton cancel = new JButton("Cancel", CANCEL_ICON);
	    JButton buttons[] = new JButton[]{imp, cancel};
	    BeanImporter.conformSize(buttons);
	    panel.setDividerLocation(180);
			int chosen = coordinator.invokeAsDialog(IMPORT_ICON, "Exported file: " + fileName, panel, buttons, 0,
				new Runnable()
				{
					public void run()
					{
						t.setSelectionRow(0);
					}
				});
					
			if (chosen == 0)
				importPackages(export);
		}
		finally
		{
			export.close();
		}
	}		

	private void importPackages(SubjectRepositoryFacet toImport)
	{
		// complain if the current package is readonly
		if (coordinator.getCurrentDiagramView().getDiagram().isReadOnly())
		{
			coordinator.invokeErrorDialog("Import not possible here", "Current diagram is readonly");
			return;
		}
		
		int chosen = saver.askAboutSave("Save before import");
		if (chosen != JOptionPane.OK_OPTION)
		{
			popup.displayPopup(IMPORT_ICON, "Import cancelled",
					"Model must be saved before importing", ScreenProperties.getUndoPopupColor(),
					Color.black, 3000);
			return;
		}
		
		ModelMover importer = new ModelMover(frame, coordinator.getCurrentDiagramView());
		try
		{
			final ImportResults results = importer.importPackages(monitor, toImport);

			boolean noBadReferences = results.getLeftOverOutsideReferences().isEmpty();
			boolean noRemovals = results.getSafelyRemoved().isEmpty() && results.getUnsafelyRemoved().isEmpty();
			boolean noBad = noBadReferences && noRemovals;
			if (!noBad)
			{
				// temporarily restore any replaced packages so we can show them in a browser
				results.deleteReplacedPackages(false);
				popup.clearPopup();
				JPanel panel = new JPanel(new BorderLayout());
				panel.setPreferredSize(new Dimension(600, 400));
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
				DefaultTreeModel model = new DefaultTreeModel(root);
				final JTree tree = new JTree(model);
	      addExpandAndCollapseMenuItems(tree);
				
				if (!noBadReferences)
				{
					DefaultMutableTreeNode badLinks = new DefaultMutableTreeNode(">Broken references from imported to model");
					root.add(badLinks);
					
					for (SavedReference ref : results.getLeftOverOutsideReferences())
					{
						DefaultMutableTreeNode first = new DefaultMutableTreeNode(ref.getDocumentation());
						first.add(new DefaultMutableTreeNode("from "  + ref.getFrom() + " to " + ref.getTo()));
						badLinks.add(first);
					}
				}
				
				if (!noRemovals)
				{
					int good = results.getSafelyRemoved().size();
					int bad = results.getUnsafelyRemoved().size();
					DefaultMutableTreeNode deleted = new DefaultMutableTreeNode("><html>Elements deleted by imported strata [<span color=\"blue\">" + good + " good</span><span color=\"black\">, </span><span color=\"red\">" + bad + " bad</span><span color=\"black\">]</span>");
					root.add(deleted);
					for (Package p : results.getReplacedPackages())
					{
						UMLTreeMediator m = new UMLTreeMediator(GlobalSubjectRepository.repository, coordinator, false, true, p, null, new JMenuItem[0], null, null, new HashSet<String>());
						JTree t = m.getJTree();
						deleted.add((MutableTreeNode) t.getModel().getRoot());
					}
				}
				
				panel.add(new JScrollPane(tree));
				final UMLNodeRendererGem umlRenderer = new UMLNodeRendererGem(null);
				tree.setCellRenderer(new DefaultTreeCellRenderer()
				{
					public Component getTreeCellRendererComponent(
							JTree tree,
							Object value,
							boolean selected,
							boolean expanded,
							boolean leaf,
							int row,
							boolean hasFocus)
					{
			      setLeafIcon(DETAILS_ICON);
						if (((DefaultMutableTreeNode) value).getUserObject() instanceof UMLTreeUserObject)
						{
							// adjust the node for safe or unsafe deleting
							UMLTreeUserObject user = (UMLTreeUserObject)((DefaultMutableTreeNode) value).getUserObject();
							boolean safelyRemoved = results.getSafelyRemoved().contains(user.getElement().getUuid());										
							boolean unsafelyRemoved = results.getUnsafelyRemoved().contains(user.getElement().getUuid());
							if (safelyRemoved)
								user.setType(8);  // blue + bold
							else
							if (unsafelyRemoved)
								user.setType(4);  // red + bold
							else
								user.setType(2);  // disabled

							// uml node
							Component c = umlRenderer.getTreeCellRenderer().getTreeCellRendererComponent(
									tree, value, selected, expanded, leaf, row, hasFocus);
							return c;
						}
						else
						{
							String val = value.toString();
							Icon folder = BROKEN_LINK_ICON;
							if (val.startsWith(">"))
							{
								val = val.substring(1);
								folder = FOLDER_ICON;
							}
				      setOpenIcon(folder);
				      setClosedIcon(folder);										
							return super.getTreeCellRendererComponent(tree, val, selected, expanded, leaf, row, hasFocus);										
						}
					}
				});
				TreeExpander.expandTree(tree, null, true, null, null, 2);
				tree.setRootVisible(false);
				coordinator.invokeAsDialog(WARNING_ICON, "Import warnings", panel, new JButton[]{new JButton("Garbage collect and refresh")}, 0, null);
				results.deleteReplacedPackages(true);
			}
	
			// recreate the diagrams, refresh and garbage collect
			GlobalSubjectRepository.repository.save(frame, false, PreferenceTypeDirectory.recent.getLastVisitedDirectory());
			// refresh from disk now and recreate all diagrams
			saver.makeRefreshAction().actionPerformed(null);
			// garbage collect to fix up the diagrams & remove deleted items
			saver.makeGarbageCollectAction().actionPerformed(null);
			PreferenceTypeDirectory.recent.setLastVisitedDirectory(new File(toImport.getFileName()));
			popup.displayPopup(IMPORT_ICON, "Import successful",
					"Strata imported from " + toImport.getFileName(), ScreenProperties.getUndoPopupColor(),
					Color.black, 3000);
		}
		catch (RepositoryOpeningException ex)
		{
			popup.clearPopup();
			coordinator.invokeErrorDialog("Import failed", ex.getMessage());
		}
		catch (RuntimeException ex)
		{
			popup.clearPopup();
			coordinator.invokeErrorDialog("Import failed", "Unexpected runtime exception:" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private void addExpandAndCollapseMenuItems(final JTree tree)
	{
		JMenuItem[] items = new JMenuItem[]{new JMenuItem(), new JMenuItem()};
		TreeExpander.addExpandAndCollapseItems(tree, items);
		TreeExpander.addPopupMenu(tree, items);
	}
}
