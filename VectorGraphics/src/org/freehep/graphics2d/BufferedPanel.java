// Copyright (c) 2003, SLAC, Stanford, U.S.A.
package org.freehep.graphics2d;

import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;

import javax.swing.*;

/**
 * This class extends JPanel by adding double buffering. This is intended to be
 * used in situations in which redrawing the contents of the panel is expensive.
 * 
 * @author Mark Donszelmann
 * @version $Id: BufferedPanel.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class BufferedPanel extends JPanel implements java.io.Serializable
{

  private VectorGraphics bufferedGraphics;
  private BufferedImage bufferedImage;
  private Dimension oldDimension = new Dimension();
  private Dimension dim = new Dimension();
  private Rectangle clip = new Rectangle();
  private boolean printing = false;
  private boolean exporting = false;
  private boolean repaint = false;

  /**
   * Creates a new BufferedPanel with a width and height set to zero.
   * 
   * @param opaque
   *          transparent panel
   */
  public BufferedPanel(boolean opaque)
  {

    // First turn off the Swing double buffering.
    super(false);

    // Make this either opaque or transparent.
    setOpaque(opaque);
  }

  /**
   * Triggers a full "user" repaint. If the "system" wants to repaint it will
   * call the paint(Graphics) method directly, rather than scheduling a
   * paint(Graphics) through a repaint().
   */
  public void repaint()
  {
    super.repaint();
    repaint = true;
  }

  /**
   * Triggers a full repaint, since the component is not valid anymore (size
   * change, iconized, ...)
   */
  public void invalidate()
  {
    super.invalidate();
    repaint = true;
  }

  /**
   * Returns if true if paintComponent(VectorGraphics) should be called (was
   * triggered by a repaint() or invalidate(), and resets the trigger.
   */
  private synchronized boolean shouldRepaint()
  {
    boolean result = repaint;
    repaint = false;
    return result;
  }

  /**
   * Paint this panel by calling paintComponent(VectorGraphics) if necessary and
   * flushing the buffered image to the screen. This method also handles
   * printing and exporting separately.
   * 
   * @param g
   *          Graphics object
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // do not paint if params are null
    if ((g == null) || (bufferedImage == null))
      return;

    // decide where we are painting
    if (g instanceof PrinterGraphics)
      printing = true;
    if (g instanceof VectorGraphics)
      exporting = true;

    if (!isDisplaying())
    {
      paintComponent(VectorGraphics.create(g));
      return;
    }

    if (shouldRepaint())
    {
      paintComponent(bufferedGraphics);
    }

    // copy buffer to screen
    long t0 = System.currentTimeMillis();
    // Despite what the API documentation says,
    // getClipBounds() returns the current dirty region. This
    // can then be used to speedup the drawing of the
    // this BufferedPanel.
    clip = g.getClipBounds(clip);

    BufferedImage subimage = bufferedImage.getSubimage(clip.x, clip.y, clip.width, clip.height);
    g.drawImage(subimage, clip.x, clip.y, this);
    // System.out.println("CopyImage took: "+(System.currentTimeMillis()-t0)+"
    // ms.");
  }

  /**
   * Returns a pointer to the graphics (VectorGraphics) context of the buffer.
   */
  public Graphics getGraphics()
  {
    return bufferedGraphics;
  }

  /**
   * Allows for custom graphics to be painted. Subclasses should implement real
   * drawing here. They can ask isPrinting(), isExporting() or isDisplaying() to
   * see where the output goes. If painting is done to a display it is done to a
   * buffer which is kept and copied afterwards.
   * 
   * Note that the parameter here is of class VectorGraphics rather than
   * Graphics.
   */
  public void paintComponent(VectorGraphics vg)
  {
  }

  /**
   * Resize and move a component.
   */
  public void setBounds(int x, int y, int w, int h)
  {
    // Make sure that the parent's method is called first;
    // otherwise, the resize never happens and new images are NOT
    // made.
    super.setBounds(x, y, w, h);

    // Make the backing image.
    makeImage();
  }

  /**
   * Returns true if the drawing is made for a PrinterGraphics context.
   */
  public boolean isPrinting()
  {
    return printing;
  }

  /**
   * Returns true if the drawing is made for a VectorGraphics context.
   */
  public boolean isExporting()
  {
    return exporting;
  }

  /**
   * Returns true if the drawing is made for a PixelGraphics context, the
   * display. True if not Printing and not Exporting.
   */
  public boolean isDisplaying()
  {
    return ((!isExporting()) && (!isPrinting()));
  }

  /**
   * Make the buffered image for this panel. Check to see if the size has
   * changed before doing anything.
   */
  private void makeImage()
  {

    // Get the full size of the panel.
    dim = getSize(dim);

    // Check that the current size is positive and that the new
    // dimension is not equal to the old one.
    if (dim.width > 0 && dim.height > 0)
    {
      if (!oldDimension.equals(dim))
      {

        // allocate an Image, which from before JDK 1.4 was always in System
        // Memory,
        // and from JDK 1.4 is a Managed Buffered Image in System memory or VRAM
        // (Windows)
        // or a BufferedImage (getClass always returns BufferedImage)
        // Real BufferedImages resides in System Memory.
        // Volatile Images reside in VRAM (Windows), or XServer memory.
        // Drawing more than ~1000 line segments to system memory (BI) and
        // doing a copy to the display is faster than drawing them
        // to video ram (VI) and using the fast VRAM->display copy.
        // For us, System Memory is the choice for both Windows and XWindows,
        // since we draw more lines that copying.

        // However, it may be that the acceleration possible on VRAM my up this
        // 1000 line limit.

        // The image created here is a Managed Buffered Image, which can be
        // moved
        // from System Memory and copied to VRAM...
        // Use the flag -Dsun.java2d.ddoffscreen=false to keep the image in
        // system memory, or -Dsun.java2d.ddforcevram=true to keep the image
        // in VRAM. Using none, seems to affect performance in picking mode.

        // There is no programmatic (java) way of keeping the image in
        // system memory, not even if you create a BufferedImage explicitly.
        if (isOpaque())
        {
          bufferedImage = (BufferedImage) super.createImage(dim.width, dim.height);
        }
        else
        {
          bufferedImage = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
        }
        bufferedGraphics = VectorGraphics.create(bufferedImage.getGraphics());

        // Reset the old size of this panel.
        oldDimension.setSize(dim);
      }
    }
    else
    {
      bufferedImage = null;
      bufferedGraphics = null;
    }
  }

}
