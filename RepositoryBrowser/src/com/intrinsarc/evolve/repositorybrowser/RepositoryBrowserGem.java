/*
 * Created on 25-May-2003 by Andrew McVeigh
 */
package com.intrinsarc.evolve.repositorybrowser;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.intrinsarc.deltaengine.errorchecking.*;
import com.intrinsarc.gem.*;
import com.intrinsarc.idraw.environment.*;
import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.idraw.foundation.persistence.*;
import com.intrinsarc.repositorybase.*;
import com.intrinsarc.swing.*;


public class RepositoryBrowserGem implements Gem
{
  public static Preference EDITABLE_UUIDS = new Preference("Advanced", "Editable UUIDs", new PersistentProperty(false));

  private RepositoryBrowserFacet browserFacet = new RepositoryBrowserFacetImpl();
  private SubjectRepositoryFacet repository;
  private SubjectRepositoryListenerFacet listener = new SubjectRepositoryListenerFacetImpl();
  private JTree outlineTree;
  private UMLTreeMediator outlineMediator;
  private JTree inlineTree;
  private UMLTreeMediator inlineMediator;
  private JSplitPane treeSplitter;
  private JSplitPane detailSplitter;
  private JPanel propertyPanel;
  private DefaultMutableTreeNode currentInlineSelection;
  private UMLDetailMediator details;
  private ToolCoordinatorFacet coordinator;
  private JCheckBoxMenuItem reusableItem;
  private RepositoryBrowserListenerFacet browserListener;
  private PackageDiagramOpenerFacet diagramOpener;
  private int previousTab;
  private ErrorRegister errors;

  public RepositoryBrowserGem(
  		ToolCoordinatorFacet coordinator,
  		SubjectRepositoryFacet repository,
      PackageDiagramOpenerFacet diagramOpener,
      ErrorRegister errors)
  {
    this.coordinator = coordinator;
    this.repository = repository;
    this.diagramOpener = diagramOpener;
    this.errors = errors;
    repository.addRepositoryListener(listener);
  }

  public RepositoryBrowserFacet getRepositoryBrowserFacet()
  {
    return browserFacet;
  }
  
  public static void registerPreferenceSlots()
  {
    GlobalPreferences.preferences.addPreferenceSlot(
        EDITABLE_UUIDS,
        new PreferenceTypeBoolean(),
        "Determines if the model browser allows UUID editing.  Only use UUID editing if you know exactly what you are doing!");
  }
  
  private class SubjectRepositoryListenerFacetImpl implements SubjectRepositoryListenerFacet
  {
    public void sendChanges()
    {
      // check all elements in both lists for deleted items and remove them
      outlineMediator.updateTree();
      inlineMediator.updateTree();
      if (details != null)
        details.investigateChange();
    }
  }

  public static DefaultMutableTreeNode findSingleSelectedNode(JTree tree)
  {
    TreePath path = tree.getSelectionModel().getSelectionPath();
    if (path != null && tree.getSelectionCount() == 1)
      return (DefaultMutableTreeNode) path.getLastPathComponent();
    return null;
  }

