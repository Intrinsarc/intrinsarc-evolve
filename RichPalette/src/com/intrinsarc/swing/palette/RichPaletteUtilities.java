package com.intrinsarc.swing.palette;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public abstract class RichPaletteUtilities
{
  public static JPanel makeLine(Icon leftIcon, String name, JComponent right, final boolean useGradient, boolean useBorder)
  {
    JPanel panel = new JPanel(new BorderLayout())
    {      
      @Override
      public void paint(Graphics g)
      {
        if (useGradient)
        {
          Graphics2D g2d = (Graphics2D) g;
          Rectangle rect = getBounds();
          Color col3 = new Color(0, 0, 0, 0);
          Color col4 = new Color(0, 0, 0, 32);
  
          GradientPaint gradient = new GradientPaint(
              new Point(rect.x, rect.y),
              col4,
              new Point((int) (rect.width / 1.7), rect.height),
              col3);
          g2d.setPaint(gradient);
          g2d.fillRect(rect.x, rect.y, (int) (rect.width / 1.7), rect.height);          
        }
  
        super.paint(g);
      }
    };
    panel.setOpaque(false);
    
    if (useBorder)
    {
      BevelBorder bevel =
        new BevelBorder(BevelBorder.RAISED, null, new Color(0,0,0,0), new Color(0,0,0,0), null);
      panel.setBorder(
        new CompoundBorder(              
          bevel,
          new EmptyBorder(0, 1, 1, 0)));
    }
    else
      panel.setBorder(new EmptyBorder(2, 3, 2, 2));
    
    JLabel styledLabel = new JLabel(name);
    FlowLayout flow = new FlowLayout(FlowLayout.LEADING, 0, 0);
    JPanel title = new JPanel(flow);
    title.setBorder(null);
    title.setOpaque(false);
    JLabel iconLabel = (leftIcon) == null ? new JLabel() : new JLabel(leftIcon);
    iconLabel.setOpaque(false);
    title.add(iconLabel);
    
    // line up
    int extraX = Math.max(20 - (int) iconLabel.getPreferredSize().getWidth(), 0) + 2;
    title.add(Box.createRigidArea(new Dimension(extraX, 1)));
    title.add(styledLabel);
    styledLabel.setOpaque(false);
    
    panel.add(title, BorderLayout.WEST);
    if (right != null)
    {
      panel.add(right, BorderLayout.EAST);
    }
    return panel;
  }
}
