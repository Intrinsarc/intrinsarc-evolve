package org.freehep.graphicsio.test;

import org.freehep.graphicsio.pdf.*;
import org.freehep.graphicsio.ps.*;
import org.freehep.util.*;

/**
 * @author Mark Donszelmann
 * @version $Id: TestPreviewThumbnail.java,v 1.1 2006-07-03 13:33:11 amcveigh
 *          Exp $
 */
public class TestPreviewThumbnail extends TestAll
{

  public TestPreviewThumbnail(String[] args) throws Exception
  {
    super(args);
    setName("Test Preview and/or Thumbnail");
  }

  public static void main(String[] args) throws Exception
  {
    UserProperties p = new UserProperties();
    p.setProperty(PDFGraphics2D.THUMBNAILS, true);
    p.setProperty(PSGraphics2D.PREVIEW, true);

    new TestPreviewThumbnail(args).runTest(p);
  }
}
