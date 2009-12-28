package org.freehep.graphicsio.font.encoding.test;

import org.freehep.graphicsio.font.encoding.*;

/**
 * Test class to test the generated Encoding Tables.
 * 
 * @author Sami Lama
 * @version $Id: TestLookup.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class TestLookup
{

  public static void main(String[] args)
  {
    Lookup lookAtIt = null;
    Lookup lookAtThis = null;
    CharTable aTable = null;
    lookAtIt = Lookup.getInstance();
    aTable = lookAtIt.getTable("PDFLatin");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("STDLatin");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("MACLatin");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("WINLatin");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("ISOLatin");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("Symbol");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("ZapfDingbats");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    aTable = lookAtIt.getTable("Expert");
    for (int i = 0; i < 256; i++)
    {
      System.out.println(aTable.toName(i));
    }
    System.out.println(aTable.toEncoding("Aacutesmall"));
    System.out.println(lookAtIt.toName('\uF8E8'));

  }
}