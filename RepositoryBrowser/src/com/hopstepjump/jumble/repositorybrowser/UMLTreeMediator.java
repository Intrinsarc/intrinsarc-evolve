package com.hopstepjump.jumble.repositorybrowser;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;
import org.eclipse.uml2.Package;

import com.hopstepjump.deltaengine.errorchecking.*;
import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;
import com.hopstepjump.swing.enhanced.*;


/**
 * the tree mediator populates and manages the tree, in either outline or detail mode...
 * @author amcveigh, Sept/Oct 2006
 */
public class UMLTreeMediator
{
  private JTree tree;
  private SortedTreeModel model;
  private Element start;
  private Namespace parentNamespace;
  private TreeCellRenderer renderer;
  private boolean outlineMode;
  private UMLTreeMediatorListener listener;
  private Set<String> exclude;
  private JScrollPane scroller;
  private boolean packageExpandMode;
  
  public UMLTreeMediator(
      final SubjectRepositoryFacet repository,
      final ToolCoordinatorFacet coordinator,
      final boolean outlineMode,
      final boolean packageExpandMode,
      Element start,
      final JPopupMenu parentPopup,
      final JMenuItem[] items,
      final PackageDiagramOpenerFacet diagramOpener,
      ErrorRegister errors,
      Set<String> exclude)
  {
    this.start = start;
    this.outlineMode = outlineMode;
    this.packageExpandMode = packageExpandMode;
    this.exclude = exclude;
    
    renderer = new UMLNodeRendererGem(errors).getTreeCellRenderer();

    // sync with the model initially
    makeInitialModel();
    tree = new JTree(model);
    
    ToolTipManager.sharedInstance().registerComponent(tree);
    tree.setSelectionModel(new SortedTreeSelectionModel());
    model.setTree(tree);
    
    // listen for selections
    tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener()
    {
      public void valueChanged(TreeSelectionEvent e)
      {
        if (listener != null)
          listener.selectionChanged(e);
      }      
    });
    tree.setCellRenderer(renderer);
    
