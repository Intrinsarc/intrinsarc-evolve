package com.intrinsarc.swing;

import java.awt.event.*;

import javax.swing.*;

// simple text field which is restricted to integers only

public class JIntegerTextField extends JTextField
{
	public JIntegerTextField(int val)
	{
		setText("" + val);
		addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				char c = e.getKeyChar();
				if (!((Character.isDigit(c) ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))))
				{
					getToolkit().beep();
					e.consume();
				}
			}
			});
	}
}