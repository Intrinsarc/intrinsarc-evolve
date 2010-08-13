/*
 * Created on Dec 11, 2003 by Andrew McVeigh
 */
package com.intrinsarc.swing.enhanced;

import java.awt.event.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public abstract class UpdatingJMenuItem extends JMenuItem implements UpdatingMenuItem
{
	public UpdatingJMenuItem(String text)
	{
		super(text);
	}

	public UpdatingJMenuItem(String text, int mnemonic)
	{
		super(text, mnemonic);
	}

	public UpdatingJMenuItem(Action a)
	{
		super(a);
	}

	public UpdatingJMenuItem(Icon icon)
	{
		super(icon);
	}

	public UpdatingJMenuItem(String text, Icon icon)
	{
		super(text, icon);
	}

	protected void alwaysFireActionPerformed(ActionEvent event)
	{
		super.fireActionPerformed(event);
	}
	
	@Override
	protected void fireActionPerformed(ActionEvent event)
	{
		// only fire if it is enabled at the application level
		if (update())
			super.fireActionPerformed(event);
	}
}
