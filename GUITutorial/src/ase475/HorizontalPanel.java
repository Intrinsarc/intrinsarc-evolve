package ase475;

import javax.swing.*;

public class HorizontalPanel extends JPanel
{
	public HorizontalPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
}
