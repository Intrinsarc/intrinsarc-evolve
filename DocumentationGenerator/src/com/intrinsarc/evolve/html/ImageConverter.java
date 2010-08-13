package com.intrinsarc.evolve.html;

import java.awt.*;
import java.awt.image.*;


public class ImageConverter
{
  public static BufferedImage toBufferedImage(Image image)
  {
      if (image instanceof BufferedImage)
      {
          return (BufferedImage)image;
      }
      BufferedImage out = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
      Graphics2D g2 = (Graphics2D) out.getGraphics();
      g2.drawImage(image, 0, 0, null);
      return out;
  }  
}
