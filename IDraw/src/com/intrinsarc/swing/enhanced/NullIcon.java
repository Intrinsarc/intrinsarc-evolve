package com.intrinsarc.swing.enhanced;

import java.awt.*;

import javax.swing.*;

public class NullIcon implements Icon
{
  public void paintIcon(Component c, Graphics g, int x, int y) {}
  public int getIconWidth() { return 16; }
  public int getIconHeight() { return 16; }
}
