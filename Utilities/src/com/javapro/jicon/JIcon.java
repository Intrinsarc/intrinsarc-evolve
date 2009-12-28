package com.javapro.jicon;

/*
=====================================================================

  JIcon.java
  
  Created by Claude Duguay
  Copyright (c) 2002
  
=====================================================================
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class JIcon extends JPanel
  implements ChangeListener, FocusListener,
    MouseListener, KeyListener
{
  protected IconListModel icons = new IconList();
  
  protected static final Border
    BLANK_BORDER = new EmptyBorder(1, 1, 1, 1);
  protected static final Border
    FOCUS_BORDER = new LineBorder(Color.blue);
  
  public JIcon()
  {
    initComponent();
  }
  
  public JIcon(Icon icon)
  {
    initComponent();
    addIcon(icon);
  }

  public JIcon(Icon[] iconList)
  {
    initComponent();
    setIcons(iconList);
  }
  
  protected void initComponent()
  {
    setOpaque(true);
    setFocusable(false);
    addKeyListener(this);
    addMouseListener(this);
    addFocusListener(this);
    setBorder(BLANK_BORDER);
    icons.addChangeListener(this);
  }
  
  public IconListModel getModel()
  {
    return icons;
  }
  
  public void setModel(IconListModel icons)
  {
    if (icons != null)
    {
      icons.removeChangeListener(this);
    }
    icons.addChangeListener(this);
    resetSize();
  }

  public void addIcon(Icon icon)
  {
    icons.addIcon(icon);
  }
  
  public void setIcons(Icon[] iconList)
  {
    icons.setIcons(iconList);
  }
  
  public void removeIcon(Icon icon)
  {
    icons.removeIcon(icon);
  }
  
  public void nextIcon()
  {
    int index = icons.getCurrentIndex() + 1;
    if (index >= icons.getIconCount())
    {
      index = 0;
    }
    icons.setCurrentIcon(index);
  }
  
  public void prevIcon()
  {
    int index = icons.getCurrentIndex() - 1;
    if (index < 0)
    {
      index = icons.getIconCount() - 1;
    }
    icons.setCurrentIcon(index);
  }
  
  public void setBorder(Border border)
  {
    super.setBorder(border);
    if (border != null) resetSize();
  }
  
  protected void resetSize()
  {
    Insets insets = getInsets();
    int w = icons.getIconWidth();
    int h = icons.getIconHeight();
    w += (insets.left + insets.right);
    h += (insets.top + insets.bottom);
    Dimension size = new Dimension(w, h);
    setPreferredSize(size);
    setMinimumSize(size);
    doLayout();
    repaint();
  }
  
  public void stateChanged(ChangeEvent event)
  {
    resetSize();
  }
  
  public void focusGained(FocusEvent event)
  {
    if (isFocusable())
    {
      setBorder(FOCUS_BORDER);
    }
  }
  
  public void focusLost(FocusEvent event)
  {
    if (isFocusable())
    {
      setBorder(BLANK_BORDER);
    }
  }
  
  public void mousePressed(MouseEvent event)
  {
    if (isFocusable())
    {
      requestFocus();
    }
    nextIcon();
    repaint();
  }
  
  public void mouseReleased(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  
  public void keyPressed(KeyEvent event)
  {
    int code = event.getKeyCode();
    if (code == KeyEvent.VK_SPACE)
    {
      nextIcon();
      repaint();
    }
  }
  
  public void keyReleased(KeyEvent event) {}
  public void keyTyped(KeyEvent event) {}
  
  public void paintComponent(Graphics g)
  {
    if (isOpaque())
    {
      int w = getSize().width;
      int h = getSize().height;
      g.setColor(getBackground());
      g.fillRect(0, 0, w, h);
    }
    Insets insets = getInsets();
    icons.paintIcon(this, g,
      insets.left, insets.top);
  }
}

