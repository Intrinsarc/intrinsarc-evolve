/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */
package edu.umd.cs.jazz.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.component.*;

/**
 * <b>ZFrame</b> is meant to be subclassed by Jazz applications that just need a ZCanvas in a JFrame.
 * It also includes full screen mode functionality when run in JDK 1.4. To get this functionality you will
 * need to uncomment code in the methods enterFullScreenMode() and exitFullScreenMode().
 *
 * @author: Jesse Grosjean
 */
public class ZFrame extends JFrame {

    /**
     * The ZCanvas, Jazz's connection to swing.
     */
    protected ZCanvas fCanvas;

    /**
     * Construct a new ZFrame. Application specific code should be put in ZFrame.initialize().
     */
    public ZFrame() {
        super();

        setBounds(getDefaultFrameBounds());
        setBackground(null);
        addListenersToWindow();

        fCanvas = new ZCanvas();

        if (getShouldHaveScrollPane()) {
            ZScrollPane scrollPane = new ZScrollPane(fCanvas);
            getContentPane().add(scrollPane);

        } else {
            getContentPane().add(fCanvas);
        }

        validate();
        setVisible(true);

        // Manipulation of the Jazz scene graph should be done from Swings
        // event dispatch thread since Jazz is not thread safe. This code calls
        // initialize() from that thread, so you are safe to do start working with
        // Jazz in the initialize() method.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ZFrame.this.initialize();
            }
        });
    }

    /**
     * Add two listeners to the Window. One to exit the application when the window is closed,
     * and one to exit full screen mode when the escape key is pressed.
     */
    public void addListenersToWindow() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    exitFullScreenMode();
                }
            }
        });
    }

    /**
     * Initialize your application from within the event dispatch thread.
     */
    public void initialize() {
    // Uncoment to display "Hello World".
        //ZText text = new ZText("Hello World");
        //ZVisualLeaf textLeaf = new ZVisualLeaf(text);
        //fCanvas.getLayer().addChild(textLeaf);
    }

    /**
     * Enter full screen mode, this requires JDK 1.4 to compile and run. To stay compatible with
     * JDK 1.3 this code is commented out by default, but if JDK 1.4 is being used the code can
     * be uncommented.
     */
    public void enterFullScreenMode() {
        //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //if (gd.isFullScreenSupported()) {
        //    setUndecorated(true);
        //    setResizable(false);
        //   gd.setFullScreenWindow(this);
        //}
    }

    /**
     * Exit full screen mode, this requires JDK 1.4 to compile and run. To stay compatible with
     * JDK 1.3 this code is commented out by default, but if JDK 1.4 is being used the code can
     * be uncommented.
     */
    public void exitFullScreenMode() {
        //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //if (gd.isFullScreenSupported()) {
        //   gd.setFullScreenWindow(null);
        //   setUndecorated(false);
        //   setResizable(true);
        //}
    }

    /**
     * Return the canvas used by this frame.
     */
	public ZCanvas getCanvas() {
		return fCanvas;
	}

    /**
     * Return the default size for the Frame.
     */
    protected Rectangle getDefaultFrameBounds() {
        return new Rectangle(100, 100, 400, 400);
    }

    /**
     * Return true if the ZCanvas should be wrapped in a ZScrollPane.
     * The default return value is false.
     */
    protected boolean getShouldHaveScrollPane() {
        return false;
    }

    public static void main(String[] argv) {
        new ZFrame();
    }
}