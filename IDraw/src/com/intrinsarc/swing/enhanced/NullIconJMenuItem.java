package com.intrinsarc.swing.enhanced;

import javax.swing.*;

public class NullIconJMenuItem extends JMenuItem
{
	public NullIconJMenuItem(String name)
	{
		super(name, new NullIcon());
	}
}
