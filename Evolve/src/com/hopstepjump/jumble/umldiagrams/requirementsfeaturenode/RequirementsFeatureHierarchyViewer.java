package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import java.awt.*;
import java.awt.Component;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import org.eclipse.uml2.*;

import com.hopstepjump.deltaengine.base.*;
import com.hopstepjump.swing.*;


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
	public Component getTreeCellRendererComponent(
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
	private org.eclipse.uml2.Type comp;
	private JPanel panel;

	public RequirementsFeatureHierarchyViewer(org.eclipse.uml2.Package pkg, org.eclipse.uml2.Type comp, JPanel panel)
	{
		this.pkg = pkg;
		this.comp = comp;
		this.panel = panel;
	}
	
	public void constructComponent()
	{
		panel.removeAll();
		DEObject pObj = GlobalDeltaEngine.engine.locateObject(pkg);
		DEObject cObj = GlobalDeltaEngine.engine.locateObject(comp);
		if (pObj == null || cObj == null)
			return;
		DEStratum perspective = pObj.asStratum();
		DERequirementsFeature feature = cObj.asRequirementsFeature();
		
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		final JTree tree = new JTree(root);
		tree.setCellRenderer(new CompositionRenderer());
		populateTree(root, perspective, feature, null, false);
		
		panel.add(tree, BorderLayout.CENTER);
		
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
	}

	private void populateTree(DefaultMutableTreeNode treeParent, DEStratum perspective, DERequirementsFeature feature, SubfeatureKindEnum kind, Node parentNode, boolean isPart)
	{
		Set<DeltaPair> partPairs =
			feature == null ?
					new HashSet<DeltaPair>() :
					feature.getDeltas(ConstituentTypeEnum.DELTA_REQUIREMENT_FEATURE_LINK).getConstituents(perspective, true);
		Node current = new Node(parentNode, perspective, feature, kind, !partPairs.isEmpty());
		
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(current);
		treeParent.add(child);
		
		// expand this out, unless it is recursive
		if (!current.isRecursive())
		{
			for (DeltaPair pair : partPairs)
			{
				DERequirementsFeatureLink link = pair.getConstituent().asRequirementsFeatureLink();
				populateTree(child, perspective, link.getSubfeature(), link.getKind(),current, true);
			}
		}
	}
}
