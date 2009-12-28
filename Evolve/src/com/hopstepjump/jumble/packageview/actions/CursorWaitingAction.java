/*
 * Created on Dec 22, 2003 by Andrew McVeigh
 */
package com.hopstepjump.jumble.packageview.actions;

import java.awt.event.*;
import java.beans.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.idraw.utility.*;

/**
 * Shows an hour glass for the duration of the command
 * @author Andrew
 */
public class CursorWaitingAction implements Action
{
	private int waitMsecs;
	private ToolCoordinatorFacet toolCoordinator;
	private Action mainAction;

	public CursorWaitingAction(Action mainAction, ToolCoordinatorFacet toolCoordinator, int waitMsecs)
	{
		this.mainAction = mainAction;
		this.toolCoordinator = toolCoordinator;
		this.waitMsecs = waitMsecs;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		mainAction.addPropertyChangeListener(listener);
	}

	public Object getValue(String key)
	{
		return mainAction.getValue(key);
	}

	public boolean isEnabled()
	{
		return mainAction.isEnabled();
	}

	public void putValue(String key, Object value)
	{
		mainAction.putValue(key, value);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		mainAction.removePropertyChangeListener(listener);
	}

	public void setEnabled(boolean b)
	{
		mainAction.setEnabled(b);
	}

	public void actionPerformed(ActionEvent e)
	{
		WaitCursorDisplayer waiter = new WaitCursorDisplayer(toolCoordinator, waitMsecs);
		waiter.displayWaitCursorAfterDelay();
		mainAction.actionPerformed(e);
		waiter.restoreOldCursor();
	}
}
