// Copyright 2001-2003, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import org.freehep.util.images.*;

/**
 * DefineBitsLossless2 TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineBitsLossless2.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class DefineBitsLossless2 extends DefineBitsLossless
{

  public DefineBitsLossless2(int id, Image image, Color bkg, ImageObserver observer)
  {
    this(id, ImageUtilities.createRenderedImage(image, observer, bkg), bkg);
  }

  public DefineBitsLossless2(int id, RenderedImage image, Color bkg)
  {
    this();
    character = id;
    this.image = image;
    this.bkg = bkg;
  }

  public DefineBitsLossless2()
  {
    super(36, 3);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {
    DefineBitsLossless2 tag = new DefineBitsLossless2();
    tag.read(tagID, swf, len, true);
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {

    write(tagID, swf, true);
  }
}
