// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.font.truetype.test;

import java.awt.*;

import javax.swing.*;

import org.freehep.graphicsio.font.truetype.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: TTFFileTest.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public class TTFFileTest
{

  public static void main(String[] args) throws Exception
  {
    // String fontName = "win1.4/LucidaBrightRegular.ttf";
    String fontName = "linux/LucidaBrightRegular.ttf";
    // String fontName = "linux/LucidaBrightItalic.ttf";
    // String fontName = "windows/LucidaBrightRegular.ttf";
    // String fontName = "windows/LucidaTypewriterRegular.ttf";
    if (args.length == 1)
    {
      fontName = args[0];
    }
    TTFFont ttf = new TTFFile(fontName);

    // ttf.readAll();
    System.out.println("\n---");
    // ttf.show();

    TTFGlyfTable.Glyph glyph = ((TTFGlyfTable) ttf.getTable("glyf")).getGlyph(120);
    System.out.println();
    System.out.println(glyph.toDetailedString());
    // bbox chars: 188, 375, 198, 353
    Rectangle maxCharBounds = ((TTFHeadTable) ttf.getTable("head")).getMaxCharBounds();
    JFrame frame = new JFrame("TTF Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new GlyphPanel(glyph, maxCharBounds));
    frame.pack();
    frame.show();

    ttf.close();
  }

}
