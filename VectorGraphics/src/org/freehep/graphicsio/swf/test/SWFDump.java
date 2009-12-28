// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf.test;

import java.io.*;

import org.freehep.graphicsio.swf.*;
import org.freehep.util.io.*;

/**
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SWFDump.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class SWFDump
{

  public static void main(String[] args)
  {

    try
    {
      if (args.length != 1)
      {
        System.err.println("Usage: SWFDump file.swf");
        System.exit(1);
      }

      FileInputStream fis = new FileInputStream(args[0]);
      SWFInputStream swf = new SWFInputStream(fis);

      long start = System.currentTimeMillis();
      SWFHeader header = swf.readHeader();
      System.out.println(header);

      Tag tag = swf.readTag();
      while (tag != null)
      {
        System.out.println(tag);
        tag = swf.readTag();
      }
      // System.out.println(swf.getDictionary());
      System.out.println("Parsed file in: " + (System.currentTimeMillis() - start) + " ms.");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

  }

}
