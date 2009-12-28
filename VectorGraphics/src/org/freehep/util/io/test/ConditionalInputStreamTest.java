// Copyright 2001, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;
import java.util.*;

import org.freehep.util.io.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: ConditionalInputStreamTest.java,v 1.1 2006-07-03 13:33:23
 *          amcveigh Exp $
 */
public class ConditionalInputStreamTest
{

  private static void test(String file, Properties defines) throws IOException
  {
    System.out.println("========================================");
    ConditionalInputStream in = new ConditionalInputStream(new FileInputStream(file), defines);
    int b = in.read();
    while (b != -1)
    {
      System.out.write(b);
      b = in.read();
    }
    in.close();
    System.out.println("========================================");
    System.out.flush();
  }

  public static void main(String[] args) throws IOException
  {
    if (args.length != 1)
    {
      System.out.println("Usage: ConditionalInputStreamTest filename");
      System.exit(1);
    }

    Properties defines = new Properties();
    System.out.println("Nothing");
    test(args[0], defines);

    System.out.println("FREEHEP");
    defines.setProperty("FREEHEP", "1");
    test(args[0], defines);

    System.out.println("FREEHEP & WIRED");
    defines.setProperty("WIRED", "1");
    test(args[0], defines);
  }
}