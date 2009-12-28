// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.swf.test;

import hep.aida.*;

import java.io.*;

import org.freehep.graphicsio.swf.*;
import org.freehep.util.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: SWFAnalyze.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class SWFAnalyze
{

  public static void main(String[] args)
  {

    try
    {
      IAnalysisFactory af = IAnalysisFactory.create();
      ITree tree = af.createTreeFactory().create("SWFAnalyze.aida", "xml", false, true);
      ITupleFactory tf = af.createTupleFactory(tree);
      ITuple tuple = tf.create("SWF", "TagType", new String[]{"Tag", "TagSize"}, new Class[]{String.class, int.class});

      FileInputStream fis = new FileInputStream(args[0]);
      SWFInputStream swf = new SWFInputStream(fis);

      long start = System.currentTimeMillis();
      SWFHeader header = swf.readHeader();
      System.out.println(header);

      Tag tag = swf.readTag();
      while (tag != null)
      {
        // System.out.println(tag);
        tuple.fill(0, tag.getName());
        System.out.print(" " + tag.getName());
        tuple.addRow();
        tag = swf.readTag();
        // FIXME add tagSize
      }
      tree.commit();
      System.out.println("Analyzed file in: " + (System.currentTimeMillis() - start) + " ms.");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
