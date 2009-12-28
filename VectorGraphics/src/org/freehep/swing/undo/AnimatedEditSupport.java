// Copyright 2003, SLAC, Stanford, U.S.A.
package org.freehep.swing.undo;

import javax.swing.undo.*;

/**
 * @author Mark Donszelmann
 * @version $Id: AnimatedEditSupport.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */

public class AnimatedEditSupport extends UndoableEditSupport
{

  public AnimatedEditSupport()
  {
    super();
  }

  public AnimatedEditSupport(Object source)
  {
    super(source);
  }

  protected CompoundEdit createCompoundEdit()
  {
    // create a "done" AnimatedCompountEdit, since the edits themselves will
    // all be "redone" individually before adding.
    return new AnimatedCompoundEdit(true);
  }
}
