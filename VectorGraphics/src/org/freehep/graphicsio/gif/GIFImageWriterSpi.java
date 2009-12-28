// Copyright 2003, FreeHEP
package org.freehep.graphicsio.gif;

import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.imageio.spi.*;


/**
 * 
 * @version $Id: GIFImageWriterSpi.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class GIFImageWriterSpi extends ImageWriterSpi
{

  public GIFImageWriterSpi()
  {
    super("FreeHEP Java Libraries, http://java.freehep.org/", "1.0", new String[]{"gif", "GIF"}, new String[]{"gif",
        "GIF"}, new String[]{"image/gif", "image/x-gif"}, "org.freehep.graphicsio.gif.GIFImageWriter",
        STANDARD_OUTPUT_TYPE, null, false, null, null, null, null, false, null, null, null, null);
  }

  public String getDescription(Locale locale)
  {
    return "Graphics Interchange Format";
  }

  public ImageWriter createWriterInstance(Object extension) throws IOException
  {
    return new GIFImageWriter(this);
  }

  public boolean canEncodeImage(ImageTypeSpecifier type)
  {
    // FIXME handle # colors
    return true;
  }
}