// Copyright 2003, FreeHEP.
package org.freehep.util.io;

import java.io.*;
import java.util.zip.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: CompressableOutputStream.java,v 1.1 2006-07-03 13:32:52
 *          amcveigh Exp $
 */
public class CompressableOutputStream extends FilterOutputStream implements FinishableOutputStream
{
  private boolean compress;
  private DeflaterOutputStream dos;

  public CompressableOutputStream(OutputStream out)
  {
    super(out);
    compress = false;
  }

  public void write(int b) throws IOException
  {
    if (compress)
    {
      dos.write(b);
    }
    else
    {
      out.write(b);
    }
  }

  public void write(byte[] b, int off, int len) throws IOException
  {
    for (int i = 0; i < len; i++)
    {
      write(b[off + i]);
    }
  }

  public void finish() throws IOException
  {
    if (compress)
    {
      dos.finish();
    }
    if (out instanceof FinishableOutputStream)
    {
      ((FinishableOutputStream) out).finish();
    }
  }

  public void close() throws IOException
  {
    if (compress)
    {
      finish();
      dos.close();
    }
    else
    {
      out.close();
    }
  }

  public void startCompressing() throws IOException
  {
    out.flush();
    compress = true;
    dos = new DeflaterOutputStream(out);
  }
}