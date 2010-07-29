package com.hopstepjump.easydock.dockingframes;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import bibliothek.extension.gui.dock.theme.*;
import bibliothek.gui.*;
import bibliothek.gui.dock.*;
import bibliothek.gui.dock.common.*;
import bibliothek.gui.dock.common.action.predefined.*;
import bibliothek.gui.dock.common.intern.*;
import bibliothek.gui.dock.control.*;
import bibliothek.gui.dock.event.*;
import bibliothek.gui.dock.title.*;

import com.hopstepjump.easydock.*;


public class DockingFramesDock implements IEasyDock
{
	private JFrame frame;
	private int id;
	private CControl control;
	private CWorkingArea area;
	private DefaultSingleCDockable firstWorkspaceDockable;
	private List<IEasyDockable> remembered = new ArrayList<IEasyDockable>();

	public DockingFramesDock(final JFrame frame, boolean disallowNorthDock)
	{
		this.frame = frame;
		control = new CControl(frame);
    CContentArea area2 = control.getContentArea();
    // disallow use of north palette dock
    control.intern().removeRoot( control.intern().getRoot( area2.getNorthIdentifier() ) );
    
		// force eager instantiation of the content area to avoid an ordering problem
		frame.add(control.getContentArea());
		area = control.createWorkingArea("area");
		area.setLocation(CLocation.base().normalRectangle(0, 0, 1, 1));
		area.setVisible(true);
		setEclipseTheme(control);
		control.putProperty(FlapDockStation.BUTTON_CONTENT, FlapDockStation.ButtonContent.ICON_AND_TEXT_ONLY);		
		control.putProperty(CControl.KEY_CLOSE, null);		
	}
	
	public void closeRememberedDockables()
	{
		for (IEasyDockable dockable : new ArrayList<IEasyDockable>(remembered))
			dockable.close();
		firstWorkspaceDockable = null;
	}
	
	/** finishSetup() must be called after the frame has been made visible */
	public void finishSetup()
	{
		// save the first workspace dockable, so we can focus it later...
		if (firstWorkspaceDockable != null)
			firstWorkspaceDockable.toFront();

		// this is required to repaint the screen so it doesn't have visual glitches sometimes
		// but it still doesn't work well with metal lnf
		frame.addComponentListener(new ComponentAdapter()
		{
			public void componentShown(ComponentEvent e)
			{
				try
				{
					Thread.sleep(400);
					frame.repaint();
				}
				catch (Exception e1)
				{
				}
			}			
		});
	}
	
	public IEasyDockable createWorkspaceDockable(String title, Icon icon, boolean addAsTab, boolean remember, JComponent component)
	{
		final DefaultSingleCDockable dockable = new DefaultSingleCDockable("" + id++, title, component);
		removeDefaultDockableActions(dockable);
		component.setMinimumSize(new Dimension(100, 100));
		
		DockingFramesDockable wrapper = new DockingFramesDockable(this, dockable, component);
		
		// do we want to remember this for closing later?
		if (remember)
			remembered.add(wrapper);			

		dockable.setCloseable(true);
		dockable.setTitleIcon(icon);
		dockable.setMinimizable(true);
		
		control.add(dockable);
		dockable.setWorkingArea(area);
		dockable.setVisible(true);
		
		if (addAsTab)
			setDockableLocation(dockable, CLocation.working(area));
		if (firstWorkspaceDockable == null)
			firstWorkspaceDockable = dockable;
		
		return wrapper;
	}
	
	void removeMe(DockingFramesDockable wrapper)
	{
		remembered.remove(wrapper);
		DefaultSingleCDockable dockable = wrapper.getDockable();
		control.remove(dockable);
		JComponent component = wrapper.getComponent();
		dockable.remove(component);
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////	
	
	private void setDockableLocation(final DefaultSingleCDockable dockable, final CLocation location)
	{
		dockable.setLocation(location);
	}
	
	private void setEclipseTheme(CControl control)
	{
		control.setTheme("eclipse");
		control.putProperty(EclipseTheme.TAB_PAINTER, DockingFramesRectGradientPainter.FACTORY);
	}

	private void removeDefaultDockableActions(DefaultSingleCDockable dockable)
	{
		dockable.putAction(CDockable.ACTION_KEY_MINIMIZE, CBlank.BLANK);
		dockable.putAction(CDockable.ACTION_KEY_MAXIMIZE, CBlank.BLANK);
		dockable.putAction(CDockable.ACTION_KEY_NORMALIZE, CBlank.BLANK);
		dockable.putAction(CDockable.ACTION_KEY_EXTERNALIZE, CBlank.BLANK);
	}

	public IEasyDockable createEmbeddedPaletteDockable(
			String title,
			Icon icon,
			EasyDockSideEnum side,
			Dimension size,
			boolean canClose,
			boolean remember,
			JComponent component)
	{
		CLocation loc = null;
		switch(side)
		{
		case NORTH:
			loc = CLocation.base().normalNorth(0.2);
			break;
		case SOUTH:
			loc = CLocation.base().normalSouth(0.2);
			break;		
		case EAST:
			loc = CLocation.base().normalEast(0.2);
			break;
		case WEST:
			loc = CLocation.base().normalWest(0.2);
			break;
		}
		return createPaletteDockable(title, icon, size, canClose, loc, remember, component);
	}

	public IEasyDockable createExternalPaletteDockable(
			String title,
			Icon icon,
			Point location,
			Dimension size,
			boolean canClose,
			boolean remember,
			JComponent component)
	{
		return createPaletteDockable(title, icon, size, canClose, CLocation.external(location.x, location.y, size.width, size.height), remember, component);
	}
	
	private IEasyDockable createPaletteDockable(String title, Icon icon, Dimension size, boolean canClose, CLocation location, boolean remember, JComponent component)
	{
		final DefaultSingleCDockable dockable = new DefaultSingleCDockable("" + id++, title, component);
		removeDefaultDockableActions(dockable);
		component.setMinimumSize(new Dimension(100, 100));
		
		// do we want to remember this for closing later?
		DockingFramesDockable wrapper = new DockingFramesDockable(this, dockable, component);
		if (remember)
			remembered.add(wrapper);			

		dockable.setTitleIcon(icon);
		dockable.setResizeLocked(true);		
		setDockableLocation(dockable, location);
		control.add(dockable);

		dockable.setVisible(true);
		dockable.setCloseable(canClose);
		dockable.setMaximizable(false);
		
		control.intern().getController().getFocusObserver().addVetoListener(
			new FocusVetoListener()
			{
				public boolean vetoFocus(MouseFocusObserver controller, Dockable simpleDockable)
				{
					return simpleDockable == dockable.intern();
				}

				public boolean vetoFocus(MouseFocusObserver controller, DockTitle title)
				{
					return title.getDockable() == dockable.intern();
				}
			});
		
		// possibly resize
		if (size != null)
			dockable.setResizeRequest(size, false);
		
		return wrapper;
	}
}
