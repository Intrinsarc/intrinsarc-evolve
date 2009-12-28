// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * The FinishableOutputStream allows a generic way of calling finish on an
 * output stream without closing it.
 * 
 * @author Mark Donszelmann
 * @version $Id: FinishableOutputStream.java,v 1.1 2006-07-03 13:32:51 amcveigh
 *          Exp $
 */
public interface FinishableOutputStream
{
  public void finish() throws IOException;
}
