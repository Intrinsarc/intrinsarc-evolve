package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.*;

import javax.swing.*;

import com.jtattoo.plaf.*;
import com.jtattoo.plaf.graphite.*;

public class GraphiteGraphicalTheme implements GraphicalTheme
{
  public void change(String subtheme) throws Exception
  {
    // setup the look and feel properties    
    Properties props = SmartGraphicalTheme.setOptions(subtheme);

    // set the theme properties
    GraphiteLookAndFeel.setTheme(subtheme);
    BaseTheme.setProperties(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(GraphiteLookAndFeel.class.getName());
  }

  public String getName()
  {
    return "Graphite";
  }

  public List<String> getSubthemes()
  {
    return GraphiteLookAndFeel.getThemes();
  }
  
  public void setProgressBarUI(JProgressBar progress)
  {
  	SmartGraphicalTheme.fixProgressBarUI(progress);
  }
}