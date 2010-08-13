package com.intrinsarc.evolve.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.acryl.*;

public class AcrylGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);
    SmartGraphicalTheme.changeWindowIcons();

    // set your theme
    AcrylLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(AcrylLookAndFeel.class.getName());
  }

  public String getName()
  {
    return "Acryl";
  }

  public List<String> getSubthemes()
  {
    return AcrylLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }
}
