package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.aluminium.*;

public class AluminiumGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);

    // set the theme properties
    AluminiumLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);
    
    // select the Look and Feel
    UIManager.setLookAndFeel(AluminiumLookAndFeel.class.getName());
  }

  public String getName()
  {
    return "Aluminium";
  }

  public List<String> getSubthemes()
  {
    return AluminiumLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }
}