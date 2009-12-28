// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.util.commandline;

/**
 * Too few arguments were provided.
 * 
 * @author Mark Donszelmann
 * @version $Id: MissingArgumentException.java,v 1.1 2006-07-03 13:33:22
 *          amcveigh Exp $
 */
public class MissingArgumentException extends CommandLineException
{
  public MissingArgumentException(String msg)
  {
    super(msg);
  }

  public MissingArgumentException()
  {
    super("Missing Argument Exception");
  }
}
