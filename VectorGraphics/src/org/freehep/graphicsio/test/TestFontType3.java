// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.test;

import org.freehep.graphicsio.*;
import org.freehep.graphicsio.pdf.*;
import org.freehep.graphicsio.ps.*;
import org.freehep.util.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: TestFontType3.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class TestFontType3 extends TestTaggedString
{

  public TestFontType3(String[] args) throws Exception
  {
    super(args);
    setName("Test Font Type3");
  }

  public static void main(String[] args) throws Exception
  {
    UserProperties p = new UserProperties();
    p.setProperty(PDFGraphics2D.EMBED_FONTS, true);
    p.setProperty(PDFGraphics2D.EMBED_FONTS_AS, FontConstants.EMBED_FONTS_TYPE3);
    p.setProperty(PSGraphics2D.EMBED_FONTS, true);
    p.setProperty(PSGraphics2D.EMBED_FONTS_AS, FontConstants.EMBED_FONTS_TYPE3);

    new TestFontType3(args).runTest(p);
  }
}
