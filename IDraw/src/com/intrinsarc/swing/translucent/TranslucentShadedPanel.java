/*
 * Created on 02-Nov-2003 by Andrew McVeigh
 */
package com.intrinsarc.swing.translucent;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public class TranslucentShadedPanel extends JPanel
{
	private Color backdrop = new Color(0, 0, 0, 0);

	public TranslucentShadedPanel()
	{
		super();
		setOpaque(false);
	}

	public TranslucentShadedPanel(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
		setOpaque(false);
	}

	public TranslucentShadedPanel(LayoutManager layout)
	{
		super(layout);
		setOpaque(false);
	}

	public TranslucentShadedPanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
		setOpaque(false);
	}

	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(backdrop);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
	}

	public void setBackdrop(Color color)
	{
		backdrop = color;
		repaint();
	}
}