  private class RepositoryBrowserFacetImpl implements RepositoryBrowserFacet
  {
    public JComponent getVisualComponent()
    {
      // make the outline tree
      JPopupMenu menu = new JPopupMenu();
      reusableItem = new JCheckBoxMenuItem("Reusable", true);
      menu.add(reusableItem);
      reusableItem.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          browserListener.haveSetReusable(reusableItem.isSelected());
        }
      });
      JMenuItem outlineItems[] = new JMenuItem[]{new JMenuItem(), new JMenuItem()};
      
      // exclude some things from the tree, as they are aggregates but not marked as derived
      Set<String> exclude = new HashSet<String>();
      outlineMediator = new UMLTreeMediator(
          repository,
          coordinator,
          true,
          false,
          repository.getTopLevelModel(),
          menu,
          outlineItems,
          diagramOpener,
          errors,
          exclude);
      outlineTree = outlineMediator.getJTree();

      // add the expand and collapse items
      TreeExpander.addExpandAndCollapseItems(outlineTree, outlineItems);

      outlineTree.setShowsRootHandles(true);
      outlineTree.setScrollsOnExpand(true);

      // make the inline tree
      JMenuItem inlineItems[] = new JMenuItem[]{new JMenuItem(), new JMenuItem()};
      inlineMediator = new UMLTreeMediator(
          repository,
          coordinator,
          false,
          false,
          null,
          null,
          inlineItems,
          diagramOpener,
          errors,
          exclude);
      inlineTree = inlineMediator.getJTree();
      inlineTree.setShowsRootHandles(true);
      inlineTree.setScrollsOnExpand(true);

      // add the expand and collapse items
      TreeExpander.addExpandAndCollapseItems(inlineTree, inlineItems);

      // set up the scroll panes etc...
      JScrollPane outlineScroller = new JScrollPane(outlineTree);
      outlineMediator.setScroller(outlineScroller);
      JScrollPane inlineScroller = new JScrollPane(inlineTree);
      inlineMediator.setScroller(inlineScroller);
      treeSplitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outlineScroller, inlineScroller);
      treeSplitter.setOneTouchExpandable(false);
      treeSplitter.setBorder(null);
      propertyPanel = new JPanel(new BorderLayout());
      detailSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeSplitter, propertyPanel);
      detailSplitter.setOneTouchExpandable(false);
      detailSplitter.setBorder(null);

      // set up the listeners
      outlineMediator.addListener(new UMLTreeMediatorListener()
      {
        public void selectionChanged(TreeSelectionEvent event)
        {
          // reenable the trees
          outlineTree.setEnabled(true);
          inlineTree.setEnabled(true);

          // remake the inline tree, using the selected item from the top tree
          DefaultMutableTreeNode selected = findSingleSelectedNode(outlineTree);
          if (selected != null)
          {
            Element inlineStart = ((UMLTreeUserObject) selected.getUserObject()).getElement();
            inlineMediator.recreate(inlineStart);
            inlineMediator.selectFirstRow();
            browserListener.outerSelectionChanged(inlineStart);
          }
          else
          {
            inlineMediator.clear();
            browserListener.outerSelectionChanged(null);
          }
        }
      });
      inlineMediator.addListener(new UMLTreeMediatorListener()
      {
        public void selectionChanged(TreeSelectionEvent event)
        {
          // reenable the trees
          outlineTree.setEnabled(true);
          inlineTree.setEnabled(true);

          DefaultMutableTreeNode selected = findSingleSelectedNode(inlineTree);
          if (selected != null)
          {
            if (currentInlineSelection != selected)
            {
              UMLTreeUserObject userObject = (UMLTreeUserObject) selected.getUserObject();

              // (a) if this is a shortcut, repoint the outline tree
              if (userObject.getShortCutType() != ShortCutType.NONE)
                outlineMediator.selectElement(userObject.getElement());
              else
              // (b) display the property sheet for the selected element
              {
                propertyPanel.removeAll();
                Element detail = ((UMLTreeUserObject) selected.getUserObject()).getElement();
                if (details != null)
                  previousTab = details.getSelectedTabIndex();
                details = new UMLDetailMediator(
                    outlineTree,
                    inlineTree,
                    coordinator,
                    detail,
                    false,
                    errors);
                details.connectRepositoryBrowserListenerFacet(browserListener);
                propertyPanel.add(details.makeVisualComponent(previousTab));
                propertyPanel.revalidate();
                currentInlineSelection = selected;
              }
            }
          }
          else
          {
            // clear the property panel
            currentInlineSelection = null;
            if (details != null)
              previousTab = details.getSelectedTabIndex();
            details = null;
            propertyPanel.removeAll();
            propertyPanel.repaint();
          }
        }
      });

      return detailSplitter;
    }
    
    public void selectFirstElements()
    {
      outlineMediator.expandTopElement();
      outlineMediator.selectFirstRow();    	
    }

    private void populatePropertyViewer(String type, String uuid)
    {
      propertyPanel.removeAll();
      propertyPanel.validate();
      propertyPanel.repaint();
    }

    private void clearPropertyViewer()
    {
      propertyPanel.removeAll();
      propertyPanel.validate();
      propertyPanel.repaint();
    }

    /*
     * @see com.hopstepjump.jumble.umltreebrowsers.SubjectRepositoryBrowserFacet#setDetailDivider(double)
     */
    public void setDetailDivider(int size)
    {
      detailSplitter.setDividerLocation(size);
    }

    /*
     * @see com.hopstepjump.jumble.umltreebrowsers.SubjectRepositoryBrowserFacet#setTreeDivider(double)
     */
    public void setTreeDivider(int size)
    {
      treeSplitter.setDividerLocation(size);
    }

    /*
     * @see com.hopstepjump.jumble.umltreebrowsers.SubjectRepositoryBrowserFacet#haveClosed()
     */
    public void haveClosed()
    {
      repository.removeRepositoryListener(listener);
    }

    /*
     * @see com.hopstepjump.jumble.umltreebrowsers.SubjectRepositoryBrowserFacet#haveAlteredAttributes()
     */
    public void haveAlteredAttributes()
    {
      inlineTree.setEnabled(false);
      outlineTree.setEnabled(false);
    }

    /*
     * @see com.hopstepjump.jumble.umltreebrowsers.SubjectRepositoryBrowserFacet#havePristineAttributes()
     */
    public void havePristineAttributes()
    {
      inlineTree.setEnabled(true);
      outlineTree.setEnabled(true);
    }

    public void forceReusable(boolean reusable)
    {
      reusableItem.setSelected(reusable);
      browserListener.haveSetReusable(reusable);
    }

    public boolean isReusable()
    {
      return reusableItem.isSelected();
    }

    public void browseTo(Object subject)
    {
      // if the subject is null, simple choose the top level package and default
      // to the start
      if (subject == null)
        outlineMediator.selectFirstRow();
      else
      {
        // get the parent
        Element element = (Element) subject;
        Element parent = repository.findOwningElement(element, Package.class);

        // if we have no parent, we are the top level model
        if (parent == null)
        {
          outlineMediator.selectFirstRow();
          inlineMediator.selectFirstRow();
        }
        else
        {
          outlineMediator.selectElement(parent);
          inlineMediator.selectElement(element);
        }
      }
    }

    public void refresh()
    {
      // redraw
      inlineTree.repaint();
      outlineTree.repaint();
      listener.sendChanges();
    }
  }

  public void connectRepositoryBrowserListenerFacet(RepositoryBrowserListenerFacet browserListener)
  {
    this.browserListener = browserListener;
  }  
}
