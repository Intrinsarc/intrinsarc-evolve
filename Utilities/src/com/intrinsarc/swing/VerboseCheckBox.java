package com.intrinsarc.swing;

import java.awt.*;

import javax.swing.*;

public class VerboseCheckBox extends JCheckBox
{
	@Override
  public void paintComponent(Graphics g) 
  {
		if (isSelected())
			setText("on");
		else
			setText("off");
		super.paintComponent(g);
  }
}
