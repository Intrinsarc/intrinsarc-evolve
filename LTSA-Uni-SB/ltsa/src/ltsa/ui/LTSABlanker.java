package ltsa.ui;

import java.awt.*;
import java.awt.event.*;

public class LTSABlanker extends Window {

	private static final long serialVersionUID = 7431091953126554971L;

	final Window thisWindow;

	// SplashScreen's constructor
	public LTSABlanker(Window owner) {
		super(owner);
		thisWindow = this;
		setBackground(Color.black);
		pack();
		// Plonk it on center of screen
		Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, ScreenSize.width, ScreenSize.height);
		this.addMouseListener(new Mouse());
		setVisible(true);
	}

	class Mouse extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			thisWindow.setVisible(false);
			thisWindow.dispose();
		}
	}

}