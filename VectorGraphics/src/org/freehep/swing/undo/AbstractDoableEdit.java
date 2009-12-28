// Copyright 2003, SLAC, Stanford, U.S.A.
package org.freehep.swing.undo;

import javax.swing.undo.*;

/**
 * Extension of AbstractUndoableEdit which allows the Edit to be used to execute
 * the action the first time using the redo() method.
 * 
 * Subclasses of this class should override redo() and undo() and as the first
 * call in their methods call super.redo() or super.undo().
 * 
 * @author Mark Donszelmann
 * @version $Id: AbstractDoableEdit.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */

public class AbstractDoableEdit extends AbstractUndoableEdit implements DoableEdit
{

  protected boolean firstTimeDone = false;

  public void redo() throws CannotRedoException
  {
    super.redo();
    firstTimeDone = true;
  }

  public boolean canRedo()
  {
    return (firstTimeDone) ? super.canRedo() : true;
  }

  public boolean canUndo()
  {
    return (firstTimeDone) ? super.canUndo() : false;
  }
}
