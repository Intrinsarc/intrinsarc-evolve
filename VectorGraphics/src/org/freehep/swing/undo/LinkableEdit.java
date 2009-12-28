// Copyright 2003, SLAC, Stanford, U.S.A.
package org.freehep.swing.undo;

import javax.swing.undo.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: LinkableEdit.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public interface LinkableEdit extends UndoableEdit
{

  public LinkableEdit getNextEdit();
  public void setNextEdit(LinkableEdit edit);

  public LinkableEdit getPreviousEdit();
  public void setPreviousEdit(LinkableEdit edit);

  public LinkableEdit getParent();
  public void setParent(LinkableEdit edit);
}
