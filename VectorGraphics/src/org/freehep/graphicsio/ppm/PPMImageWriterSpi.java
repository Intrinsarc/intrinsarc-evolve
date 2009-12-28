// Copyright 2003, FreeHEP
package org.freehep.graphicsio.ppm;

import java.io.*;
import java.util.*;

import javax.imageio.*;
import javax.imageio.spi.*;


/**
 * 
 * @version $Id: PPMImageWriterSpi.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class PPMImageWriterSpi extends ImageWriterSpi
{

  public PPMImageWriterSpi()
  {
    super("FreeHEP Java Libraries, http://java.freehep.org/", "1.0", new String[]{"ppm", "PPM"}, new String[]{"ppm",
        "PPM"}, new String[]{"image/x-portable-pixmap", "image/x-portable-pixmap"},
        "org.freehep.graphicsio.ppm.PPMImageWriter", STANDARD_OUTPUT_TYPE, null, false, null, null, null, null, false,
        null, null, null, null);
  }

  public String getDescription(Locale locale)
  {
    return "UNIX Portable PixMap";
  }

  public ImageWriter createWriterInstance(Object extension) throws IOException
  {
    return new PPMImageWriter(this);
  }

  public boolean canEncodeImage(ImageTypeSpecifier type)
  {
    // FIXME
    return true;
  }
}