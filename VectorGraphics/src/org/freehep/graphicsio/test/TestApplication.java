// Copyright 2001 freehep
package org.freehep.graphicsio.test;

import java.awt.*;

/**
 * @author Simon Fischer
 * @version $Id: TestApplication.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class TestApplication
{

  public static void main(String[] args) throws Exception
  {

    // Create a new frame to hold everything.
    TestingFrameMultipage frame = new TestingFrameMultipage("Test Application");

    // Create a new instance of this class and add it to the frame.
    frame.addPage("All", new TestAll(null));
    frame.addPage("Colors", new TestColors(null));
    frame.addPage("Clip", new TestClip(null));
    frame.addPage("Fonts", new TestFonts(null));
    frame.addPage("HTML", new TestHTML(null));
    frame.addPage("Image2D", new TestImage2D(null));
    frame.addPage("Images", new TestImages(null));
    frame.addPage("Labels", new TestLabels(null));
    frame.addPage("Lines", new TestLineStyles(null));
    frame.addPage("Paint", new TestPaint(null));
    frame.addPage("PrintColors", new TestPrintColors(null));
    frame.addPage("Shapes", new TestShapes(null));
    frame.addPage("Symbols", new TestSymbols2D(null));
    frame.addPage("Text", new TestText2D(null));
    frame.addPage("Tagged String", new TestTaggedString(null));
    frame.addPage("Transforms", new TestTransforms(null));
    frame.addPage("Transparency", new TestTransparency(null));

    // Give the frame a size and make it visible.
    frame.pack();
    frame.setSize(new Dimension(800, 800));
    frame.show();
  }
}