    // add a popup menu to the tree
    tree.addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent e)
      {
        showPopup(e);
      }

      private void showPopup(MouseEvent e)
      {
        Point pt = e.getPoint();
        if (e.getButton() == MouseEvent.BUTTON2)
        {
          TreePath path = tree.getPathForLocation(pt.x, pt.y);
          if (path != null)
          {
            tree.setSelectionPath(path);          
            // if this is a package, jump to the diagram
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            UMLTreeUserObject object = (UMLTreeUserObject) node.getUserObject();
            Element element = object.getElement();
            if (element instanceof Package && diagramOpener != null)
              diagramOpener.openDiagramForPackage((Package) element);              
          } 
        }
          
        if (e.getButton() == MouseEvent.BUTTON3)
        {
          TreePath path = tree.getPathForLocation(pt.x, pt.y);
          if (path != null)
          {
            JPopupMenu popup = new JPopupMenu();
          	DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            tree.setSelectionPath(path);
            UMLTreeUserObject user = (UMLTreeUserObject) node.getUserObject();
            
            if (!repository.isReadOnly(user.getElement()))
            {
              popup = new UMLTreeMenuCreator().createMenu(
              		coordinator,
              		node);
              
              new StereotypeChanger().createMenu(
              		popup,
              		coordinator, 
              		user.getElement());
            }
  
            // add the extra items
            Utilities.addSeparator(popup);
            for (int lp = 0; lp < items.length; lp++)
              popup.add(items[lp]);

            popup.show(tree, pt.x, pt.y);
          }
          else
          if (parentPopup != null)
          {
            // invoke the popup for the parent
            parentPopup.show(tree, pt.x, pt.y);
          }
        }
      }
    });
  }
  
  public void setScroller(JScrollPane scroller)
  {
  	this.scroller = scroller;
  }
  
  /**
   * recreate the tree from a root node
   * @param start
   */
  public void recreate(Element start)
  {
    this.start = start;
    makeInitialModel();
    tree.setModel(model);
    model.setTree(tree);
  }
  
  public JTree getJTree()
  {
    return tree;
  }
  
  private DefaultMutableTreeNode getRoot()
  {
    return (DefaultMutableTreeNode) model.getRoot();
  }

  private String getName(Element element)
  {
    if (element instanceof NamedElement)
      return ((NamedElement) element).getName();
    return "";
  }
  
  private void makeInitialModel()
  {
    model = new SortedTreeModel(null, new Comparator<DefaultMutableTreeNode>()
    {
      public int compare(DefaultMutableTreeNode n1, DefaultMutableTreeNode n2)
      {
        UMLTreeUserObject t1 = (UMLTreeUserObject) n1.getUserObject(); 
        UMLTreeUserObject t2 = (UMLTreeUserObject) n2.getUserObject(); 
        Element e1 = t1.getElement();
        Element e2 = t2.getElement();
        
        // either node is an up shortcut, it loses
        if (t1.getShortCutType() == ShortCutType.UP && t2.getShortCutType() != ShortCutType.UP)
          return 1;
        if (t2.getShortCutType() == ShortCutType.UP && t1.getShortCutType() != ShortCutType.UP)
          return -1;
        // either node is an up shortcut and the other side has none, it loses
        if (t1.getShortCutType() == ShortCutType.NORMAL && t2.getShortCutType() == ShortCutType.NONE)
          return 1;
        if (t2.getShortCutType() == ShortCutType.NORMAL && t1.getShortCutType() == ShortCutType.NONE)
          return -1;
        
        // if the nodes have different types, base the return on a comparison of the types
        String c1 = e1.eClass().getName();
        String c2 = e2.eClass().getName();
        if (!c1.equals(c2))
          return c1.compareTo(c2);
        
        return getName(e1).compareTo(getName(e2));
      }
    });
    TreeNode startNode = makeNode(null, null, null, start);
    model.setRoot(startNode);

    
    // if we are not in outline mode and we are looking at a package, add shortcut to the parent package
    if (!outlineMode && !packageExpandMode && start != null && start instanceof Package)
    {
      parentNamespace = ((Package) start).getParentPackage();
      if (parentNamespace != null)
        makeNode(getRoot(), null, null, parentNamespace);
    }
  }
  
  private TreeNode makeNode(DefaultMutableTreeNode parent, Set<DefaultMutableTreeNode> siblingNodes, EStructuralFeature feature, Element element)
  {
    // extra check to ensure we are not adding a non-package item to an outline display 
    if (outlineMode && !(element instanceof Package))
      return null;
    
    Package asPackage = null;
    if (element instanceof Package)
      asPackage = (Package) element;
    
    // display as a short cut?
    boolean showAsShortCut = !outlineMode && !packageExpandMode && asPackage != null && element != start;
    ShortCutType shortCut = ShortCutType.NONE;
    if (showAsShortCut)
    {
      if (element == parentNamespace)
        shortCut = ShortCutType.UP;
      else
        shortCut = ShortCutType.NORMAL;
    }
    
    // find a possible node in the siblings
    DefaultMutableTreeNode elementNode = null;
    if (siblingNodes != null)
    {
	    for (DefaultMutableTreeNode siblingNode : siblingNodes)
	    {
	    	UMLTreeUserObject userObject = (UMLTreeUserObject) siblingNode.getUserObject();
	    	if (userObject.getElement() == element)
	    	{
	    		elementNode = siblingNode;
	    		siblingNodes.remove(siblingNode);
	    		break;
	    	}
	    }
    }
    
  	// calculate the stereotype hash
  	int stereoHash = StereotypeUtilities.calculateRawStereotypeHash(element);

  	// if we haven't got an existing node, create and insert into the tree
    if (elementNode == null)
    {
    	elementNode = new DefaultMutableTreeNode(
    			new UMLTreeUserObject(element, shortCut, feature, UMLNodeText.getNodeText(element), false, 0, stereoHash),
    			true);
    	if (parent != null)
    		model.insertNodeInto(elementNode, parent, parent.getChildCount());
    }
    
    // if the text doesn't match up, the adjust
    String newText = UMLNodeText.getNodeText(element);
    UMLTreeUserObject userObject = (UMLTreeUserObject) elementNode.getUserObject();
    String oldText = userObject.getText();

    if (!oldText.equals(newText) || userObject.getStereoHash() != stereoHash)
    {
    	userObject.setText(newText);
    	userObject.setStereoHash(stereoHash);
    	model.nodeChanged(elementNode);
    }
    
    if (!showAsShortCut && element != null)
    {
    	Set<DefaultMutableTreeNode> newSiblingNodes = new HashSet<DefaultMutableTreeNode>();
      for (int lp = 0; lp < elementNode.getChildCount(); lp++)
      {
      	DefaultMutableTreeNode childNode =  (DefaultMutableTreeNode) elementNode.getChildAt(lp);
      	UMLTreeUserObject childUserObject = (UMLTreeUserObject) childNode.getUserObject();
      	// only add the sibling if it is a real node, and not a short cut...
      	if (childUserObject.getShortCutType() != ShortCutType.UP)
      		newSiblingNodes.add(childNode);
      }
    	
      // don't handle normal element in outline mode
      for (Object obj : element.eClass().getEAllContainments())
      {
        EStructuralFeature ref = (EStructuralFeature) obj;
        // ignore derived features
        if (exclude.contains(ref.getName()))
          continue;
  
        // for outline mode we only want packages etc...
        if (outlineMode && !ref.getName().equals("childPackages"))
          continue;

        if (!ref.isDerived() && !ref.getEType().getName().contains("_"))
        {
          if (ref.getUpperBound() == 1)
          {
            Object value = element.eGet(ref);
            if (value instanceof Element)
            {
	            Element child = (Element) value;
	            if (!child.isThisDeleted())
	              makeNode(elementNode, newSiblingNodes, ref, child);
            }
          }
          else
          {
            EList list = (EList) element.eGet(ref);
            if (list != null)
              for (Object childObj : list)
              {
              	if (childObj instanceof Element)
              	{
	                Element child = (Element) childObj;
	                if (!child.isThisDeleted())
	                  makeNode(elementNode, newSiblingNodes, ref, child);
              	}
              }
          }
        }        
      }
      // any nodes left should be removed
      for (DefaultMutableTreeNode deletedNode : newSiblingNodes)
    		model.removeNodeFromParent(deletedNode);
    }
    
    return elementNode;
  }

	public void expandTopElement()
  {
    tree.expandRow(0);
  }
  
  public void selectFirstRow()
  {
  	selectAndScrollTo(new TreePath(getRoot()));
  }

  public void addListener(UMLTreeMediatorListener listener)
  {
    this.listener = listener;
  }

  public void selectElement(Element element)
  {
  	// iterate over all the tree elements, looking for a match -- then select it
    DefaultMutableTreeNode match = findNodeForElement(getRoot(), element);
    TreeSelectionModel sel = tree.getSelectionModel();
    if (match == null)
      sel.setSelectionPath(null);
    else
    	selectAndScrollTo(new TreePath(model.getPathToRoot(match)));
  }
  
  private void selectAndScrollTo(TreePath path)
  {
  	tree.getSelectionModel().setSelectionPath(path);  
	  // ensure we can see any selected element on the screen
	  if (scroller != null)
	  {
	    final Rectangle bounds = tree.getPathBounds(path);
	    if (bounds != null)
	    {
	    	SwingUtilities.invokeLater(new Runnable()
	    	{
					public void run()
					{
						int height = tree.getBounds().height;
						JViewport view = scroller.getViewport();
						int vHeight = view.getBounds().height;
						int y = bounds.y;
						if (y + vHeight > height)
							y = Math.max(0, height - vHeight);
							
			    	scroller.getViewport().setViewPosition(new Point(0, y));
					}	    	
	    	});
	    }
	  }
  }
  
  private DefaultMutableTreeNode findNodeForElement(DefaultMutableTreeNode start, Element element)
  {
    if (start == null)
      return null;
    UMLTreeUserObject userObject = (UMLTreeUserObject) start.getUserObject();
    if (userObject.getElement() == element)
      return start;
    
    // otherwise, look at children
    for (int lp = 0; lp < start.getChildCount(); lp++)
    {
      DefaultMutableTreeNode child = (DefaultMutableTreeNode) start.getChildAt(lp);
      DefaultMutableTreeNode match = findNodeForElement(child, element);
      if (match != null)
        return match;
    }
    return null;
  }

  public void clear()
  {
    tree.setModel(null);
    tree.revalidate();
  }

	public void updateTree()
	{
		// synch up the tree with the latest model
		Set<DefaultMutableTreeNode> rootNodes = new HashSet<DefaultMutableTreeNode>();
		rootNodes.add((DefaultMutableTreeNode) model.getRoot());
		makeNode(null, rootNodes, null, start);
	}
}
