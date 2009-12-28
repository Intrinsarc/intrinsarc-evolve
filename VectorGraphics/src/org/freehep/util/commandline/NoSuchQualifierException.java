// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.util.commandline;

/**
 * Qualifier does not exist.
 * 
 * @author Mark Donszelmann
 * @version $Id: NoSuchQualifierException.java,v 1.1 2006-07-03 13:33:22
 *          amcveigh Exp $
 */
public class NoSuchQualifierException extends CommandLineException
{
  public NoSuchQualifierException(String msg)
  {
    super(msg);
  }

  public NoSuchQualifierException()
  {
    super("No Such Qualifier Exception");
  }
}
