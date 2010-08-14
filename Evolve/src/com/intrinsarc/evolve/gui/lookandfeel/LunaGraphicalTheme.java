package com.intrinsarc.evolve.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.intrinsarc.swing.*;
import com.jtattoo.plaf.*;
import com.jtattoo.plaf.luna.*;

public class LunaGraphicalTheme implements GraphicalTheme
{
  public static final String THEME_NAME = "Luna";

	public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);

    // set the theme properties
    LunaLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(LunaLookAndFeel.class.getName());
  }

  private static ImageIcon loadIcon(String iconName)
  {
    return IconLoader.loadIcon(iconName);
  }

  public String getName()
  {
    return THEME_NAME;
  }

  public List<String> getSubthemes()
  {
    return LunaLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }
}