package com.intrinsarc.swing.lookandfeel;

import java.util.*;

import javax.swing.*;

public class SystemGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }

  public String getName()
  {
    return "System";
  }

  public List<String> getSubthemes()
  {
    return new ArrayList<String>();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  }

	public boolean drawsBoxAroundTextArea()
	{
		return false;
	}
}