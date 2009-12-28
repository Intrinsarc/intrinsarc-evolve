// Copyright 2001, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;

import org.freehep.util.io.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: RunLengthInputStreamTest.java,v 1.1 2006-07-03 13:33:23
 *          amcveigh Exp $
 */
public class RunLengthInputStreamTest
{

  public static void main(String[] args) throws IOException
  {
    if (args.length != 1)
    {
      System.out.println("Usage: RunLengthInputStreamTest filename");
      System.exit(1);
    }

    RunLengthInputStream in = new RunLengthInputStream(new FileInputStream(args[0]));
    int b = in.read();
    while (b != -1)
    {
      System.out.write(b);
      b = in.read();
    }
    in.close();
    System.out.flush();
  }
}
