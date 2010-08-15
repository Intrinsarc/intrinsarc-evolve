package com.intrinsarc.swing.lookandfeel;

import java.util.*;

import javax.swing.*;

public class QuaquaGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
  	UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
  }

  public String getName()
  {
    return "Quaqua";
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