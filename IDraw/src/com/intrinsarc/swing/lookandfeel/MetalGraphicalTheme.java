package com.intrinsarc.swing.lookandfeel;

import java.util.*;

import javax.swing.*;

public class MetalGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    UIManager.put("swing.boldMetal", false);
    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
  }

  public String getName()
  {
    return "Metal";
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