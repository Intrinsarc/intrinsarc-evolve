/*
 * Created on 02-Nov-2003 by Andrew McVeigh
 */
package com.intrinsarc.swing.translucent;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public class TranslucentSplitPane extends JSplitPane
{
	private Color backdrop = new Color(0, 0, 0, 0);

	public TranslucentSplitPane()
	{
		super();
		setOpaque(false);
	}

	public TranslucentSplitPane(int newOrientation)
	{
		super(newOrientation);
		setOpaque(false);
	}

	public TranslucentSplitPane(int newOrientation, boolean newContinuousLayout)
	{
		super(newOrientation, newContinuousLayout);
		setOpaque(false);
	}

	public TranslucentSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent)
	{
		super(newOrientation, newLeftComponent, newRightComponent);
		setOpaque(false);
	}

	public TranslucentSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent, Component newRightComponent)
	{
		super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
		setOpaque(false);
	}

	public void setBackdrop(Color color)
	{
		backdrop = color;
		repaint();
	}

}
