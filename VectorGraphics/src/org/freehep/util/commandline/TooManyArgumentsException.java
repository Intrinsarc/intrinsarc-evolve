// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.util.commandline;

/**
 * Too many arguments were provided.
 * 
 * @author Mark Donszelmann
 * @version $Id: TooManyArgumentsException.java,v 1.1 2006-07-03 13:33:22
 *          amcveigh Exp $
 */
public class TooManyArgumentsException extends CommandLineException
{
  public TooManyArgumentsException(String msg)
  {
    super(msg);
  }

  public TooManyArgumentsException()
  {
    super("Too Many Arguments Exception");
  }
}
