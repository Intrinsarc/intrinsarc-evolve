package com.hopstepjump.jumble.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.hopstepjump.idraw.environment.*;
import com.hopstepjump.idraw.foundation.*;

public class LongRunningTaskProgressMonitor implements LongRunningTaskProgressMonitorFacet
{
  private ToolCoordinatorFacet coordinator;
  private JFrame frame;
  private PopupMakerFacet popup;
  private JDialog dialog;
  private Cursor current;
  private Thread t;

  public LongRunningTaskProgressMonitor(ToolCoordinatorFacet coordinator, JFrame frame, PopupMakerFacet popup)
  {
    this.coordinator = coordinator;
    this.frame = frame;
    this.popup = popup;
  }
  
  public void invokeActivityAndMonitorProgress(final Runnable activity)
  {
    // use an app modal dialog to show the status and prevent further input
    dialog = new JDialog(frame, true);
    dialog.setUndecorated(true);
    dialog.setLocation(-100, -100);
    current = coordinator.displayWaitCursor();
    
    dialog.addComponentListener(new ComponentAdapter()
    {
    	private boolean run;
    	
			public void componentShown(ComponentEvent e)
			{
				if (!run)
				{
					run = true;
			    // like swing worker, but a bit simpler...
			    t = new Thread(
			        new Runnable()
			        {
			          public void run()
			          {
			              try
			              {
			                activity.run();
			              }
			              catch (Throwable t)
			              {
			              	t.printStackTrace();
			              	popup.clearPopup();
			              }
			              finally
			              {
			                stopActivity();
			              }
			          }
			        });
			    t.start();
				}
			}
    });

    // bring up the modal dialog to prevent input across the whole application
		dialog.setVisible(true);
  }
  
  public void stopActivity()
  {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				synchronized (LongRunningTaskProgressMonitor.this)
				{
			    if (dialog != null)
			    {
			      dialog.dispose();
			      coordinator.restoreCursor(current); 
			      dialog = null;
			      current = null;
			    }
		    }
			}
		});
  }
  
  /**
   * stop by displaying a dialog.  usually indicates some sort of failure.
   * @param title
   * @param message
   * @param option
   * @param icon
   */
  public void stopActivityAndDisplayDialog(
      final String title,
      final String message,
      final boolean error)
  {
    stopActivity();
		SwingUtilities.invokeLater(
		    new Runnable()
		    {
		      public void run()
		      {
		      	popup.clearPopup();
		      	if (error)
		      		coordinator.invokeErrorDialog(title, message);
		      	else
		      		coordinator.invokeAsDialog(
		        		null,
		            title,
		            new JLabel(message),
		            null,
		            null);
		      }                      
		    });
  }
   
  /** stop by displaying a popup -- usually indicates success
   * @param icon
   * @param title
   * @param message
   * @param color
   * @param msecsToDisplay
   */
  public void stopActivityAndDisplayPopup(
      final Icon icon,
      final String title,
      final String message,
      final Color color,
      final int msecsToDisplayFor,
      boolean showCompletedProgressBar)
  {
  	stopActivity();
    popup.displayPopup(
        icon,
        title,
        message,
        color,
        Color.black,
        msecsToDisplayFor,
        showCompletedProgressBar,
        100,
        100);  
  }

  /** display an interim dialog to show where we are up to
   * @param icon
   * @param title
   * @param message
   * @param color
   * @param msecsToDisplayFor
   */
  public void displayInterimPopup(
      Icon icon,
      String title,
      String message,
      Color color,
      int msecsToDisplayFor)
  {
    popup.displayPopup(
        icon,
        title,
        message,
        color,
        Color.black,
        msecsToDisplayFor);  
  }  

  /** display an interim dialog to show where we are up to
   * @param icon
   * @param title
   * @param message
   * @param color
   * @param msecsToDisplayFor
   */
  public void displayInterimPopupWithProgress(
      Icon icon,
      String title,
      String message,
      Color color,
      int msecsToDisplayFor,
      int currentProgress,
      int maxProgress)
  {
    popup.displayPopup(
        icon,
        title,
        message,
        color,
        Color.black,
        msecsToDisplayFor,
        true,
        currentProgress,
        maxProgress);  
  }

	public void waitForFinish()
	{
		try
		{
			t.join();
		}
		catch (InterruptedException e)
		{
		}
	}
}
