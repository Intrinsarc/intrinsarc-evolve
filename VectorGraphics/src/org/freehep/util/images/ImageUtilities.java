// Copyright 2001-2003, FreeHEP.
package org.freehep.util.images;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * @author Mark Donszelmann
 * @version $Id: ImageUtilities.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class ImageUtilities
{

  private ImageUtilities()
  {
  }

  public static RenderedImage createRenderedImage(Image image, ImageObserver observer, Color bkg)
  {
    if ((bkg == null) && (image instanceof RenderedImage))
      return (RenderedImage) image;

    BufferedImage bufferedImage = new BufferedImage(image.getWidth(observer), image.getHeight(observer), (bkg == null)
        ? BufferedImage.TYPE_INT_ARGB
        : BufferedImage.TYPE_INT_RGB);
    Graphics g = bufferedImage.getGraphics();
    if (bkg == null)
    {
      g.drawImage(image, 0, 0, observer);
    }
    else
    {
      g.drawImage(image, 0, 0, bkg, observer);
    }
    return bufferedImage;
  }

  public static RenderedImage createRenderedImage(RenderedImage image, Color bkg)
  {
    if (bkg == null)
      return image;

    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
    g.setBackground(bkg);
    g.clearRect(0, 0, image.getWidth(), image.getHeight());
    g.drawRenderedImage(image, new AffineTransform());
    return bufferedImage;
  }

  public static byte[] getBytes(Image image, Color bkg, String code, int pad, ImageObserver observer)
  {
    return getBytes(createRenderedImage(image, observer, bkg), bkg, code, pad);
  }

  /**
   * Returns the bytes of an image.
   * 
   * @param image
   *          to be converted to bytes
   * @param bkg
   *          the color to be used for alpha-multiplication
   * @param code
   *          ARGB, A, or BGR, ... you may also use *ARGB to pre-multiply with
   *          alpha
   * @param pad
   *          number of bytes to pad the scanline with (1=byte, 2=short, 4=int,
   *          ...)
   */
  public static byte[] getBytes(RenderedImage image, Color bkg, String code, int pad)
  {
    if (pad < 1)
      pad = 1;

    Raster raster = image.getData();

    int width = image.getWidth();
    int height = image.getHeight();

    boolean preMultiply = (code.charAt(0) == '*');
    if (preMultiply)
      code = code.substring(1);

    int pixelSize = code.length();

    int size = width * height * pixelSize;
    size += (width % pad) * height;
    int index = 0;
    byte[] bytes = new byte[size];

    for (int y = 0; y < height; y++)
    {
      for (int x = 0; x < width; x++)
      {

        int[] rgba = raster.getPixel(x, y, (int[]) null);

        // Check the transparancy. If transparent substitute
        // the background color.
        if (preMultiply && (rgba.length > 3))
        {
          if (bkg == null)
            bkg = Color.BLACK;
          double alpha = rgba[3] / 255.0;
          rgba[0] = (int) (alpha * rgba[0] + (1 - alpha) * bkg.getRed());
          rgba[1] = (int) (alpha * rgba[1] + (1 - alpha) * bkg.getGreen());
          rgba[2] = (int) (alpha * rgba[2] + (1 - alpha) * bkg.getBlue());
        }

        for (int i = 0; i < code.length(); i++)
        {
          switch (code.charAt(i))
          {
            case 'a' :
            case 'A' :
              bytes[index] = (rgba.length > 3) ? (byte) rgba[3] : (byte) 0xFF;
              break;

            case 'r' :
            case 'R' :
              bytes[index] = (byte) rgba[0];
              break;

            case 'g' :
            case 'G' :
              bytes[index] = (byte) rgba[1];
              break;

            case 'b' :
            case 'B' :
              bytes[index] = (byte) rgba[2];
              break;

            default :
              System.err.println(ImageUtilities.class.getClass() + ": Invalid code in '" + code + "'");
              break;
          }
          index++;
        }
      }
      for (int i = 0; i < (width % pad); i++)
      {
        bytes[index] = 0;
        index++;
      }
    }

    return bytes;
  }
}
