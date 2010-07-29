package com.hopstepjump.easydock.dockingframes;

import java.util.*;

import javax.swing.*;

import bibliothek.gui.dock.common.*;
import bibliothek.gui.dock.common.event.*;
import bibliothek.gui.dock.common.intern.*;

import com.hopstepjump.easydock.*;

public class DockingFramesDockable implements IEasyDockable
{
	private DefaultSingleCDockable dockable;
	private Set<IEasyDockableListener> listeners;
	private DockingFramesDock owner;
	private JComponent component;
	private boolean closed;
	private CDockableAdapter stateListener;
	private CFocusListener focusListener;

	public DockingFramesDockable(DockingFramesDock dockingFramesEasyDock, DefaultSingleCDockable dockable, JComponent component)
	{
		this.owner = dockingFramesEasyDock;
		this.dockable = dockable;
		this.component = component;
	}

	public void addListener(IEasyDockableListener listener)
	{
		if (listeners == null)
		{
			listeners = new HashSet<IEasyDockableListener>();

			dockable.addFocusListener(focusListener = new CFocusListener()
			{
				public void focusGained(CDockable arg0)
				{
					for (IEasyDockableListener l : listeners)
						l.hasFocus();
				}

				public void focusLost(CDockable arg0)
				{
				}
			});

			dockable.addCDockableStateListener(stateListener = new CDockableAdapter()
			{
				@Override
				public void visibilityChanged(CDockable dockable)
				{
					if (!dockable.isVisible())
					{
						for (IEasyDockableListener l : listeners)
						{
							l.hasClosed();
							close();
						}
					}
				}
			});
		}
		listeners.add(listener);
	}

	public void setTitleIcon(Icon icon)
	{
		dockable.setTitleIcon(icon);
	}

	public void setTitleText(String title)
	{
		dockable.setTitleText(title);
	}

	public void close()
	{
		if (!closed)
		{
			closed = true;
			dockable.getControl().createCloseAction(dockable).trigger(dockable.intern());
			if (focusListener != null)
				dockable.removeFocusListener(focusListener);
			if (stateListener != null)
				dockable.removeCDockableStateListener(stateListener);
			owner.removeMe(this);
		}
	}

	public void restore()
	{
		dockable.toFront();
	}
	
	public DefaultSingleCDockable getDockable()
	{
		return dockable;
	}

	public JComponent getComponent()
	{
		return component;
	}
}
