// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.freehep.graphicsio.*;
import org.freehep.graphicsio.raw.*;
import org.freehep.util.*;
import org.freehep.util.images.*;
import org.freehep.util.io.*;

/**
 * DefineBitsJPEG3 TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineBitsJPEG3.java,v 1.1 2009-03-04 22:46:54 andrew Exp $
 */
public class DefineBitsJPEG3 extends DefineBitsJPEG2
{

  public DefineBitsJPEG3(int id, Image image, Properties options, Color bkg, ImageObserver observer)
  {
    this(id, ImageUtilities.createRenderedImage(image, observer, bkg), bkg, options);
  }

  public DefineBitsJPEG3(int id, RenderedImage image, Color bkg, Properties options)
  {
    this();
    character = id;
    this.image = image;
    this.options = options;
  }

  public DefineBitsJPEG3()
  {
    super(35, 3);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {

    DefineBitsJPEG3 tag = new DefineBitsJPEG3();
    tag.character = swf.readUnsignedShort();
    swf.getDictionary().put(tag.character, tag);
    int jpegLen = (int) swf.readUnsignedInt();
    byte[] data = swf.readByte(jpegLen);

    ByteArrayInputStream bais = new ByteArrayInputStream(data);
    BufferedImage bi = ImageGraphics2D.readImage("jpg", bais);
    if (bais.available() > 0)
      System.err.println("DefineBitsJPEG3: not all bytes read: " + bais.available());

    int width = bi.getWidth();
    int height = bi.getHeight();
    InflaterInputStream zip = new InflaterInputStream(swf);
    byte[] alpha = new byte[width * height];
    zip.read(alpha);

    WritableRaster raster = bi.getAlphaRaster();
    for (int y = 0; y < height; y++)
    {
      for (int x = 0; x < width; x++)
      {
        raster.setPixel(x, y, new int[]{alpha[y * width + x]});
      }
    }

    tag.image = bi;
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {

    swf.writeUnsignedShort(character);
    swf.writeUnsignedInt(imageLength);
    swf.write(getImageBytes());
  }

  public int getLength() throws IOException
  {
    return getImageBytes().length + 2;
  }

  private byte[] imageBytes;
  private int imageLength;
  private byte[] getImageBytes() throws IOException
  {
    if (imageBytes == null)
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageGraphics2D.writeImage(image, "jpg", options, baos);
      imageLength = baos.size();

      FlateOutputStream flate = new FlateOutputStream(baos);
      UserProperties props = new UserProperties();
      props.setProperty(RawImageWriteParam.BACKGROUND, Color.BLACK);
      props.setProperty(RawImageWriteParam.CODE, "A");
      props.setProperty(RawImageWriteParam.PAD, 1);
      ImageGraphics2D.writeImage(image, "raw", props, flate);
      flate.finish();
      baos.close();

      imageBytes = baos.toByteArray();
    }
    return imageBytes;
  }

  public String toString()
  {
    StringBuffer s = new StringBuffer();
    s.append(super.toString() + "\n");
    s.append("  character:   " + character + "\n");
    s.append("  image:      " + image + "\n");
    return s.toString();
  }
}
