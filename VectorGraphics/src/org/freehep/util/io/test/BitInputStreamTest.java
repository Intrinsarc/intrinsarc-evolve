// Copyright 2002, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;

import org.freehep.util.io.*;

/**
 * HealMe
 * 
 * @author Mark Donszelmann
 * @version $Id: BitInputStreamTest.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public class BitInputStreamTest
{

  public static void main(String args[])
  {
    try
    {
      if (args.length != 1)
      {
        System.err.println("Usage: BitInputStreamTest inputfile");
        System.exit(1);
      }
      BitInputStream bis = new BitInputStream(new FileInputStream(args[0]));

      int n = 0;
      int sum = 0;
      for (int i = 0; i <= 10; i++)
      {
        n = BitOutputStream.minBits(i);
        long x = bis.readUBits(n);
        System.out.println("read: " + x);
        sum += n;
      }
      System.out.println("bits: " + sum + " bytes: " + (int) java.lang.Math.ceil((float) sum / 8f));
      bis.close();
    }
    catch (Exception e)
    {
      System.err.println("HealMe: " + e);
    }
  }
}
