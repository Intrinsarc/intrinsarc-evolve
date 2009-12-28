package com.javapro.jicon;

/*
=====================================================================

  IconList.java
  
  Created by Claude Duguay
  Copyright (c) 2002
  
=====================================================================
*/

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

public class IconList
  implements IconListModel
{
  protected ArrayList listeners = new ArrayList();
  protected ArrayList icons = new ArrayList();
  protected int width, height;
  protected Icon icon;
  protected int index;
  
  public IconList() {}
  
  public IconList(Icon[] iconList)
  {
    setIcons(iconList);
  }
  
  public int getCurrentIndex()
  {
    return index;
  }
  
  public void setIcons(Icon[] iconList)
  {
    icons.clear();
    for (int i = 0; i < iconList.length; i++)
    {
      icons.add(iconList[i]);
    }
    setCurrentIcon(0);
    calculateSize();
    fireChangeEvent();
  }
  
  public void addIcon(Icon icon)
  {
    icons.add(icon);
    setCurrentIcon(capIndex(icons.size()));
    calculateSize();
    fireChangeEvent();
  }
  
  public void removeIcon(Icon icon)
  {
    icons.remove(icon);
    setCurrentIcon(capIndex(index));
    calculateSize();
    fireChangeEvent();
  }
  
  public int getIconCount()
  {
    return icons.size();
  }
  
  public Icon getIcon(int index)
  {
    return (Icon)icons.get(index);
  }
  
  public void setCurrentIcon(int index)
  {
    if (index < 0 || index > getIconCount() - 1)
    {
      throw new IllegalArgumentException(
        "invalid index value");
    }
    icon = (Icon)icons.get(index);
    this.index = index;
    fireChangeEvent();
  }
  
  protected int capIndex(int index)
  {
    if (index >= icons.size())
    {
      return icons.size() - 1;
    }
    return index;
  }
  
  protected void calculateSize()
  {
    width = 0;
    height = 0;
    for (int i = 0; i < getIconCount(); i++)
    {
      Icon icon = (Icon)icons.get(i);
      width = Math.max(width, icon.getIconWidth());
      height = Math.max(height, icon.getIconHeight());
    }
  }

  public int getIconWidth()
  {
    return width;
  }
  
  public int getIconHeight()
  {
    return height;
  }
  
  public void paintIcon(Component c, Graphics g, int x, int y)
  {
    icon.paintIcon(c, g, x, y);
  }
  
  protected void fireChangeEvent()
  {
    ChangeEvent event = new ChangeEvent(this);
    ArrayList list = (ArrayList)listeners.clone();
    for (int i = 0; i < list.size(); i++)
    {
      ChangeListener listener = (ChangeListener)list.get(i);
      listener.stateChanged(event);
    }
  }
  
  public void addChangeListener(ChangeListener listener)
  {
    listeners.add(listener);
  }
  
  public void removeChangeListener(ChangeListener listener)
  {
    listeners.remove(listener);
  }
}

