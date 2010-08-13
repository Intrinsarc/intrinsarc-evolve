/*
 * Created on 02-Nov-2003 by Andrew McVeigh
 */
package com.intrinsarc.swing.translucent;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public class TranslucentScrollPane extends JScrollPane
{
	private Color backdrop = new Color(0, 0, 0, 0);

	public TranslucentScrollPane()
	{
		super();
		makeTranslucent();
	}

	public TranslucentScrollPane(int vsbPolicy, int hsbPolicy)
	{
		super(vsbPolicy, hsbPolicy);
		makeTranslucent();
	}

	public TranslucentScrollPane(Component view)
	{
		super(view);
		makeTranslucent();
	}

	public TranslucentScrollPane(Component view, int vsbPolicy, int hsbPolicy)
	{
		super(view, vsbPolicy, hsbPolicy);
		makeTranslucent();
	}

	private void makeTranslucent()
	{
		setOpaque(false);
		getViewport().setOpaque(false);
		getVerticalScrollBar().setOpaque(false);
		getHorizontalScrollBar().setOpaque(false);
	}

	public void paint(Graphics g)
	{
		/*Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(
			TranslucentInternalFrame.ALPHA_RULE, TranslucentInternalFrame.ALPHA_VALUE));
		g2d.setColor(backdrop);
		g2d.fillRect(0, 0, getWidth(), getHeight());*/
		super.paint(g);
	}

	public void setBackdrop(Color color)
	{
		backdrop = color;
		repaint();
	}
}
