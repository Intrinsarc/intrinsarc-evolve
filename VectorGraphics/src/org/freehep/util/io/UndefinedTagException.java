// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * Exception for the TaggedOutputStream. Signals that the user tries to write a
 * tag which is not defined at this version or below.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: UndefinedTagException.java,v 1.1 2006-07-03 13:32:51 amcveigh
 *          Exp $
 */
public class UndefinedTagException extends IOException
{

  public UndefinedTagException()
  {
    super();
  }

  public UndefinedTagException(String msg)
  {
    super(msg);
  }

  public UndefinedTagException(int code)
  {
    super("Code: (" + code + ")");
  }
}
