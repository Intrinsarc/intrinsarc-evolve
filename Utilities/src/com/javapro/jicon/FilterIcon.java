package com.javapro.jicon;

/*
=====================================================================

  FilterIcon.java
  
  Created by Claude Duguay
  Copyright (c) 2002
  
=====================================================================
*/

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class FilterIcon
  implements Icon
{
  protected Image image;
  
  public FilterIcon(Icon icon, ImageFilter filter)
  {
    int w = icon.getIconWidth();
    int h = icon.getIconHeight();
    BufferedImage canvas = new BufferedImage(w, h,
      BufferedImage.TYPE_INT_ARGB);
    Graphics g = canvas.getGraphics();
    icon.paintIcon(null, g, 0, 0);
    image = Toolkit.getDefaultToolkit().createImage(
      new FilteredImageSource(canvas.getSource(), filter));
  }
  
  public int getIconWidth()
  {
    return image.getWidth(null);
  }
  
  public int getIconHeight()
  {
    return image.getHeight(null);
  }

  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    g.drawImage(image, x, y, c);
  }
}

