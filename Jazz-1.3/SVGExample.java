/**
 * Copyright (C) 1998-2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import edu.umd.cs.jazz.*;
import edu.umd.cs.jazz.util.*;

/**
 *      SVG (Scalable Vector Graphics) is a new XML standard file format for vector graphics.
 *      This is an example that will load any SVG file.
 */
public class SVGExample extends AbstractExample {

    // This applets Jazz Canvas
    ZCanvas canvas = null;

    JFileChooser fileChooser;
    java.net.URL resource;

    /**
     * Default Constructor
     */
    public SVGExample() {
        super("SVG Example");
    }

    public String getExampleDescription() {
        return "SVG (Scalable Vector Graphics) is a new XML standard file format for vector graphics.\n"
                +"This is an example that will load any SVG file.\n"
                +"You will see a floor plan as an initial example. This file (hcil.svg) is included in examples.jar";
    }

    public void initializeExample() {
        super.initializeExample();

        // Set up basic frame
        setBounds(100, 100, 800, 600);
        setResizable(true);
        setVisible(true);
        setBackground(null);

        canvas = new ZCanvas();
        canvas.setNavEventHandlersActive(true);
        getContentPane().add(canvas);

        canvas.getCamera().getDrawingSurface().setRenderQuality(
                              ZDrawingSurface.RENDER_QUALITY_HIGH);


        fileChooser = new JFileChooser("./examples/src/");
        fileChooser.addChoosableFileFilter(new SVGFilter());

        // Create and set the menu bar
        JMenuBar menuBar = createJMenuBar();
        setJMenuBar(menuBar);

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                canvas.requestFocus();
            }
        });

        // Load initial demo svg file: hcil.svg

       // ZSVG svg = new ZSVG();
        resource = this.getClass().getClassLoader().getResource("hcil.svg");
      //  ZGroup group = svg.read(resource.toString());
        //canvas.getLayer().addChild(group);

        setVisible(true);
        validate();
    }

    public static void main(String args[]) {
        SVGExample app = new SVGExample();
        app.initializeExample();
        app.show();
    }

    /**
     * Create the MenuBar
     */
    public JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        // The 'File' Menu
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        menuBar.add(file);
        // The 'Open XML File' Menu Item
        JMenuItem xml = new JMenuItem("Open SVG File");
        xml.setMnemonic('O');
        xml.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    openSVG();
                }
            });
        file.add(xml);

        // The 'Quit' Menu Item
        JMenuItem quit = new JMenuItem("Quit");
        quit.setMnemonic('Q');
        quit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    System.exit(0);
                }
            });
        file.add(quit);

        return menuBar;
    }


/*
    public void saveAsSVG() {
        ZDebug.dump(canvas.getRoot());
    }
*/

    /**
      *  This method contains the routine that uses ZSVG class.
      *  ZSVG svg = new ZSVG();         // initialize
      *  ZGroup group = svg.read(file); // load the objects into group
      *  ...
      *  canvas.getLayer().addChild(group);     // add the group into jazz canvas
      */
    public void openSVG() {
        File file = null;

        int retval = fileChooser.showDialog(canvas, "Open SVG File");
        if (retval == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        if(file == null)
            return;

        // Clear screen
        canvas.getLayer().removeAllChildren();

        //
        // These two lines of codes load SVG file into Jazz ZGroup
        //
     //   ZSVG svg = new ZSVG();
        //ZGroup group = svg.read(file);

        //canvas.getLayer().addChild(group);
    }


    private class SVGFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            if (extension != null && extension.equalsIgnoreCase("svg"))
                return true;
            return false;
        }

        public String getDescription() {
            return "SVG Files";
        }

        public String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');
            if (i > 0 &&  i < s.length() - 1) {
                ext = s.substring(i+1).toLowerCase();
            }
            return ext;
        }
    }
}