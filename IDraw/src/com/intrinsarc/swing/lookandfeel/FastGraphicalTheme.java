package com.intrinsarc.swing.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.fast.*;

public class FastGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);

    // set the theme properties
    FastLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(FastLookAndFeel.class.getName());
  }

  public String getName()
  {
    return "Fast";
  }

  public List<String> getSubthemes()
  {
    return FastLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }

	public boolean drawsBoxAroundTextArea()
	{
		return false;
	}
}