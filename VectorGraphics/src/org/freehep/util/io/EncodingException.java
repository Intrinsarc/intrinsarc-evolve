// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * Encoding Exception for any of the encoding streams.
 * 
 * @author Mark Donszelmann
 * @version $Id: EncodingException.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class EncodingException extends IOException
{

  public EncodingException(String msg)
  {
    super(msg);
  }
}
