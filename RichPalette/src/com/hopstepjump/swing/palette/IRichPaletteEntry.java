package com.hopstepjump.swing.palette;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;

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
