// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;
import java.util.zip.*;

/**
 * 
 * IMPORTANT: inherits from InputStream rather than FilterInputStream so that
 * the correct read(byte[], int, int) method is used.
 * 
 * @author Mark Donszelmann
 * @version $Id: DecompressableInputStream.java,v 1.1 2006-07-03 13:32:50
 *          amcveigh Exp $
 */
public class DecompressableInputStream extends InputStream
{

  private boolean decompress;
  private InflaterInputStream iis;
  private InputStream in;

  public DecompressableInputStream(InputStream input)
  {
    super();
    in = input;
    decompress = false;
  }

  public int read() throws IOException
  {
    return (decompress) ? iis.read() : in.read();
  }

  public long skip(long n) throws IOException
  {
    return (decompress) ? iis.skip(n) : in.skip(n);
  }

  public void startDecompressing() throws IOException
  {
    decompress = true;
    iis = new InflaterInputStream(in);
  }
}
