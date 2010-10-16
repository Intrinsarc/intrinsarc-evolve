package com.intrinsarc.swing.lookandfeel;

import java.util.*;

import javax.swing.*;

public class SystemGraphicalTheme implements GraphicalTheme
{
	public static final String THEME_NAME = "System";
	
  public void change(String subtheme) throws Exception
  {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  }

  public String getName()
  {
    return THEME_NAME;
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