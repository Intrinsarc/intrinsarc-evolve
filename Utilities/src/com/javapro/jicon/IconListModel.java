package com.javapro.jicon;

/*
=====================================================================

  IconListModel.java
  
  Created by Claude Duguay
  Copyright (c) 2002
  
=====================================================================
*/

import javax.swing.*;
import javax.swing.event.*;

public interface IconListModel
  extends Icon
{
  public int getIconCount();
  public int getCurrentIndex();
  public Icon getIcon(int index);
  public void setIcons(Icon[] icon);
  public void addIcon(Icon icon);
  public void removeIcon(Icon icon);
  public void setCurrentIcon(int index);
  public void addChangeListener(ChangeListener listener);
  public void removeChangeListener(ChangeListener listener);
}

