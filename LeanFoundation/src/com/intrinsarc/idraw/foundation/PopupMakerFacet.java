package com.intrinsarc.idraw.foundation;

import java.awt.*;

import javax.swing.*;

public interface PopupMakerFacet
{
  public void displayPopup(
      Icon icon,
      String title,
      Object description,
      Color fillColor,
      Color textColor,
      int msecsToDisplayFor);
  
  public void displayPopup(
      final Icon icon, 
      final String title,
      final Object description,
      final Color fillColor,
      final Color textColor,
      final int msecsToDisplayFor,
      final boolean displayProgressBar,
      final int currentProgressPosition,
      final int progressMax);

	public void clearPopup();
}
