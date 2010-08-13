package com.intrinsarc.evolve.umldiagrams.classifiernode;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.intrinsarc.deltaengine.base.*;
import com.intrinsarc.swing.*;


/**
 * Node allows us to check for recursive containment quickly and easily...
 * @author andrew
 */
class Node
{
	private Node parent;
	private String partName;
	private DEStratum perspective;
	private String componentName;
	private DEComponent component;
	private boolean composite;
	private boolean recursive;
	
	public Node(Node parent, String partName, DEStratum perspective, DEComponent component, boolean composite)
	{
		this.parent = parent;
		this.partName = partName;
		this.componentName = component == null ? "(no type)" : component.getName(perspective);
		this.perspective = perspective;
		this.component = component;
		this.composite = composite;
		recursive = parent != null && parent.includes(component);
	}
	
	public DEStratum getPerspective()
	{
		return perspective;
	}
	
	public String getName()
	{
		return
			componentName + (partName != null && partName.length() != 0 ? ", <b>" + partName : "");
	}
	
	public boolean isRecursive()
	{
		return recursive;
	}
	
	private boolean includes(DEComponent child)
	{
		if (child == component)
			return true;
		if (parent != null)
			return parent.includes(child);
		return false;
	}
	
	public String getPartName()
	{
		return partName;
	}

	public boolean isComposite()
	{
		return composite;
	}
}

class CompositionRenderer extends DefaultTreeCellRenderer
{
	public static final ImageIcon LEAF_ICON = IconLoader.loadIcon("leaf.png");
	public static final ImageIcon COMPOSITE_ICON = IconLoader.loadIcon("composite.png");
	public static final ImageIcon PART_ICON = IconLoader.loadIcon("part.png");
	
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

    Icon icon = user.getPartName() == null ? (user.isComposite() ? COMPOSITE_ICON : LEAF_ICON) : PART_ICON;
    setLeafIcon(icon);
    setOpenIcon(icon);
    setClosedIcon(icon);

    String name = "<html>" + user.getName();
    if (user.getPartName() == null)
    	name += " <span color='gray'>(from perspective " + user.getPerspective().getFullyQualifiedName() + ")";
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

public class CompositionHierarchyViewer
{
	private org.eclipse.uml2.Package pkg;
	private org.eclipse.uml2.Class comp;
	private JPanel panel;

	public CompositionHierarchyViewer(org.eclipse.uml2.Package pkg, org.eclipse.uml2.Class comp, JPanel panel)
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
		DEComponent component = cObj.asComponent();
		
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		final JTree tree = new JTree(root);
		tree.setCellRenderer(new CompositionRenderer());
		populateTree(root, null, perspective, component, null, false);
		
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

	private void populateTree(DefaultMutableTreeNode treeParent, String partName, DEStratum perspective, DEComponent component, Node parentNode, boolean isPart)
	{
		Set<DeltaPair> partPairs =
			component == null ?
					new HashSet<DeltaPair>() :
					component.getDeltas(ConstituentTypeEnum.DELTA_PART).getConstituents(perspective, true);
		Node current = new Node(parentNode, partName, perspective, component, !partPairs.isEmpty());
		
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(current);
		treeParent.add(child);
		
		// expand this out, unless it is recursive
		if (!current.isRecursive())
		{
			for (DeltaPair pair : partPairs)
			{
				DEPart part = pair.getConstituent().asPart();
				populateTree(child, part.getName(), perspective, part.getType(), current, true);
			}
		}
	}
}
