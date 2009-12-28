package com.javapro.jicon;

/*
=====================================================================

  CheckBoxIcon.java
  
  Created by Claude Duguay
  Copyright (c) 2002

=====================================================================
*/

import java.awt.*;

import javax.swing.*;

public class CheckBoxIcon
  implements Icon
{
  public static final int NOTHING = 0;
  public static final int INCLUDE = 1;
  public static final int EXCLUDE = 2;
  
  protected Color fore, back;
  protected int type, width, height;
  protected boolean drawBox;
  
  public CheckBoxIcon(int type, int width, int height)
  {
    this(Color.black, Color.white, type, width, height, false);
  }
  
  public CheckBoxIcon(int type, int width, int height, boolean drawBox)
  {
    this(Color.black, Color.white, type, width, height, drawBox);
  }
  
  public CheckBoxIcon(Color fore, Color back,
    int type, int width, int height)
  {
    this(fore, back, type, width, height, true);
  }
  
  public CheckBoxIcon(Color fore, Color back,
    int type, int width, int height, boolean drawBox)
  {
    this.fore = fore;
    this.back = back;
    this.type = type;
    this.width = width;
    this.height = height;
    this.drawBox = drawBox;
  }
  
  public int getIconWidth()
  {
    return width;
  }
  
  public int getIconHeight()
  {
    return height;
  }

  public void paintIcon(Component c, Graphics gc, int x, int y)
  {
    Graphics2D g = (Graphics2D)gc;
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(back);
    g.fillRect(x, y, width, height);
    if (drawBox)
    {
      g.setColor(c.getForeground());
      g.drawRect(x, y, width - 1, height - 1);
    }
    g.setColor(fore);
    g.setStroke(new BasicStroke(2));
    if (type == EXCLUDE)
    {
      g.drawLine(x + 1, y + 1, x + width - 2, y + height - 2);
      g.drawLine(x + 1, y + height - 2, x + width - 2, y + 1);
    }
    if (type == INCLUDE)
    {
      g.drawLine(x + width / 2 - 2, y + height - 4, x + width - 2, y + 2);
      g.drawLine(x + width / 2 - 2, y + height - 4, x + 2, y + height / 2);
    }
    g.setRenderingHint(
      RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_OFF);
    g.setColor(back);
    g.drawRect(x + 1, y + 1, width - 2, height - 2);
    if (drawBox)
    {
      g.setColor(c.getForeground());
      g.setStroke(new BasicStroke());
      g.drawRect(x, y, width - 1, height - 1);
    }
  }
}
