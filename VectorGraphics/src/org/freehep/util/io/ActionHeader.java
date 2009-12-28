// Copyright 2001, FreeHEP.
package org.freehep.util.io;

/**
 * Keeps the actionCode and Length of a specific action. To be used in the
 * TaggedInputStream to return the actionCode and Length, and in the
 * TaggedOutputStream to write them.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ActionHeader.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class ActionHeader
{

  int actionCode;
  long length;

  public ActionHeader(int actionCode, long length)
  {
    this.actionCode = actionCode;
    this.length = length;
  }

  public void setAction(int actionCode)
  {
    this.actionCode = actionCode;
  }

  public int getAction()
  {
    return actionCode;
  }

  public void setLength(long length)
  {
    this.length = length;
  }

  public long getLength()
  {
    return length;
  }
}
