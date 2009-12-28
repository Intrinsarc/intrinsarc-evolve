package com.hopstepjump.swing.enhanced;

import javax.swing.*;

public abstract class UpdatingJRadioButtonMenuItem extends JRadioButtonMenuItem implements UpdatingMenuItem
{
	public UpdatingJRadioButtonMenuItem(String text, ButtonGroup group)
	{
		super(text);
		group.add(this);
	}

	public UpdatingJRadioButtonMenuItem(Action a, ButtonGroup group)
	{
		super(a);
		group.add(this);
	}

	public UpdatingJRadioButtonMenuItem(Icon icon, ButtonGroup group)
	{
		super(icon);
		group.add(this);
	}

	public UpdatingJRadioButtonMenuItem(String text, Icon icon, ButtonGroup group)
	{
		super(text, icon);
		group.add(this);
	}
}
