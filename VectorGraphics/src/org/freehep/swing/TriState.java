// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.swing;



/**
 * @author Mark Donszelmann
 * @version $Id: TriState.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public interface TriState
{

  public int getTriState();

  public void setTriState(int state);
  public void setTriState(boolean state);
}
