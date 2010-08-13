package com.intrinsarc.evolve.repositorybrowser;

//SortTreeModel.java
//This class is similar to the DefaultTreeModel, but it keeps
//a node's children in alphabetical order.
//

import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

public class SortedTreeModel extends DefaultTreeModel
{
  private Comparator<DefaultMutableTreeNode> comparator;
  private JTree tree;

  public SortedTreeModel(TreeNode node, Comparator<DefaultMutableTreeNode> c)
  {
    super(node);
    comparator = c;
  }

  public SortedTreeModel(TreeNode node, boolean asksAllowsChildren, Comparator<DefaultMutableTreeNode> c)
  {
    super(node, asksAllowsChildren);
    comparator = c;
  }

  public void insertNodeInto(MutableTreeNode child, MutableTreeNode parent)
  {
    int index = findIndexFor((DefaultMutableTreeNode) child, parent);
    super.insertNodeInto(child, parent, index);
  }

  public void insertNodeInto(MutableTreeNode child, MutableTreeNode par, int i)
  {
    // The index is useless in this model, so just ignore it.
    insertNodeInto(child, par);
  }
  
  public void nodeChanged(TreeNode node)
  {
    MutableTreeNode parent = (MutableTreeNode) node.getParent();
    DefaultMutableTreeNode mnode = (DefaultMutableTreeNode) node;

    if (parent != null)
    {
      // keep the old selection path for reference
      TreePath oldPath = tree == null ? null : tree.getSelectionPath();
      
      // if the node is no longer in order, change it about
      int index = getIndexOfChild(parent, node);
      int wantedIndex = findIndexFor(mnode, parent);
      super.nodeChanged(node);

      if (index != wantedIndex && tree != null)
      {
        // move the node around to keep it in alphabetical order
        boolean reselect = oldPath == null ? false : oldPath.getLastPathComponent() == mnode;
        SortedTreeSelectionModel selection = (SortedTreeSelectionModel) tree.getSelectionModel();
        selection.setSuppressUpdates(true);
        removeNodeFromParent(mnode);
        selection.setSuppressUpdates(false);
        insertNodeInto(mnode, parent);
        if (reselect)
        {
          TreeNode[] newPath = this.getPathToRoot(mnode);
          tree.setSelectionPath(new TreePath(newPath));          
        }
      }
    }
    else
      super.nodeChanged(node);
  }


  /**
   * taken from http://www.java-tips.org/java-se-tips/java.lang/binary-search-implementation-in-java.html
   * and modified to work with a tree
   * @param a
   * @param x
   * @return
   */
  private int findIndexFor(DefaultMutableTreeNode child, MutableTreeNode parent)
  {
      int low = 0;
      int high = parent.getChildCount() - 1;
      int mid;

      while( low <= high )
      {
          mid = ( low + high ) / 2;

          int c = comparator.compare((DefaultMutableTreeNode) parent.getChildAt(mid), child);
          if (c < 0 )
              low = mid + 1;
          else
          if(c > 0 )
              high = mid - 1;
          else
              return mid;
      }

      return high + 1;
  }
  
  public void setTree(JTree tree)
  {
    this.tree = tree;
  }
}
