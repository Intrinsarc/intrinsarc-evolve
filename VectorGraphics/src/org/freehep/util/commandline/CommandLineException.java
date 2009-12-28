// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.util.commandline;

/**
 * Superclass of all Command Line exceptions.
 * 
 * @author Mark Donszelmann
 * @version $Id: CommandLineException.java,v 1.1 2006-07-03 13:33:22 amcveigh
 *          Exp $
 */
public abstract class CommandLineException extends Exception
{
  public CommandLineException(String msg)
  {
    super(msg);
  }
}
