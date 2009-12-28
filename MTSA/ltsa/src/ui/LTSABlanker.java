package ui;

import java.awt.*;
import java.awt.event.*;

public class LTSABlanker extends Window {
	class Mouse extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			thisWindow.setVisible(false);
			thisWindow.dispose();
		}
	}

	final Window thisWindow;

	// SplashScreen's constructor
	public LTSABlanker(Window owner) {
		super(owner);
		thisWindow = this;
		setBackground(Color.black);
		pack();
		// Plonk it on center of screen
		Dimension WindowSize = getSize(), ScreenSize = Toolkit
				.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, ScreenSize.width, ScreenSize.height);
		this.addMouseListener(new Mouse());
		setVisible(true);
	}

}