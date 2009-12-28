package com.hopstepjump.jumble.repositorybrowser;

import javax.swing.event.*;
import javax.swing.tree.*;

/**
 * allow suppression of updates, in addition to existing selection model
 * @author amcveigh
 */
public class SortedTreeSelectionModel extends DefaultTreeSelectionModel
{
  private boolean suppressUpdates = false;
  
  public SortedTreeSelectionModel()
  {
  }

  protected void fireValueChanged(TreeSelectionEvent e)
  {
    if (!suppressUpdates)
      super.fireValueChanged(e);
  }
  
  public void setSuppressUpdates(boolean suppressUpdates)
  {
    this.suppressUpdates = suppressUpdates;
  }
}
