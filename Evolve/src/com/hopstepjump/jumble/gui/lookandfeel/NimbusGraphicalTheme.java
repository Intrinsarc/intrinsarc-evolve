package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

public class NimbusGraphicalTheme implements GraphicalTheme
{
  public static String THEME_NAME = "Nimbus";

  public void change(String subtheme) throws Exception
  {
    // select the Look and Feel
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
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
}
