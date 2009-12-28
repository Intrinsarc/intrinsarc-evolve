package com.hopstepjump.easydock.vlsolutions;


public class VLDock {}
	/*implements IEasyDock
}
{
	public static final ImageIcon ICON = IconLoader.loadIcon("bug.png");

	private JFrame frame;
	private int id;
	private DockingDesktop desk;
	private Dockable workArea = new Dockable()
	{
		private JPanel panel = new JPanel();
		public Component getComponent()
		{
			return panel;
		}

		public DockKey getDockKey()
		{
			return new DockKey("workarea");
		}
	};
	private DockGroup palette = new DockGroup("palette");
	private DockGroup tabs = new DockGroup("tabs");
	private DockGroup others = new DockGroup("others");
	private List<IEasyDockable> remembered = new ArrayList<IEasyDockable>();

	static
	{
    DockingUISettings.getInstance().installUI();
		UIManager.put("DockViewTitleBar.float.hide", ICON);
		UIManager.put("DockViewTitleBar.float.rollover", ICON);
		UIManager.put("DockViewTitleBar.float.pressed", ICON);
		
		UIManager.put("DockViewTitleBar.float", null);
		UIManager.put("DockViewTitleBar.float.rollover", null);
		UIManager.put("DockViewTitleBar.float.pressed", null);
	}
	
	public VLDock(final JFrame frame, boolean disallowNorthDock)
	{
		this.frame = frame;

		// force eager instantiation of the content area to avoid an ordering problem
		desk = new DockingDesktop();
		desk.addDockable(workArea);

		frame.add(desk);
	}
	
	public void closeRememberedDockables()
	{
		for (IEasyDockable dockable : new ArrayList<IEasyDockable>(remembered))
			dockable.close();
	}
	
	// finishSetup() must be called after the frame has been made visible
	public void finishSetup()
	{
	}
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////	
	
	void removeMe(DockingFramesDockable dockable)
	{
		remembered.remove(dockable);
	}

	public IEasyDockable createEmbeddedPaletteDockable(
			final String title,
			Icon icon,
			EasyDockSideEnum side,
			Dimension size,
			boolean canClose,
			boolean remember,
			final JComponent component)
	{
		VLDockable dockable = new VLDockable(this, palette, component, true, title, icon, canClose);
		switch (side)
		{
		case NORTH:
			desk.split(workArea, dockable, DockingConstants.SPLIT_TOP, size.height / (double) frame.getHeight());
			break;
		case SOUTH:
			desk.split(workArea, dockable, DockingConstants.SPLIT_BOTTOM, size.height / (double) frame.getHeight());
			break;
		case EAST:
			desk.split(workArea, dockable, DockingConstants.SPLIT_RIGHT, size.width / (double) frame.getWidth());
			break;
		case WEST:
			desk.split(workArea, dockable, DockingConstants.SPLIT_LEFT, size.width / (double) frame.getWidth());
			break;
		}
		return dockable;
	}

	public IEasyDockable createExternalPaletteDockable(
			String title,
			Icon icon,
			final Point location,
			Dimension size,
			boolean canClose,
			boolean remember,
			final JComponent component)
	{
		final VLDockable dockable = new VLDockable(this, others, component, false, title, icon, canClose);
		desk.setFloating(dockable, true, location);
		desk.addDockable(dockable);
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				desk.setFloating(dockable, true, location);
				desk.setDockableHeight(dockable, 100);
				desk.setDockableWidth(dockable, 100);
				System.out.println("$$ made floating dockable");				
			}
		});

		return dockable;
	}

	public IEasyDockable createWorkspaceDockable(
			String title,
			Icon icon,
			boolean addAsTab,
			boolean remember,
			final JComponent component)
	{
		VLDockable dockable = new VLDockable(this, tabs, component, false, title, icon, true);
		desk.createTab(workArea, dockable, 1, true);
		return dockable;
	}
}*/
