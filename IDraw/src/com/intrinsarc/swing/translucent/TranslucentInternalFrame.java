/*
 * Created on 02-Nov-2003 by Andrew McVeigh
 */
package com.intrinsarc.swing.translucent;

import java.awt.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public class TranslucentInternalFrame extends JInternalFrame
{
	static final float ALPHA_VALUE = 0.9f;
	static final int ALPHA_RULE = AlphaComposite.SRC_OVER;
	
	private float alphaValue = ALPHA_VALUE;

	public TranslucentInternalFrame()
	{
		super();
		makeTranslucent();
	}

  public TranslucentInternalFrame(String title)
	{
		super(title);
		makeTranslucent();
	}

	public TranslucentInternalFrame(String title, boolean resizable)
	{
		super(title, resizable);
		makeTranslucent();
	}

	public TranslucentInternalFrame(String title, boolean resizable, boolean closable)
	{
		super(title, resizable, closable);
		makeTranslucent();
	}

	public TranslucentInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable)
	{
		super(title, resizable, closable, maximizable);
		makeTranslucent();
	}

	public TranslucentInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable)
	{
		super(title, resizable, closable, maximizable, iconifiable);
		makeTranslucent();
	}

	public void makeTranslucent()
	{
		setOpaque(false);
	}
  
  public void makeOpaque()
  {
    setOpaque(true);
  }

	public void paint(Graphics g)
	{
    Graphics2D g2d = (Graphics2D) g;
    if (!isOpaque())
      g2d.setComposite(AlphaComposite.getInstance(ALPHA_RULE, alphaValue));
    super.paint(g);
  }

	public void setAlphaValue(double d)
	{
		alphaValue = (float) d;
	}
}
