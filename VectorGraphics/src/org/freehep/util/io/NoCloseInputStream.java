// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * The NoCloseInputStream ignores the close so that one can keep reading from
 * the underlying stream.
 * 
 * @author Mark Donszelmann
 * @version $Id: NoCloseInputStream.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class NoCloseInputStream extends BufferedInputStream
{

  public NoCloseInputStream(InputStream stream)
  {
    super(stream);
  }

  public NoCloseInputStream(InputStream stream, int size)
  {
    super(stream, size);
  }

  public void close() throws IOException
  {
  }

  public void realClose() throws IOException
  {
    super.close();
  }


}
