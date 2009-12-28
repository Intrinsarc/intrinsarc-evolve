package com.hopstepjump.swing.enhanced;

import javax.swing.*;

public abstract class UpdatingJCheckBoxMenuItem extends JCheckBoxMenuItem implements UpdatingMenuItem
{
	public UpdatingJCheckBoxMenuItem(String text)
	{
		super(text);
	}

	public UpdatingJCheckBoxMenuItem(Action a)
	{
		super(a);
	}

	public UpdatingJCheckBoxMenuItem(Icon icon)
	{
		super(icon);
	}

	public UpdatingJCheckBoxMenuItem(String text, Icon icon)
	{
		super(text, icon);
	}
}
