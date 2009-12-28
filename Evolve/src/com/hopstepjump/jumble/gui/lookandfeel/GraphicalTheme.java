package com.hopstepjump.jumble.gui.lookandfeel;

import java.util.*;

import javax.swing.*;


public interface GraphicalTheme
{
  public List<String> getSubthemes();
  public void change(String subtheme) throws Exception;
  public String getName();
  public void setProgressBarUI(JProgressBar progress);
}
