package com.intrinsarc.evolve.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import org.eclipse.emf.common.util.*;
import org.eclipse.uml2.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.evolve.repositorybrowser.*;
import com.intrinsarc.swing.*;


/**
 * Node allows us to check for recursive containment quickly and easily...
 * @author andrew
 */
class Node
{
	private Node parent;
	private DEStratum perspective;
	private String featureName;
	private DERequirementsFeature feature;
	private boolean recursive;
	private SubfeatureKindEnum kind;
	
	public Node(Node parent, DEStratum perspective, DERequirementsFeature feature, SubfeatureKindEnum kind)
	{
		this.parent = parent;
		this.featureName = feature == null ? "??" : feature.getName(perspective);
		this.perspective = perspective;
		this.feature = feature;
		recursive = parent != null && parent.includes(feature);
		this.kind = kind;
	}
	
	public DEStratum getPerspective()
	{
		return perspective;
	}
	
	public String getName()
	{
		return featureName;
	}
	
	public boolean isRecursive()
	{
		return recursive;
	}
	
	public DERequirementsFeature getFeature()
	{
		return feature;
	}
	
	private boolean includes(DERequirementsFeature child)
	{
		if (child == feature)
			return true;
		if (parent != null)
			return parent.includes(child);
		return false;
	}	
	
	public SubfeatureKindEnum getKind()
	{
		return kind;
	}
}

class CompositionRenderer extends DefaultTreeCellRenderer
{
	public static final ImageIcon FEATURE_ICON = IconLoader.loadIcon("feature.png");
	public static final ImageIcon MANDATORY_ICON = IconLoader.loadIcon("mandatory-subfeature.png");
	public static final ImageIcon ONE_OF_ICON = IconLoader.loadIcon("one-of-subfeature.png");
	public static final ImageIcon ONE_OR_MORE_ICON = IconLoader.loadIcon("one-or-more-subfeature.png");
	public static final ImageIcon OPTIONAL_ICON = IconLoader.loadIcon("optional-subfeature.png");
	
	@Override
	public java.awt.Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
			int row,
			boolean hasFocus)
	{
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    Node user = (Node) node.getUserObject();
    if (user == null)
    	return super.getTreeCellRendererComponent(tree, "", selected, expanded, leaf, row, hasFocus);

    Icon icon = FEATURE_ICON;
    if (user.getKind() != null)
	    switch (user.getKind())
	    {
	    case MANDATORY:
	    	icon = MANDATORY_ICON;
	    	break;
	    case ONE_OF:
	    	icon = ONE_OF_ICON;
	    	break;
	    case ONE_OR_MORE:
	    	icon = ONE_OR_MORE_ICON;
	    	break;
	    case OPTIONAL:
	    	icon = OPTIONAL_ICON;
	    	break;
	    }
    setLeafIcon(icon);
    setOpenIcon(icon);
    setClosedIcon(icon);

    String name = "<html>" + user.getName();
    java.awt.Component c = super.getTreeCellRendererComponent(
    		tree,
    		name,
    		selected,
    		expanded,
    		leaf,
    		row,
    		hasFocus);
    if (user.isRecursive())
    	c.setForeground(Color.RED);
    
    return c;
	}	
}


/*
 * uses a tree to draw a composition hierarchy of the component from the given perspective
 */

public class RequirementsFeatureHierarchyViewer
{
	private org.eclipse.uml2.Package pkg;
	private RequirementsFeature feature;
	private JPanel panel;
	private int width;

	public RequirementsFeatureHierarchyViewer(org.eclipse.uml2.Package pkg, RequirementsFeature feature, JPanel panel, int width)
	{
		this.pkg = pkg;
		this.feature = getOriginalFeature(feature);
		this.panel = panel;
		this.width = width;
	}
	
	private RequirementsFeature getOriginalFeature(RequirementsFeature feature)
	{
		// get the original
		DERequirementsFeature req = GlobalDeltaEngine.engine.locateObject(feature).asRequirementsFeature();
		return (RequirementsFeature) req.getSubstitutesOrSelf().iterator().next().getRepositoryObject();
	}

