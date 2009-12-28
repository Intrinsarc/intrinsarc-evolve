// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.cgm.test;

import java.io.*;

import org.freehep.graphicsio.cgm.*;
import org.freehep.util.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: CGMDump.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public class CGMDump
{

  public static void main(String[] args)
  {

    try
    {
      FileInputStream fis = new FileInputStream(args[0]);
      CGMInputStream cgm = new CGMInputStream(fis);

      long start = System.currentTimeMillis();

      Tag tag = cgm.readTag();
      while (tag != null)
      {
        System.out.println(tag);
        tag = cgm.readTag();
      }
      System.out.println("Parsed file in: " + (System.currentTimeMillis() - start) + " ms.");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
