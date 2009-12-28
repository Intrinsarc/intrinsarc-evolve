// Copyright 2003, SLAC, Stanford, U.S.A.
package org.freehep.swing.undo;

import javax.swing.undo.*;

/**
 * Allows for the (compound) processing of UndoableEdits. One can use
 * UndoableEditSupport to implement this.
 * 
 * @author Mark Donszelmann
 * @version $Id: UndoableEditProcessor.java,v 1.1 2006-07-03 13:33:13 amcveigh
 *          Exp $
 */

public interface UndoableEditProcessor
{

  /**
   * Starts a new CompoundEdit and adds subsequent edits to it.
   */
  public void beginUpdate();

  /**
   * Ends the CompoundEdit
   */
  public void endUpdate();

  /**
   * Post (execute) the edit.
   */
  public void postEdit(UndoableEdit edit);
}
