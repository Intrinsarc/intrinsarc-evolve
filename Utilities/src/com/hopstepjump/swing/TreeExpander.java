package com.hopstepjump.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

public class TreeExpander
{

  // If expand is true, expands all nodes in the tree.
  // Otherwise, collapses all nodes in the tree.
  public static void expandEntireTree(JTree tree, boolean expand, TreeSelector selector)
  {
    // Traverse tree from root
    List<TreePath> select = new ArrayList<TreePath>();
    
    TreeExpander.expandAll(tree, null, expand, selector, select);
    
    // possibly select a set of entries
    if (selector != null)
    	tree.setSelectionPaths(select.toArray(new TreePath[0]));
  }

  /**
   * taken from sun bug report, with example code. works well.
   * tweaked so it never collapses the root node.
   * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6359532
   * 
   * @param tree
   * @param parent
   * @param expand
   */
  public static void expandAll(JTree tree, TreePath parent, boolean expand, TreeSelector selector, List<TreePath> select)
  {
  	expandTree(tree, parent, expand, selector, select, -1);
  }
  
  /**
   * NOTE: select holds the list of paths matched by selector
   * @param tree
   * @param parent
   * @param expand
   * @param selector
   * @param select
   * @param depth
   */
  public static void expandTree(JTree tree, TreePath parent, boolean expand, TreeSelector selector, List<TreePath> select, int depth)
  {
  	if (depth == 0)
  		return;
  	if (parent == null)
  		parent = new TreePath(tree.getModel().getRoot());
  	
    // Traverse children
    TreeNode node = (TreeNode) parent.getLastPathComponent();
    if (node.getChildCount() >= 0)
    {
      for (Enumeration e = node.children(); e.hasMoreElements();)
      {
        TreeNode n = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
        
        if (selector != null && selector.selectNode(n) && select != null)
        	select.add(path);
        
        expandTree(tree, path, expand, selector, select, depth - 1);
      }
    }

    // Expansion or collapse must be done bottom-up
    if (!parent.getLastPathComponent().equals(tree.getModel().getRoot()) || expand)
    {
      if (expand)
        tree.expandPath(parent);
      else
        tree.collapsePath(parent);
    }
  }

  /**
   * add expand and collapse menu items to the tree
   * 
   * @param tree
   * @param inlineMenu
   */
  public static void addExpandAndCollapseItems(final JTree tree, JMenuItem[] items)
  {
    items[0].setText("Expand all");
    items[0].addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        TreeExpander.expandAll(tree, tree.getSelectionPath(), true, null, null);
      }
    });

    items[1].setText("Collapse all");
    items[1].addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (tree.getSelectionCount() == 0)
          TreeExpander.expandEntireTree(tree, false, null);
        else
          TreeExpander.expandAll(tree, tree.getSelectionPath(), false, null, null);
      }
    });
  }

	public static void addPopupMenu(final JTree tree, final JMenuItem[] items)
	{
		TreeExpander.addExpandAndCollapseItems(tree, items);
		
		tree.addMouseListener(new MouseAdapter()
		{
		  public void mouseReleased(MouseEvent e)
		  {
		    Point pt = e.getPoint();
		    TreePath path = tree.getPathForLocation(pt.x, pt.y);
		    if (path != null)
		      if (e.getButton() == MouseEvent.BUTTON3 && path.getParentPath() != null)
		      {
		        JPopupMenu popup = new JPopupMenu();
		        tree.setSelectionPath(path);
	
		        for (int lp = 0; lp < items.length; lp++)
		          popup.add(items[lp]);
		        popup.show(tree, pt.x, pt.y);
		      }
		  }
		});
	}
}
