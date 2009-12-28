// Copyright 2003, FreeHEP
package org.freehep.graphicsio.gif;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;


/**
 * 
 * @version $Id: GIFImageWriter.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class GIFImageWriter extends ImageWriter
{

  public GIFImageWriter(GIFImageWriterSpi originatingProvider)
  {
    super(originatingProvider);
  }

  public void write(IIOMetadata streamMetadata, IIOImage image, ImageWriteParam param) throws IOException
  {
    if (image == null)
      throw new IllegalArgumentException("image == null");

    if (image.hasRaster())
      throw new UnsupportedOperationException("Cannot write rasters");

    Object output = getOutput();
    if (output == null)
      throw new IllegalStateException("output was not set");

    if (param == null)
      param = getDefaultWriteParam();

    ImageOutputStream ios = (ImageOutputStream) output;
    RenderedImage ri = image.getRenderedImage();

    if (ri instanceof BufferedImage)
    {
      BufferedImage bi = (BufferedImage) ri;
      boolean interlaced = param.getProgressiveMode() != ImageWriteParam.MODE_DISABLED;
      GIFEncoder encoder = new GIFEncoder(bi, ios, interlaced);
      encoder.encode();
    }
    else
    {
      throw new IOException("Image not of type BufferedImage");
    }
  }

  public IIOMetadata convertStreamMetadata(IIOMetadata inData, ImageWriteParam param)
  {
    return null;
  }

  public IIOMetadata convertImageMetadata(IIOMetadata inData, ImageTypeSpecifier imageType, ImageWriteParam param)
  {
    return null;
  }

  public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier imageType, ImageWriteParam param)
  {
    return null;
  }

  public IIOMetadata getDefaultStreamMetadata(ImageWriteParam param)
  {
    return null;
  }

  public ImageWriteParam getDefaultWriteParam()
  {
    return new GIFImageWriteParam(getLocale());
  }
}
