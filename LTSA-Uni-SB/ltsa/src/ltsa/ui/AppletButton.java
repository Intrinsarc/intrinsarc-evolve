package ltsa.ui;

/* a specialised Applet button to launch LTSA
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AppletButton extends Applet implements Runnable {
	private static final long serialVersionUID = 3774107038027030060L;

	Button button;
	Thread windowThread;
	boolean pleaseCreate = false;
	HPWindow window = null;

	@Override
	public void init() {
		setLayout(new BorderLayout());
		button = new Button("Launch LTSA");
		button.setFont(new Font("Helvetica", Font.BOLD, 18));
		button.addActionListener(new ButtonAction());
		add("Center", button);
	}

	@Override
	public void start() {
		if (windowThread == null) {
			windowThread = new Thread(this);
			windowThread.start();
		}
	}

	@Override
	public void stop() {
		windowThread = null;
		if (window != null)
			window.dispose();
	}

	public void ended() {
		if (window != null)
			window = null;
	}

	public synchronized void run() {
		while (windowThread != null) {
			while (pleaseCreate == false) {
				try {
					wait();
				} catch (final InterruptedException e) {
					// ignore
				}
			}
			pleaseCreate = false;
			try {
				final String lf = UIManager.getSystemLookAndFeelClassName();
				UIManager.setLookAndFeel(lf);
			} catch (final Exception e) {
				// ignore
			}
			if (window == null) {
				showStatus("Please wait while the window comes up...");
				window = new HPWindow(this);
				window.setTitle("LTS Analyser");
				window.pack();
				HPWindow.centre(window);
				window.setVisible(true);
				showStatus("");
			}
		}
	}

	synchronized void triggerWindow() {
		pleaseCreate = true;
		notify();
	}

	class ButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			triggerWindow();
		}
	}

}