package org.freehep.graphicsio.examples;

// Copyright 2003, SLAC, Stanford, U.S.A.

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.freehep.graphics2d.*;
import org.freehep.util.export.*;

/**
 * @author Mark Donszelmann
 * @version $Id: ExportDialogExample.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class ExportDialogExample extends JPanel
{


  public ExportDialogExample()
  {
    setPreferredSize(new Dimension(600, 400));
  }

  public void paintComponent(Graphics g)
  {

    if (g == null)
      return;

    VectorGraphics vg = VectorGraphics.create(g);

    Dimension dim = getSize();
    Insets insets = getInsets();

    vg.setColor(Color.white);
    vg.fillRect(insets.left, insets.top, dim.width - insets.left - insets.right, dim.height - insets.top
        - insets.bottom);

    vg.setColor(Color.black);

    vg.setLineWidth(4.0);
    vg.drawLine(10, 10, dim.width - 10, dim.height - 10);
  }

  public static void main(String[] args) throws Exception
  {
    JFrame frame = new JFrame("ExportDialogExample");
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });

    final ExportDialogExample panel = new ExportDialogExample();
    frame.getContentPane().add(panel);

    JMenuBar menuBar = new JMenuBar();
    frame.setJMenuBar(menuBar);

    JMenu file = new JMenu("File");
    menuBar.add(file);

    JMenuItem exportItem = new JMenuItem("Export...");
    exportItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        ExportDialog export = new ExportDialog();
        export.showExportDialog(panel, "Export view as ...", panel, "export");
      }
    });
    file.add(exportItem);

    JMenuItem quitItem = new JMenuItem("Quit");
    quitItem.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });
    file.add(quitItem);

    frame.pack();
    frame.show();
  }
}
