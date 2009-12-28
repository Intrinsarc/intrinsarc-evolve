// Copyright 2002, SLAC, Stanford, U.S.A.
package org.freehep.util.test;

import java.util.*;

import org.freehep.util.*;

/**
 * @author Mark Donszelmann
 * @version $Id: DoubleHashtableTest.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */

public class DoubleHashtableTest
{

  public static void main(String[] args)
  {
    DoubleHashtable table = new DoubleHashtable();

    if (!table.isEmpty())
    {
      System.err.println("Error 0");
    }

    table.put("Donszelmann", "Mark", "CERN");
    table.put("Donszelmann", "Mark", "SLAC");
    table.put("Donszelmann", "Niels", "Knoworries");
    table.put("Johnson", "Tony", "SLAC");
    table.put(null, "Mark", "Family");
    table.put("Donszelmann", null, "Family");
    table.put(null, null, "Family");
    table.put("Perl", "Joseph", null);

    if (table.isEmpty())
    {
      System.err.println("Error 1");
    }

    if (table.get("Donszelmann") == null)
    {
      System.err.println("Error 2");
    }

    if (table.get("Donszelmann", "Mark") == null)
    {
      System.err.println("Error 3");
    }

    if (!table.get("Donszelmann", "Mark").equals("SLAC"))
    {
      System.err.println("Error 4");
    }

    if (table.get("Donszelmann", null) == null)
    {
      System.err.println("Error 5");
    }

    if (!table.get("Donszelmann", null).equals("Family"))
    {
      System.err.println("Error 6");
    }

    if (table.get(null, null) == null)
    {
      System.err.println("Error 7");
    }

    if (table.get(null, "Mark") == null)
    {
      System.err.println("Error 8");
    }

    if (!table.containsKey("Perl", "Joseph"))
    {
      System.out.println("Error 9");
    }

    if (table.get("Perl", "Joseph") != null)
    {
      System.out.println("Error 10");
    }

    table.remove("Johnson", "Tony");
    if (table.get("Johnson", "Tony") != null)
    {
      System.out.println("Error 11");
    }

    int count = 0;
    for (Enumeration e = table.elements(); e.hasMoreElements();)
    {
      Object value = e.nextElement();
      count++;
    }
    if (count != table.size())
    {
      System.out.println("Error 12");
    }

    for (Iterator i = table.iterator(); i.hasNext();)
    {
      String value = (String) i.next();
      if ((value != null) && value.equals("SLAC"))
      {
        i.remove();
      }
    }
    if (table.get("Donszelmann", "Mark") != null)
    {
      System.err.println("Error 13");
    }

    table.clear();
    if (!table.isEmpty())
    {
      System.err.println("Error 14");
    }

    System.err.println("Test ended");
  }
}