	public void constructComponent()
	{
		panel.removeAll();
		DEObject pObj = GlobalDeltaEngine.engine.locateObject(pkg);
		DEObject rObj = GlobalDeltaEngine.engine.locateObject(feature);
		if (pObj == null || rObj == null)
			return;
		DEStratum perspective = pObj.asStratum();
		DERequirementsFeature req = rObj.asRequirementsFeature();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		final JTree tree = new JTree(root);
		tree.setCellRenderer(new CompositionRenderer());
		populateTree(root, perspective, req, null, null);
		
		// set up a splitpane: left = features, right = implementing components
		JSplitPane split = new JSplitPane();
		JScrollPane left = new JScrollPane(tree);
		left.setBackground(Color.WHITE);
		
		JPanel implementers = new JPanel(new BorderLayout());
    final DefaultMutableTreeNode implRoot = new DefaultMutableTreeNode(
        new UMLTreeUserObject(
            null,
            ShortCutType.NONE,
            null,
            "Implementing components",
            false,
            0,
            0));
		
		final JTree list = new JTree(implRoot);
		list.setCellRenderer(new UMLNodeRendererGem(null).getTreeCellRenderer());
		implementers.add(list, BorderLayout.CENTER);
		
		final JScrollPane right = new JScrollPane(implementers);
		right.setBackground(Color.WHITE);
		split.setLeftComponent(left);
		split.setRightComponent(right);
		
		split.setDividerLocation(panel.getWidth() > 0 ? (int) (panel.getWidth() / 2.5) : (int) (width / 2.5));
		panel.add(split, BorderLayout.CENTER);
		
		tree.setRootVisible(false);
    TreeExpander.expandEntireTree(tree, true, null);
		JMenuItem refreshItem = new JMenuItem("Refresh");
		refreshItem.addActionListener(new ActionListener()
		{			
			public void actionPerformed(ActionEvent e)
			{
				constructComponent();
			}
		});
		JMenuItem[] items = new JMenuItem[]{new JMenuItem(), new JMenuItem(), refreshItem};
		TreeExpander.addExpandAndCollapseItems(tree, items);
		TreeExpander.addPopupMenu(tree, items);
		
		// listen to the focus, so we can find implementing components
		tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent e)
			{
				// get the selected item
				implRoot.removeAllChildren();
				DefaultMutableTreeNode sel = findSingleSelectedNode(tree);
				if (sel != null)
				{
					Node node = (Node) sel.getUserObject();
					RequirementsFeature f = (RequirementsFeature) node.getFeature().getRepositoryObject();
					
					for (Object obj : f.getReverseDependencies())
					{
						Dependency dep = (Dependency) obj;
						if (!dep.isThisDeleted() && dep.isTrace())
						{
							// add to the list
							EList ls = dep.getClients();
							if (!ls.isEmpty())
							{
								for (Object lso : ls)
								{
									Element elem = (Element) lso;
									if (!elem.isThisDeleted())
									{
										DEElement comp = GlobalDeltaEngine.engine.locateObject(lso).asElement();
										if (comp != null)
										{
									    DefaultMutableTreeNode tn = new DefaultMutableTreeNode(
									        new UMLTreeUserObject(
									            (Element) comp.getRepositoryObject(),
									            ShortCutType.NONE,
									            null,
									            comp.getFullyQualifiedName(),
									            false,
									            0,
									            0));
											
											implRoot.add(tn);
										}
									}
								}
							}
						}
					}
			    TreeExpander.expandEntireTree(list, true, null);
				}
				
				((DefaultTreeModel) list.getModel()).reload();
				right.repaint();
			}
		});
		
		panel.revalidate();		
	}
	
  private DefaultMutableTreeNode findSingleSelectedNode(JTree tree)
  {
    TreePath path = tree.getSelectionModel().getSelectionPath();
    if (path != null && tree.getSelectionCount() == 1)
      return (DefaultMutableTreeNode) path.getLastPathComponent();
    return null;
  }

	private void populateTree(DefaultMutableTreeNode treeParent, DEStratum perspective, DERequirementsFeature feature, SubfeatureKindEnum kind, Node parentNode)
	{
		Set<DeltaPair> partPairs =
			feature == null ?
					new HashSet<DeltaPair>() :
					feature.getDeltas(ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK).getConstituents(perspective, true);
		Node current = new Node(parentNode, perspective, feature, kind);
		
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(current);
		treeParent.add(child);
		
		// expand this out, unless it is recursive
		if (!current.isRecursive())
		{
			for (SubfeatureKindEnum k : SubfeatureKindEnum.values())
			{
				for (DeltaPair pair : partPairs)
				{
					DERequirementsFeatureLink link = pair.getConstituent().asRequirementsFeatureLink();
					if (k == link.getKind())
						populateTree(child, perspective, link.getSubfeature(), link.getKind(), current);
				}
			}
		}
	}
}
