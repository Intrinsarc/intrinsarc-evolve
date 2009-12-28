// Copyright 2001, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;

import org.freehep.util.io.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: RunLengthOutputStreamTest.java,v 1.1 2006-07-03 13:33:23
 *          amcveigh Exp $
 */
public class RunLengthOutputStreamTest
{

  public static void main(String[] args) throws IOException
  {
    if (args.length != 1)
    {
      System.out.println("Usage: RunLengthOutputStreamTest filename");
      System.exit(1);
    }

    RunLengthOutputStream out = new RunLengthOutputStream(System.out);
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
