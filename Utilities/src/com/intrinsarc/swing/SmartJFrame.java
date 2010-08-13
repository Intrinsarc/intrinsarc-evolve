package com.intrinsarc.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * handles fullscreen with menu hiding functionality
 */
public class SmartJFrame extends JFrame
{
	private JFrame inner = new JFrame();
	private JViewport view = new JViewport();
	private boolean fullScreen;
	private int oldState;
	private Point oldPoint;
	private Dimension oldSize;
	private Dimension oldInnerSize;
	private boolean showingTop;
	private ComponentListener offsetDeterminer;
	private int fullScreenScrollDown;

	public SmartJFrame(String title)
	{
		super(title);
		view.add(inner.getRootPane());
		super.add(view);
	}
	
	public void setFullScreenScrollDown(int fullScreenScrollDown)
	{
		this.fullScreenScrollDown = fullScreenScrollDown;
	}
	
	@Override
	public void setJMenuBar(JMenuBar menubar)
	{
		inner.setJMenuBar(menubar);
	}
	
	@Override
	public Component add(Component component)
	{
		return inner.add(component);
	}
	
	public void toggleFullScreen()
	{
		if (!fullScreen)
		{
			final Rectangle fullSize = getGraphicsConfiguration().getBounds();
			offsetDeterminer =
				new ComponentAdapter()
				{
					@Override
					public void componentResized(ComponentEvent e)
					{
						new Thread()
						{
							public void run()
							{
								try
								{
									Thread.sleep(250);
								}
								catch (InterruptedException e)
								{
								}
								SwingUtilities.invokeLater(new Runnable()
								{
									public void run()
									{
										view.setViewPosition(new Point(0, fullScreenScrollDown));
									}
								});
							}
						}.start();
					}
				};
			inner.getRootPane().addComponentListener(offsetDeterminer);
			inner.getRootPane().setPreferredSize(new Dimension(fullSize.width, fullSize.height + fullScreenScrollDown - 1));
			
			
			// save away the location etc
			oldState = getExtendedState();
			oldPoint = getLocation();
			oldSize = getSize();
			oldInnerSize = inner.getRootPane().getSize();
	
			setVisible(false);
			dispose();
			setUndecorated(true);
			setVisible(true);
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					setExtendedState(Frame.MAXIMIZED_BOTH);
					try
					{
						Thread.sleep(500);
					}
					catch (InterruptedException e)
					{
					}
					setResizable(false);
				}
			});
			Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener()
			{
				public void eventDispatched(AWTEvent event)
				{
					if (!fullScreen)
						return;
					Component c = getRootComponent(event); 
					if (c instanceof JPopupMenu)
						return;
					if (c != inner.getRootPane())
						return;
					
					// get the top level frame...
					MouseEvent e = (MouseEvent) event;
					Component src = (Component) e.getSource();
					int y = e.getY() + src.getLocationOnScreen().y;
					if (!showingTop)
					{
						if (y <= 2 && !showingTop)
						{
							showingTop = true;
							view.setViewPosition(new Point(0, 0));
						}
					}
					else
					{
						// use a schmitt trigger
						if (y >= fullScreenScrollDown * 2 && showingTop)
						{
							showingTop = false;
							view.setViewPosition(new Point(0, fullScreenScrollDown));
						}
					}
				}
			}, AWTEvent.MOUSE_MOTION_EVENT_MASK);
		}
		else
		{
			inner.getRootPane().removeComponentListener(offsetDeterminer);
			view.setViewPosition(new Point(0, 0));
			setVisible(false);
			dispose();
			setUndecorated(false);
			setResizable(true);
			setExtendedState(oldState);
			setLocation(oldPoint);
			setSize(oldSize);
			inner.getRootPane().setPreferredSize(oldInnerSize);
			setVisible(true);
		}
		fullScreen = !fullScreen;
	}
	
	private Component getRootComponent(AWTEvent event)
	{
		Component c = (Component) event.getSource();
		for (; c != null; c = c.getParent())
		{
			if (c instanceof JPopupMenu)
				return c;
			if (c instanceof JRootPane)
				return c;
		}
		
		return null;
	}				
}				
