// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.emf.test;

import hep.aida.*;

import java.io.*;

import org.freehep.graphicsio.emf.*;
import org.freehep.util.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: EMFAnalyze.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public class EMFAnalyze
{

  public static void main(String[] args)
  {

    try
    {
      IAnalysisFactory af = IAnalysisFactory.create();
      ITree tree = af.createTreeFactory().create("EMFAnalyze.aida", "xml", false, true);
      ITupleFactory tf = af.createTupleFactory(tree);
      ITuple tuple = tf.create("EMF", "TagType", new String[]{"Tag", "TagSize"}, new Class[]{String.class, int.class});

      FileInputStream fis = new FileInputStream(args[0]);
      EMFInputStream emf = new EMFInputStream(fis);

      long start = System.currentTimeMillis();
      EMFHeader header = emf.readHeader();
      System.out.println(header);

      Tag tag = emf.readTag();
      while (tag != null)
      {
        // System.out.println(tag);
        tuple.fill(0, tag.getName());
        tuple.addRow();
        tag = emf.readTag();
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
