// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * The CountedByteOutputStream counts the number of bytes written.
 * 
 * @author Mark Donszelmann
 * @version $Id: CountedByteOutputStream.java,v 1.1 2006-07-03 13:32:51 amcveigh
 *          Exp $
 */
public class CountedByteOutputStream extends FilterOutputStream
{

  private int count;

  public CountedByteOutputStream(OutputStream out)
  {
    super(out);
    count = 0;
  }

  public void write(int b) throws IOException
  {
    out.write(b);
    count++;
  }

  public void write(byte[] b) throws IOException
  {
    out.write(b);
    count += b.length;
  }

  public void write(byte[] b, int offset, int len) throws IOException
  {
    out.write(b, offset, len);
    count += len;
  }

  public int getCount()
  {
    return count;
  }
}
