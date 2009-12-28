package com.hopstepjump.idraw.environment;

import java.awt.*;

import javax.swing.*;

public interface LongRunningTaskProgressMonitorFacet
{
  public void invokeActivityAndMonitorProgress(final Runnable activity);
  
  public void stopActivity();
  
  /**
   * stop by displaying a dialog.  usually indicates some sort of failure.
   */
  public void stopActivityAndDisplayDialog(
      final String title,
      final String message,
      final boolean error);
  
  /** stop by displaying a popup -- usually indicates success
   */
  public void stopActivityAndDisplayPopup(
      final Icon icon,
      final String title,
      final String message,
      final Color color,
      final int msecsToDisplayFor,
      boolean showCompletedProgressBar);
  
  /** display an interim dialog to show where we are up to
   */
  public void displayInterimPopup(
      Icon icon,
      String title,
      String message,
      Color color,
      int msecsToDisplayFor);
  
  /** display an interim dialog to show where we are up to
   */
  public void displayInterimPopupWithProgress(
      Icon icon,
      String title,
      String message,
      Color color,
      int msecsToDisplayFor,
      int currentProgress,
      int maxProgress);
	public void waitForFinish();
}
