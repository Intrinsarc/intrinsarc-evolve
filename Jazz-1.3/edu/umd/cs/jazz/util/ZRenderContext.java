/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import edu.umd.cs.jazz.*;

/**
 * <b>ZRenderContext</b> stores information relevant to the current render as
 * it occurs. The render context is available to objects when they are rendered,
 * and the objects can use this information to change the way they render
 * themselves.
 * 
 * <P>
 * <b>Warning:</b> Serialized and ZSerialized objects of this class will not be
 * compatible with future Jazz releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Jazz. A future release of Jazz will provide support for long
 * term persistence.
 * 
 * @author Benjamin B. Bederson
 * @see ZCamera
 */
public class ZRenderContext implements Serializable
{
  private static final int DEFAULT_NUM_VISIBLE_BOUNDS = 10;

  /**
   * List of (recursive) cameras currently rendering the scenegraph
   */
  private Stack cameras;

  /**
   * List of (recursive) transforms that are the transform that the camera
   * started with before it started painting. Visual components could need to
   * know this for advanced techniques.
   */
  private Stack transforms;

  /**
   * List of (recursive) visible bounds in the local coordinates of the current
   * node.
   */
  private ZList.ZBoundsList visibleBounds = ZListImpl.NullList;

  /**
   * The surface that triggered this render.
   */
  private ZDrawingSurface surface;

  /**
   * The graphics that is active for this render.
   */
  private transient Graphics2D g2;

  /**
   * The rendering quality that was requested for this render context
   */
  private int qualityRequested;

  /**
   * greekText specifies that text should be rendered as "greek" rather than
   * actual characters
   */
  private boolean greekText = false;

  /**
   * Accurate spacing specifies whether jazz should turn on the fractional
   * metrics bit in text rendering. Note that this does not work well with
   * editing in Swing components on the jazz surface
   */
  private boolean accurateSpacing = true;

  // ****************************************************************************
  //
  // Constructors
  //
  // ***************************************************************************

  /**
   * Constructs a simple ZRenderContext. This is intended to be used only by
   * context-sensitive objects to compute bounds. This constructor should not be
   * used for render contexts to be used for an actual render.
   * 
   * @param camera
   *          The camera
   */
  public ZRenderContext(ZCamera camera)
  {
    surface = null;
    cameras = new Stack();
    transforms = new Stack();
    this.visibleBounds = new ZListImpl.ZBoundsListImpl(DEFAULT_NUM_VISIBLE_BOUNDS);
    this.visibleBounds.add(0, camera.getViewBounds());

    cameras.push(camera);
  }

  /**
   * Constructs a new ZRenderContext.
   * 
   * @param aG2
   *          The graphics for this render
   * @param visibleBounds
   *          The bounds being rendered in screen coordinates
   * @param aSurface
   *          The surface being rendered onto
   * @param qualityRequested
   *          The quality to render with
   */
  public ZRenderContext(Graphics2D aG2, ZBounds visibleBounds, ZDrawingSurface aSurface, int qualityRequested)
  {
    surface = null;
    cameras = new Stack();
    transforms = new Stack();
    this.visibleBounds = new ZListImpl.ZBoundsListImpl(DEFAULT_NUM_VISIBLE_BOUNDS);
    this.visibleBounds.add(0, visibleBounds);
    g2 = aG2;
    surface = aSurface;
    this.qualityRequested = qualityRequested;
    this.accurateSpacing = surface.getUseFractionalMetrics();
    setRenderingHints(g2, qualityRequested);
  }

  /**
   * Get the graphics used for this render.
   * 
   * @return the graphics
   */
  public Graphics2D getGraphics2D()
  {
    return g2;
  }

  /**
   * Add a visible bounds to the render context.
   * 
   * @param bounds
   *          the new bounds.
   */
  public void pushVisibleBounds(ZBounds bounds)
  {
    visibleBounds.add(bounds);
  }

  /**
   * Remove a visible bounds from the render context.
   */
  public void popVisibleBounds()
  {
    visibleBounds.pop();
  }

  /**
   * Get the visible bounds of the current render in the local coordinate
   * system.
   * 
   * @return the bounds
   */
  public ZBounds getVisibleBounds()
  {
    if (visibleBounds.size() == 0)
    {
      return null;
    }
    else
    {
      return (ZBounds) visibleBounds.get(visibleBounds.size() - 1);
    }
  }

