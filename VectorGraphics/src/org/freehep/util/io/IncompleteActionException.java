// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * Exception for the TaggedInputStream. Signals that the inputstream contains
 * more bytes than the stream has read for this action.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: IncompleteActionException.java,v 1.1 2006-07-03 13:32:52
 *          amcveigh Exp $
 */
public class IncompleteActionException extends IOException
{

  private Action action;
  private byte[] rest;

  public IncompleteActionException(Action action, byte[] rest)
  {
    super("Action " + action + " contains " + rest.length + " unread bytes");
    this.action = action;
    this.rest = rest;
  }

  public Action getAction()
  {
    return action;
  }

  public byte[] getBytes()
  {
    return rest;
  }
}
