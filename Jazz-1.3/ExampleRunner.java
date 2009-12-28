/**
 * Copyright 2000 by University of Maryland, College Park, MD 20742, USA
 * All rights reserved.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;

import javax.swing.*;

/**
 * This class provides a simple UI for running Jazz examples. To add a new
 * example you should edit the method createExampleList().
 *
 * @see AbstractExample
 * @author: Jesse Grosjean
 */
class ExampleRunner extends JFrame {
    private JMenuBar ivjExampleRunner2JMenuBar = null;
    private JPanel ivjJFrameContentPane = null;
    private JList ivjExampleListJList = null;
    private JScrollPane ivjExampleListJScrollPane = null;
    private JScrollPane ivjExampleTextJScrollPane = null;
    private JTextPane ivjExampleTextJTextPane = null;
    private JMenu ivjFileJMenu = null;
    private JMenuItem ivjQuitJMenuItem = null;
    private JButton ivjRunExampleJButton = null;
    private JSplitPane ivjContainingJSplitPane = null;
    private JMenuItem ivjJMenuItem1 = null;
    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener, java.awt.event.WindowListener, javax.swing.event.ListSelectionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ExampleRunner.this.getQuitJMenuItem())
                    connEtoC1(e);
            if (e.getSource() == ExampleRunner.this.getRunExampleJButton())
                    connEtoC4(e);
            if (e.getSource() == ExampleRunner.this.getJMenuItem1())
                    connEtoC5(e);
        };
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
            if (e.getSource() == ExampleRunner.this.getExampleListJList())
                    connEtoC3(e);
        };
        public void windowActivated(java.awt.event.WindowEvent e) {};
        public void windowClosed(java.awt.event.WindowEvent e) {
            if (e.getSource() == ExampleRunner.this)
                    connEtoC2(e);
        };
        public void windowClosing(java.awt.event.WindowEvent e) {};
        public void windowDeactivated(java.awt.event.WindowEvent e) {};
        public void windowDeiconified(java.awt.event.WindowEvent e) {};
        public void windowIconified(java.awt.event.WindowEvent e) {};
        public void windowOpened(java.awt.event.WindowEvent e) {};
    };

    public ExampleRunner() {
        super();
        initialize();
    }

    /**
     * connEtoC1:  (QuitJMenuItem.action.actionPerformed(java.awt.event.ActionEvent) --> ExampleRunner2.exitExampleRunner()V)
     * @param arg1 java.awt.event.ActionEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.awt.event.ActionEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.exitExampleRunner();
            // user code begin {2}
            // user code end
        } catch (java.lang.Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2:  (ExampleRunner2.window.windowClosed(java.awt.event.WindowEvent) --> ExampleRunner2.exitExampleRunner()V)
     * @param arg1 java.awt.event.WindowEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.WindowEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.exitExampleRunner();
            // user code begin {2}
            // user code end
        } catch (java.lang.Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3:  (ExampleListJList.listSelection.valueChanged(javax.swing.event.ListSelectionEvent) --> ExampleRunner2.updateExampleDescription()V)
     * @param arg1 javax.swing.event.ListSelectionEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3(javax.swing.event.ListSelectionEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.updateExampleDescription();
            // user code begin {2}
            // user code end
        } catch (java.lang.Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4:  (RunExampleJButton.action.actionPerformed(java.awt.event.ActionEvent) --> ExampleRunner2.runSelectedExample()V)
     * @param arg1 java.awt.event.ActionEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.awt.event.ActionEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.runSelectedExample();
            // user code begin {2}
            // user code end
        } catch (java.lang.Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5:  (JMenuItem1.action.actionPerformed(java.awt.event.ActionEvent) --> ExampleRunner2.runSelectedExample()V)
     * @param arg1 java.awt.event.ActionEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5(java.awt.event.ActionEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.runSelectedExample();
            // user code begin {2}
            // user code end
        } catch (java.lang.Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    public void createExamplesList() {
        Vector v = new Vector();
        v.add(new AnimationExample());
        v.add(new ClipExample());
        v.add(new DragAndDropExample());
        v.add(new EventExample());
        v.add(new HandlesExample());
        v.add(new PortalExample());
        v.add(new RTreeExample());
	v.add(new ScrollingExample());
        v.add(new ShapeExample());
        v.add(new SVGExample());
        v.add(new SwingJazzExample());
        v.add(new TreeLayoutExample());
        v.add(new TwoSurfacesExample());
        ivjExampleListJList.setListData(v);
        ivjExampleListJList.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                        exampleListDoubleClickTest(e);
                }
        });
    }

    public void exampleListDoubleClickTest(MouseEvent e) {
        if (e.getClickCount() > 1) {
                runSelectedExample();
        }
    }

    public void exitExampleRunner() {
        System.exit(0);
    }

    /**
     * Return the JSplitPane1 property value.
     * @return javax.swing.JSplitPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSplitPane getContainingJSplitPane() {
        if (ivjContainingJSplitPane == null) {
            try {
                ivjContainingJSplitPane = new javax.swing.JSplitPane(javax.swing.JSplitPane.VERTICAL_SPLIT);
                ivjContainingJSplitPane.setName("ContainingJSplitPane");
                ivjContainingJSplitPane.setDividerLocation(80);
                ivjContainingJSplitPane.setLastDividerLocation(80);
                ivjContainingJSplitPane.setContinuousLayout(true);
                getContainingJSplitPane().add(getExampleListJScrollPane(), "top");
                getContainingJSplitPane().add(getExampleTextJScrollPane(), "bottom");
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjContainingJSplitPane;
    }

    /**
     * Return the JList1 property value.
     * @return javax.swing.JList
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JList getExampleListJList() {
        if (ivjExampleListJList == null) {
            try {
                ivjExampleListJList = new javax.swing.JList();
                ivjExampleListJList.setName("ExampleListJList");
                ivjExampleListJList.setBounds(0, 0, 160, 170);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExampleListJList;
    }

    /**
     * Return the JScrollPane1 property value.
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getExampleListJScrollPane() {
        if (ivjExampleListJScrollPane == null) {
            try {
                ivjExampleListJScrollPane = new javax.swing.JScrollPane();
                ivjExampleListJScrollPane.setName("ExampleListJScrollPane");
                ivjExampleListJScrollPane.setToolTipText("List of examples");
                getExampleListJScrollPane().setViewportView(getExampleListJList());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExampleListJScrollPane;
    }

    /**
     * Return the ExampleRunner2JMenuBar property value.
     * @return javax.swing.JMenuBar
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuBar getExampleRunner2JMenuBar() {
        if (ivjExampleRunner2JMenuBar == null) {
            try {
                ivjExampleRunner2JMenuBar = new javax.swing.JMenuBar();
                ivjExampleRunner2JMenuBar.setName("ExampleRunner2JMenuBar");
                ivjExampleRunner2JMenuBar.add(getFileJMenu());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExampleRunner2JMenuBar;
    }

    /**
     * Return the JScrollPane2 property value.
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getExampleTextJScrollPane() {
        if (ivjExampleTextJScrollPane == null) {
            try {
                ivjExampleTextJScrollPane = new javax.swing.JScrollPane();
                ivjExampleTextJScrollPane.setName("ExampleTextJScrollPane");
                ivjExampleTextJScrollPane.setToolTipText("description of selected example");
                getExampleTextJScrollPane().setViewportView(getExampleTextJTextPane());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExampleTextJScrollPane;
    }

    /**
     * Return the JTextPane1 property value.
     * @return javax.swing.JTextPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTextPane getExampleTextJTextPane() {
        if (ivjExampleTextJTextPane == null) {
            try {
                ivjExampleTextJTextPane = new javax.swing.JTextPane();
                ivjExampleTextJTextPane.setName("ExampleTextJTextPane");
                ivjExampleTextJTextPane.setText("this is the text pane");
                ivjExampleTextJTextPane.setBounds(0, 0, 0, 100);
                ivjExampleTextJTextPane.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExampleTextJTextPane;
    }

    /**
     * Return the JMenu1 property value.
     * @return javax.swing.JMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenu getFileJMenu() {
        if (ivjFileJMenu == null) {
            try {
                ivjFileJMenu = new javax.swing.JMenu();
                ivjFileJMenu.setName("FileJMenu");
                ivjFileJMenu.setText("File");
                ivjFileJMenu.setActionCommand("File");
                ivjFileJMenu.add(getQuitJMenuItem());
                ivjFileJMenu.add(getJMenuItem1());
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjFileJMenu;
    }

    /**
     * Return the JFrameContentPane property value.
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJFrameContentPane() {
        if (ivjJFrameContentPane == null) {
            try {
                ivjJFrameContentPane = new javax.swing.JPanel();
                ivjJFrameContentPane.setName("JFrameContentPane");
                ivjJFrameContentPane.setLayout(new java.awt.BorderLayout());
                getJFrameContentPane().add(getContainingJSplitPane(), "Center");
                getJFrameContentPane().add(getRunExampleJButton(), "South");
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJFrameContentPane;
    }

    /**
     * Return the JMenuItem1 property value.
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getJMenuItem1() {
        if (ivjJMenuItem1 == null) {
            try {
                ivjJMenuItem1 = new javax.swing.JMenuItem();
                ivjJMenuItem1.setName("JMenuItem1");
                ivjJMenuItem1.setText("Run Example");
                ivjJMenuItem1.setActionCommand("RunExampleMenuItem");
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJMenuItem1;
    }

    /**
     * Return the QuitJMenuItem property value.
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getQuitJMenuItem() {
        if (ivjQuitJMenuItem == null) {
            try {
                ivjQuitJMenuItem = new javax.swing.JMenuItem();
                ivjQuitJMenuItem.setName("QuitJMenuItem");
                ivjQuitJMenuItem.setText("Exit");
                ivjQuitJMenuItem.setActionCommand("Quit");
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjQuitJMenuItem;
    }

    /**
     * Return the RunExampleJButton property value.
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getRunExampleJButton() {
        if (ivjRunExampleJButton == null) {
            try {
                ivjRunExampleJButton = new javax.swing.JButton();
                ivjRunExampleJButton.setName("RunExampleJButton");
                ivjRunExampleJButton.setToolTipText("Runs the selected example");
                ivjRunExampleJButton.setText("Run Example");
                ivjRunExampleJButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                // user code begin {1}
                // user code end
            } catch (java.lang.Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRunExampleJButton;
    }

    /**
     * Called whenever the part throws an exception.
     * @param exception java.lang.Throwable
     */
    private void handleException(java.lang.Throwable exception) {

        /* Uncomment the following lines to print uncaught exceptions to stdout */
        // System.out.println("--------- UNCAUGHT EXCEPTION ---------");
        // exception.printStackTrace(System.out);
    }

    /**
     * Initializes connections
     * @exception java.lang.Exception The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws java.lang.Exception {
        // user code begin {1}
        // user code end
        getQuitJMenuItem().addActionListener(ivjEventHandler);
        this.addWindowListener(ivjEventHandler);
        getExampleListJList().addListSelectionListener(ivjEventHandler);
        getRunExampleJButton().addActionListener(ivjEventHandler);
        getJMenuItem1().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("ExampleRunner2");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Jazz Example Runner");
            setSize(426, 335);
            setJMenuBar(getExampleRunner2JMenuBar());
            setContentPane(getJFrameContentPane());
            initConnections();
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        createExamplesList();
        getExampleListJList().setSelectedIndex(0);
        setIconImage(loadFrameIcon());
        // user code end
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

    public static void main(java.lang.String[] args) {
        try {
            ExampleRunner aExampleRunner;
            aExampleRunner = new ExampleRunner();
            aExampleRunner.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                };
            });
            aExampleRunner.setVisible(true);
        } catch (Throwable exception) {
            System.err.println("Exception occurred in main() of javax.swing.JFrame");
            exception.printStackTrace(System.out);
        }
    }

    public void runSelectedExample() {
        int selected = getExampleListJList().getSelectedIndex();
        AbstractExample example = (AbstractExample) getExampleListJList().getModel().getElementAt(selected);
        example.showExample();
    }

    public void updateExampleDescription() {
        // This will be called when a new item in the example list is selected.
        int selected = getExampleListJList().getSelectedIndex();
        AbstractExample example = (AbstractExample) getExampleListJList().getModel().getElementAt(selected);
        getExampleTextJTextPane().setText(example.getExampleDescription());
    }
;

}
