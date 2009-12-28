package ui;

/* a specialised Applet button to launch LTSA
 */

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class AppletButton extends Applet implements Runnable {
	class ButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			triggerWindow();
		}
	}
	Button button;
	Thread windowThread;
	boolean pleaseCreate = false;

	HPWindow window = null;

	public void ended() {
		if (window != null)
			window = null;
	}

	public void init() {

		setLayout(new BorderLayout());
		button = new Button("Launch LTSA");
		button.setFont(new Font("Helvetica", Font.BOLD, 18));
		button.addActionListener(new ButtonAction());
		add("Center", button);
	}

	public synchronized void run() {
		while (windowThread != null) {
			while (pleaseCreate == false) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			pleaseCreate = false;
			try {
				String lf = UIManager.getSystemLookAndFeelClassName();
				UIManager.setLookAndFeel(lf);
			} catch (Exception e) {
			}
			if (window == null) {
				showStatus("Please wait while the window comes up...");
				window = new HPWindow(this);
				window.setTitle("MTS Analyser");
				window.pack();
				HPWindow.centre(window);
				window.setVisible(true);
				showStatus("");
			}
		}
	}

	public void start() {
		if (windowThread == null) {
			windowThread = new Thread(this);
			windowThread.start();
		}
	}

	public void stop() {
		windowThread = null;
		if (window != null)
			window.dispose();
	}

	synchronized void triggerWindow() {
		pleaseCreate = true;
		notify();
	}

}