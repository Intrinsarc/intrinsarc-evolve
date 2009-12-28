/**
 * Copyright 2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * This class should be extended by example programs that will be launched by
 * the ExampleRunner application. @see EventExample as an example subclass of
 * this class.
 *
 * @see ExampleRunner
 * @author: Jesse Grosjean
 */
public abstract class AbstractExample extends JFrame {
    protected boolean hasBeenInitialized = false;

    public AbstractExample(String title) {
        setTitle(title);
    }

    public abstract String getExampleDescription();

    public void hideExample() {
        setVisible(false);
    }

    public void initializeExample() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                hideExample();
            }
        });
        setIconImage(loadFrameIcon());
    }

    private Image loadFrameIcon() {
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        try {
            java.net.URL url= getClass().getResource("jazzlogo.gif");
            return toolkit.createImage((ImageProducer) url.getContent());
        } catch (Exception ex) {
        }
        return null;
    }

    public void showExample() {
        if (!hasBeenInitialized) {
            initializeExample();
            hasBeenInitialized = true;
        }
        setVisible(true);
    }

    public String toString() {
        return getTitle();
    }
}