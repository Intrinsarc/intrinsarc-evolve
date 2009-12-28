package com.hopstepjump.easydock.vlsolutions;


public class VLDockable {}
/*implements IEasyDockable, Dockable
{
	private VLDock dock;
	private JComponent component;
	private DockKey key = new DockKey(UUID.randomUUID().toString());

	public VLDockable(VLDock dock, DockGroup group, JComponent component, boolean palette, String title, Icon icon, boolean canClose)
	{
		this.dock = dock;
		this.component = component;
		key.setFloatEnabled(true);
		key.setDockGroup(group);
		if (palette)
		{
			key.setMaximizeEnabled(false);
			key.setResizeWeight(0);
		}
		else
		{
			key.setResizeWeight(1);
		}
		key.setCloseEnabled(canClose);
		setTitleText(title);
		setTitleIcon(icon);
	}

	public void addListener(IEasyDockableListener listener)
	{
	}

	public void close()
	{
	}

	public void restore()
	{
		// TODO Auto-generated method stub
		
	}

	public void setTitleIcon(Icon icon)
	{
		key.setIcon(icon);
	}

	public void setTitleText(String title)
	{
		key.setName(title);
	}

	public Component getComponent()
	{
		return component;
	}

	public DockKey getDockKey()
	{
		return key;
	}
}
*/