// Copyright 2001-2003, FreeHEP.
package org.freehep.graphicsio.test;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import org.freehep.util.export.*;

/**
 * @author Charles Loomis
 * @version $Id: TestingFrame.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class TestingFrame extends JFrame
{

  // Export Dialog.
  protected ExportDialog dialog;

  // The main panel.
  private JComponent testPanel;

  public TestingFrame(String title)
  {

    // Title this frame.
    super(title);

    // Make this exit when the close button is clicked.
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        quit();
      }
    });

    // Create the Export dialog.
    dialog = new ExportDialog();

    // Make a menu bar
    JMenuBar menuBar = new JMenuBar();

    // File Menu
    JMenu file = new JMenu("File");
    menuBar.add(file);

    // Add Export...
    JMenuItem export = new JMenuItem("Export...");
    file.add(export);
    export.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        export();
      }
    });

    // Add Quit...
    JMenuItem quit = new JMenuItem("Quit");
    file.add(quit);
    quit.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        quit();
      }
    });

    // Edit Menu
    JMenu edit = new JMenu("Edit");
    menuBar.add(edit);

    // Add Copy
    JMenuItem copy = new JMenuItem("Copy");
    edit.add(copy);
    copy.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        copy();
      }
    });

    // Add this to the frame.
    setJMenuBar(menuBar);
  }

  /**
   * A method so that the export dialog can be brought up correctly.
   */
  public void export()
  {
    if (testPanel != null)
    {
      dialog.showExportDialog(this, "Export...", testPanel, "untitled");
      System.gc();
    }
  }

  /**
   * Add a test panel to the frame.
   */
  public void addContent(JComponent c)
  {

    // Set the content pane to this component.
    this.setContentPane(c);
    testPanel = c;

    // Create a border of white surrounded by black.
    Border border = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white),
        BorderFactory.createMatteBorder(2, 2, 2, 2, Color.green));
    c.setBorder(border);
  }

  public void copy()
  {
    Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
    VectorGraphicsTransferable transferable = new VectorGraphicsTransferable(testPanel);
    clipBoard.setContents(transferable, transferable);
  }

  /**
   * This method brings up a dialog box to ask if the user really wants to quit.
   * If the answer is yes, the application is stopped.
   */
  public void quit()
  {

    // Create a dialog box to ask if the user really wants to quit.
    int n = JOptionPane.showConfirmDialog(this, "Do you really want to quit?", "Confirm Quit",
        JOptionPane.YES_NO_OPTION);

    if (n == JOptionPane.YES_OPTION)
    {
      System.exit(0);
    }
  }

}
