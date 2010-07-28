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
	private boolean closed;

	public DockingFramesDockable(DockingFramesDock dockingFramesEasyDock, DefaultSingleCDockable dockable)
	{
		this.owner = dockingFramesEasyDock;
		this.dockable = dockable;
	}

	public void addListener(IEasyDockableListener listener)
	{
		if (listeners == null)
		{
			listeners = new HashSet<IEasyDockableListener>();

			dockable.addFocusListener(new CFocusListener()
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

			dockable.addCDockableStateListener(new CDockableAdapter()
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
}