  /**
   * Get the drawing surface being rendered onto.
   * 
   * @return the surface
   */
  public ZDrawingSurface getDrawingSurface()
  {
    return surface;
  }

  /**
   * Specify if strings should be rendered one character at a time with slower,
   * but more accurate spacing.
   * 
   * @param <code>b</code> True turns on accurate spacing, false turns it off.
   */
  public void setAccurateSpacing(boolean b)
  {
    if (accurateSpacing != b)
    {
      this.accurateSpacing = b;
      setRenderingHints(getGraphics2D(), qualityRequested);
    }
  }

  /**
   * Determine if strings should be rendered with accurate (but slower)
   * character spacing.
   * 
   * @return true if accurate spacing is on
   */
  public boolean getAccurateSpacing()
  {
    return accurateSpacing;
  }

  /**
   * Specify if strings should be rendered as "greek" blobs rather than actual
   * text. Typically only very small text should be rendered "greeked" and only
   * when the system is animating.
   * 
   * @param <code>b</code> True turns on greek text.
   */
  public void setGreekText(boolean b)
  {
    greekText = b;
  }

  /**
   * Determine if text should be rendered "greeked"
   * 
   * @return true if text is "greeked"
   */
  public boolean getGreekText()
  {
    return greekText;
  }

  /**
   * Sets the rendering hints of the specified graphics to either or high or
   * low. This gets called whenever the surface is painted, and specifies how
   * high and low quality are defined.
   */
  protected void setRenderingHints(Graphics2D g2, int quality)
  {
    switch (quality)
    {
      case ZDrawingSurface.RENDER_QUALITY_LOW :
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if (accurateSpacing)
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }
        else
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        setGreekText(true);
        break;

      case ZDrawingSurface.RENDER_QUALITY_MEDIUM :
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // added by andrewmcv
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if (accurateSpacing)
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }
        else
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        setGreekText(false);
        break;

      case ZDrawingSurface.RENDER_QUALITY_MEDIUM_JAGGED_TEXT :
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // added by andrewmcv
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        if (accurateSpacing)
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }
        else
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        }
        setGreekText(false);
        break;

      case ZDrawingSurface.RENDER_QUALITY_HIGH :
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (accurateSpacing)
        {
          g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }
        setGreekText(false);
        break;
    }
  }

  /**
   * Get the current camera being rendered within.
   * 
   * @return the camera
   */
  public ZCamera getRenderingCamera()
  {
    if (cameras.isEmpty())
    {
      return null;
    }
    else
    {
      return (ZCamera) cameras.peek();
    }
  }

  /**
   * Get the transform the current camera was given before it started rendering
   * itself. This could be useful for an advanced object type that needs to know
   * what the transform was before the current camera changed it to apply its
   * view.
   * 
   * @return the transform
   */
  public AffineTransform getCameraTransform()
  {
    if (transforms.isEmpty())
    {
      return null;
    }
    else
    {
      return (AffineTransform) transforms.peek();
    }
  }

  /**
   * Add a rendering camera
   * 
   * @param camera
   *          The camera
   */
  public void pushCamera(ZCamera camera)
  {
    cameras.push(camera);
    transforms.push(g2.getTransform());
  }

  /**
   * Remove a rendering camera
   */
  public void popCamera()
  {
    cameras.pop();
    transforms.pop();
  }

  /**
   * Returns the magnification of the current camera being rendered within. If
   * currently being rendered within nested cameras, then this returns
   * <em>only</em> the magnification of the current camera. Note that this
   * does <em>not</em> include the transformations of the current or any other
   * object being rendered.
   * 
   * @see #getCompositeMagnification
   */
  public double getCameraMagnification()
  {
    ZCamera camera = getRenderingCamera();
    if (camera == null)
    {
      return 1.0d;
    }
    else
    {
      return camera.getMagnification();
    }
  }

  /**
   * Returns the total current magnification that is currently being used for
   * rendering. This includes the magnifcation of the current cameras as well as
   * the scale of the current any parent objects.
   * 
   * @see #getCameraMagnification
   */
  public double getCompositeMagnification()
  {
    return ZTransformGroup.computeScale(g2.getTransform());
  }
}
