// Copyright 2001, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;

import org.freehep.util.io.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: ASCII85OutputStreamTest.java,v 1.1 2006-07-03 13:33:23 amcveigh
 *          Exp $
 */
public class ASCII85OutputStreamTest
{

  public static void main(String[] args) throws IOException
  {
    if (args.length != 1)
    {
      System.out.println("Usage: ASCII85OutputStreamTest filename");
      System.exit(1);
    }

    ASCII85OutputStream out = new ASCII85OutputStream(System.out);
    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
    String line = reader.readLine();
    while (line != null)
    {
      out.write(line.getBytes());
      out.write('\r');
      out.write('\n');
      line = reader.readLine();
    }
    reader.close();
    out.close();
  }
}
