package ase475;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class VerticalPanel extends JPanel
{
	public VerticalPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void setDrawBorder(boolean draw)
	{
		if (!draw)
			setBorder(null);
		else
			setBorder(
					new CompoundBorder(
							new EmptyBorder(new Insets(5, 5, 5, 5)),
							BorderFactory.createLineBorder(Color.BLUE, 5)));
	}
}
