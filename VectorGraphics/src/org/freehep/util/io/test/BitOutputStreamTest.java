// Copyright 2002, FreeHEP.
package org.freehep.util.io.test;

import java.io.*;

import org.freehep.util.io.*;

/**
 * BitMe
 * 
 * @author Mark Donszelmann
 * @version $Id: BitOutputStreamTest.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public class BitOutputStreamTest
{

  public static void main(String args[])
  {
    try
    {
      if (args.length != 1)
      {
        System.err.println("Usage: BitOutputStreamTest outputfile");
        System.exit(1);
      }
      BitOutputStream bos = new BitOutputStream(new FileOutputStream(args[0]));

      int n = 0;
      int sum = 0;
      for (int i = 0; i <= 10; i++)
      {
        n = BitOutputStream.minBits(i);
        System.out.println("bits needed: " + n);
        bos.writeUBits(i, n);
        sum += n;
      }
      System.out.println("bits: " + sum + " bytes: " + (int) java.lang.Math.ceil((float) sum / 8f));
      bos.close();
    }
    catch (Exception e)
    {
      System.err.println("Bitme: " + e);
    }
  }
}
