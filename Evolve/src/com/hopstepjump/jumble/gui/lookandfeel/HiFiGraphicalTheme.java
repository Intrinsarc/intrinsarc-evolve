package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.hifi.*;

public class HiFiGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);

    // set the theme properties
    HiFiLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(HiFiLookAndFeel.class.getName());
  }

  public String getName()
  {
    return "HiFi";
  }

  public List<String> getSubthemes()
  {
    return HiFiLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }
}