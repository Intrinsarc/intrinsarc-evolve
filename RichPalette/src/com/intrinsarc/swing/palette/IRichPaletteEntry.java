package com.intrinsarc.swing.palette;

import javax.swing.*;

import com.intrinsarc.idraw.foundation.*;

public interface IRichPaletteEntry
{
  JComponent makeComponent();
  void select();
  void deselect();
  boolean isSelected();
  char getHotkey();
	ToolClassification getToolClassification(String category);
	String getName();
	Icon getIcon();
	Object getUserObject();
} 
