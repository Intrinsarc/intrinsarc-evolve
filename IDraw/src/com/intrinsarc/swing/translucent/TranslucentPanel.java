/*
 * Created on 02-Nov-2003 by Andrew McVeigh
 */
package com.intrinsarc.swing.translucent;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public class TranslucentPanel extends JPanel
{	
	public TranslucentPanel()
	{
		super();
		setOpaque(false);
	}

	public TranslucentPanel(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
		setOpaque(false);
	}

	public TranslucentPanel(LayoutManager layout)
	{
		super(layout);
		setOpaque(false);
	}

	public TranslucentPanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
		setOpaque(false);
	}
}
