// Copyright 2003 FreeHEP
package org.freehep.graphicsio.cgm.test;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import org.freehep.graphicsio.cgm.*;
import org.freehep.util.*;

/**
 * CellArrayTest illustrates drawing of an image, including just a graphics
 * transform, and then also an image tranform with rotation. The image includes
 * a transparent cell (represented by a cell with 0 alpha). Note that CGM
 * doesn't fully support alpha, but it supports a transparent cell value, which
 * is used by CGMGraphics2D only when alpha is exactly 0).
 * 
 * @author Ian Graham
 */
public class CellArrayTest
{

  public static void main(String[] args) throws IOException
  {

    int[][] argbArray = {{0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFF008000},
        {0xFFFFFF00, 0x00000000, 0xFFFF00FF, 0xFF008000}, {0xFF00FFFF, 0xFF80FF80, 0xFFFFFF00, 0xFF008000},};

    BufferedImage image = new BufferedImage(4, 3, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < argbArray.length; y++)
    {
      for (int x = 0; x < argbArray[0].length; x++)
      {
        image.setRGB(x, y, argbArray[y][x]);
      }
    }

    UserProperties graphicsProperties = (UserProperties) CGMGraphics2D.getDefaultProperties();
    graphicsProperties.setProperty(CGMGraphics2D.BINARY, true);
    graphicsProperties.setProperty(CGMGraphics2D.BACKGROUND_COLOR, Color.LIGHT_GRAY);
    graphicsProperties.setProperty(CGMGraphics2D.BACKGROUND, true);

    OutputStream out = new FileOutputStream("CellArrayTest.cgm");
    CGMGraphics2D graphics = new CGMGraphics2D(out, new Dimension(6, 5));
    graphics.setCreator(System.getProperty("user.name"));
    graphics.startExport();
    graphics.setTransform(AffineTransform.getTranslateInstance(1, 1));
    graphics.drawImage(image, 0, 0, null);
    AffineTransform transform = AffineTransform.getTranslateInstance(2, 1);
    transform.concatenate(AffineTransform.getRotateInstance(Math.toRadians(30)));
    graphics.drawImage(image, transform, null);
    graphics.endExport();
  }

}
