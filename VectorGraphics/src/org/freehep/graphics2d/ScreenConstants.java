// Copyright 2000, CERN, Geneva, Switzerland and University of Santa Cruz, California, U.S.A.
package org.freehep.graphics2d;

import java.awt.*;
import java.util.*;

/**
 * This class defines a set of constants which describe a screen.
 * 
 * @author Mark Donszelmann
 * @version $Id: ScreenConstants.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class ScreenConstants
{
  public final static String VGA = "600x480";
  public final static String SVGA = "800x600";
  public final static String XGA = "1024x768";
  public final static String SXGA = "1280x1024";
  public final static String SXGA_PLUS = "1400x1050";
  public final static String UXGA = "1600x1200";

  private static Dimension UNDEFINED = new Dimension(0, 0);
  private static final Map sizes;
  static
  {
    sizes = new HashMap();
    sizes.put(VGA, new Dimension(640, 480));
    sizes.put(SVGA, new Dimension(800, 600));
    sizes.put(XGA, new Dimension(1024, 768));
    sizes.put(SXGA, new Dimension(1280, 1024));
    sizes.put(SXGA_PLUS, new Dimension(1400, 1050));
    sizes.put(UXGA, new Dimension(1600, 1200));
  }

  public static Dimension getSize(String size)
  {
    Dimension d = (Dimension) sizes.get(size);
    return d != null ? d : UNDEFINED;
  }
}
