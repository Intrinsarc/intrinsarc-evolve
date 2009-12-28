// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.gif;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.spi.*;

import org.freehep.graphicsio.*;
import org.freehep.util.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: GIFGraphics2D.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class GIFGraphics2D extends ImageGraphics2D
{

  static
  {
    try
    {
      Class clazz = Class.forName("org.freehep.graphicsio.gif.GIFImageWriterSpi");
      IIORegistry.getDefaultInstance().registerServiceProvider((ImageWriterSpi) clazz.newInstance(),
          ImageWriterSpi.class);
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }

  private static final String rootKey = GIFGraphics2D.class.getName();
  public static final String QUANTIZE_COLORS = rootKey + ".QuantizeColors";
  public static final String QUANTIZE_MODE = rootKey + ".QuantizeMode";

  private static final UserProperties defaultProperties = new UserProperties();
  static
  {
    defaultProperties.setProperty(QUANTIZE_COLORS, false);
    defaultProperties.setProperty(QUANTIZE_MODE, "WebColors");
  }

  public static Properties getDefaultProperties()
  {
    return defaultProperties;
  }

  public static void setDefaultProperties(Properties newProperties)
  {
    defaultProperties.setProperties(newProperties);
  }

  private final static String version = "$Revision: 1.1 $";

  public GIFGraphics2D(File file, Dimension size) throws FileNotFoundException
  {
    this(new FileOutputStream(file), size);
  }

  public GIFGraphics2D(File file, Component component) throws FileNotFoundException
  {
    this(new FileOutputStream(file), component);
  }

  public GIFGraphics2D(OutputStream os, Dimension size)
  {
    super(new BufferedOutputStream(os), size, "gif");
    initProperties(getDefaultProperties());
  }

  public GIFGraphics2D(OutputStream os, Component component)
  {
    super(new BufferedOutputStream(os), component, "gif");
    initProperties(getDefaultProperties());
  }
}
