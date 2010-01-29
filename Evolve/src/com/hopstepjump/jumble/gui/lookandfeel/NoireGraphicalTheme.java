package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.List;
import java.util.Properties;

import javax.swing.JProgressBar;
import javax.swing.UIManager;

import com.jtattoo.plaf.BaseTheme;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.noire.*;

public class NoireGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);

    // set the theme properties
    NoireLookAndFeel.setTheme(props);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(NoireLookAndFeel.class.getName());
  }

  public String getName()
  {
    return "Noire";
  }

  public List<String> getSubthemes()
  {
    return NoireLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }
}