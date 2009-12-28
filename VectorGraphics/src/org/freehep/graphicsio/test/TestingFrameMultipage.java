// University of California, Santa Cruz, USA and
// CERN, Geneva, Switzerland, Copyright (c) 2000
package org.freehep.graphicsio.test;

import java.awt.*;
import java.awt.datatransfer.*;

import javax.swing.*;
import javax.swing.border.*;

import org.freehep.util.export.*;

/**
 * @author Mark Donszelmann
 * @version $Id: TestingFrameMultipage.java,v 1.1 2006-07-03 13:33:11 amcveigh
 *          Exp $
 */
public class TestingFrameMultipage extends TestingFrame
{

  // Export Dialog.
  ExportDialog dialog;

  // The main panel.
  JTabbedPane tabs;

  public TestingFrameMultipage(String title)
  {

    // Title this frame.
    super(title);

    tabs = new JTabbedPane();
    getContentPane().add(tabs);
  }

  /**
   * A method so that the export dialog can be brought up correctly.
   */
  public void export()
  {
    if (tabs != null)
    {
      // FIXME broken to export all pages, now export only one page
      // dialog.showExportDialog(this, "Export...", tabs.getComponents(),
      // "untitled");
      dialog.showExportDialog(this, "Export...", tabs.getSelectedComponent(), tabs.getTitleAt(tabs.getSelectedIndex()));
    }
  }

  /**
   * Add a test panel to the frame.
   */
  public void addPage(String name, JComponent c)
  {

    tabs.addTab(name, c);

    // Create a border of white surrounded by black.
    Border border = BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white),
        BorderFactory.createMatteBorder(2, 2, 2, 2, Color.green));
    c.setBorder(border);
  }

  public void copy()
  {
    Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
    VectorGraphicsTransferable transferable = new VectorGraphicsTransferable(tabs.getSelectedComponent());
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
