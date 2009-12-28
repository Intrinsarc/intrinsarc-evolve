package org.freehep.graphicsio.font.test;

import java.awt.*;
import java.awt.font.*;

import org.freehep.graphicsio.font.*;
import org.freehep.graphicsio.font.encoding.*;

public class TestType1fonts
{

  public static void main(String[] argv)
  {

    try
    {

      FontIncluder fi = new FontEmbedderType1(new FontRenderContext(null, true, true), System.out, false);

      fi.includeFont(new Font("arial", Font.PLAIN, 1000), Lookup.getInstance().getTable("PDFLatin"), "MyFont");

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
