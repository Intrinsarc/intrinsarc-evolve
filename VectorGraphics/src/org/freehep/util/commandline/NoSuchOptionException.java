// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.util.commandline;

/**
 * Option does not exist, or an ampty option is provided.
 * 
 * @author Mark Donszelmann
 * @version $Id: NoSuchOptionException.java,v 1.1 2006-07-03 13:33:22 amcveigh
 *          Exp $
 */
public class NoSuchOptionException extends CommandLineException
{
  public NoSuchOptionException(String msg)
  {
    super(msg);
  }

  public NoSuchOptionException()
  {
    super("No Such Option Exception");
  }
}
