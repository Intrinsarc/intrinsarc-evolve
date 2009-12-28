/*
 * Created on Dec 22, 2003 by Andrew McVeigh
 */
package com.hopstepjump.idraw.utility;

import java.awt.*;

import com.hopstepjump.idraw.foundation.*;


public class WaitCursorDisplayer implements Runnable
{
	private final ToolCoordinatorFacet toolCoordinator;
	private int msecsToWait;
	private boolean restored;
	private Cursor currentCursor;
	
	public WaitCursorDisplayer(ToolCoordinatorFacet toolCoordinator, int msecsToWaitBeforeChangingCursor)
	{
		msecsToWait = msecsToWaitBeforeChangingCursor;
		this.toolCoordinator = toolCoordinator;
	}
	
	public synchronized void displayWaitCursorAfterDelay()
	{
		if (msecsToWait == 0)
			currentCursor = this.toolCoordinator.displayWaitCursor();
		else
			new Thread(this).start();
	}

	public void run()
	{
		try
		{
			Thread.sleep(msecsToWait);
		}
		catch (InterruptedException ex)
		{
		}
		
		synchronized (this)
		{
			if (!restored)
				currentCursor = toolCoordinator.displayWaitCursor();
		}				
	}
	
	public synchronized void restoreOldCursor()
	{
		restored = true;
		if (currentCursor != null)
			toolCoordinator.restoreCursor(currentCursor);
	}			
}