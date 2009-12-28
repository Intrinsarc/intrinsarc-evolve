// University of California, Santa Cruz, USA and
// CERN, Geneva, Switzerland, Copyright (c) 2000
package org.freehep.graphics2d.test;

import java.awt.*;

import javax.swing.*;

import org.freehep.graphics2d.*;

/**
 * @author Mark Donszelmann
 * @version $Id: TestPerformance.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class TestPerformance extends JPanel
{

  private int text = 20;

  public void paintComponent(Graphics g)
  {

    VectorGraphics vg = VectorGraphics.create(g);
    Graphics2D g2 = (Graphics2D) g;

    Dimension dim = getSize();
    Insets insets = getInsets();

    int width = dim.width / 5;
    int height = dim.height / 2;

    // vg.setLineWidth(1.5);
    vg.setColor(Color.black);
    vg.fillRect(insets.left, insets.top, dim.width - insets.left - insets.right, dim.height - insets.top
        - insets.bottom);

    vg.setColor(Color.orange);
    drawIntPolylines(g2, 1 * width, 0, width, height);
    drawIntPolylines(vg, 1 * width, height, width, height);

    vg.setColor(Color.cyan);
    drawDoublePolylines(g2, 2 * width, 0, width, height);
    drawDoublePolylines(vg, 2 * width, height, width, height);

    vg.setColor(Color.red);
    drawSymbols(vg, 3 * width, height, width, height, VectorGraphicsConstants.SYMBOL_STAR, false);
    vg.setColor(Color.blue);
    drawSymbols(vg, 4 * width, height, width, height, VectorGraphicsConstants.SYMBOL_CIRCLE, true);
  }

  private void drawIntPolylines(Graphics2D g, int x, int y, int width, int height)
  {
    int[][] xp = new int[100][500];
    int[][] yp = new int[100][500];
    double lineWidth = 2.0;
    for (int i = 0; i < xp.length; i++)
    {
      for (int j = 0; j < xp[0].length; j++)
      {
        xp[i][j] = (int) (x + Math.random() * width);
        yp[i][j] = (int) (y + text + Math.random() * (height - text));
      }
    }

    long start, end;
    if (g instanceof VectorGraphics)
    {
      VectorGraphics vg = (VectorGraphics) g;

      start = System.currentTimeMillis();
      for (int i = 0; i < xp.length; i++)
      {
        vg.drawPolyline(xp[i], yp[i], xp[i].length);
      }
      end = System.currentTimeMillis();
    }
    else
    {
      start = System.currentTimeMillis();
      for (int i = 0; i < xp.length; i++)
      {
        g.drawPolyline(xp[i], yp[i], xp[i].length);
      }
      end = System.currentTimeMillis();
    }
    g.drawString(xp.length + " IntPolys[" + xp[0].length + "] " + (end - start) + " ms", x, y + 15);
  }

  private void drawDoublePolylines(Graphics2D g, int x, int y, int width, int height)
  {
    double[][] xp = new double[100][500];
    double[][] yp = new double[100][500];
    for (int i = 0; i < xp.length; i++)
    {
      for (int j = 0; j < xp[0].length; j++)
      {
        xp[i][j] = x + Math.random() * width;
        yp[i][j] = y + text + Math.random() * (height - text);
      }
    }

    long start, end;
    if (g instanceof VectorGraphics)
    {
      VectorGraphics vg = (VectorGraphics) g;

      start = System.currentTimeMillis();
      for (int i = 0; i < xp.length; i++)
      {
        vg.drawPolyline(xp[i], yp[i], xp[i].length);
      }
      end = System.currentTimeMillis();
    }
    else
    {
      start = System.currentTimeMillis();
      for (int i = 0; i < xp.length; i++)
      {
        int[] ix = new int[xp[i].length];
        int[] iy = new int[yp[i].length];
        for (int j = 0; j < xp[i].length; j++)
        {
          ix[j] = (int) xp[i][j];
          iy[j] = (int) yp[i][j];
        }
        g.drawPolyline(ix, iy, ix.length);
      }
      end = System.currentTimeMillis();
    }
    g.drawString(xp.length + " DoublePolys[" + xp[0].length + "] " + (end - start) + " ms", x, y + 15);
  }

  private void drawSymbols(VectorGraphics vg, int x, int y, int width, int height, int type, boolean fill)
  {
    double[] xs = new double[5000];
    double[] ys = new double[5000];
    for (int i = 0; i < xs.length; i++)
    {
      xs[i] = x + Math.random() * width;
      ys[i] = y + text + Math.random() * (height - text);
    }

    long start = System.currentTimeMillis();
    for (int i = 0; i < xs.length; i++)
    {
      if (fill)
      {
        vg.fillSymbol(xs[i], ys[i], 6, type);
      }
      else
      {
        vg.drawSymbol(xs[i], ys[i], 6, type);
      }
    }
    long end = System.currentTimeMillis();
    vg.drawString(xs.length + " symbols[" + type + "] " + (end - start) + " ms", x, y + 15);
  }

  public static void main(String[] args)
  {

    // Create a new frame to hold everything.
    JFrame frame = new JFrame("Test PixelGraphics2D Performance");

    // Create a new instance of this class and add it to the frame.
    frame.getContentPane().add(new TestPerformance());

    // Give the frame a size and make it visible.
    frame.setSize(new Dimension(1024, 768));
    frame.show();
  }
}
