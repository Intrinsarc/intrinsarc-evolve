// Copyright 2000-2003 FreeHEP
package org.freehep.graphicsio.cgm;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.*;
import org.freehep.graphicsio.exportchooser.*;
import org.freehep.swing.layout.*;
import org.freehep.util.*;

/**
 * // FIXME, check all options
 * 
 * @author Mark Donszelmann
 * @version $Id: CGMExportFileType.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class CGMExportFileType extends AbstractExportFileType
{

  public String getDescription()
  {
    return "Computer Graphics Metafile";
  }

  public String[] getExtensions()
  {
    return new String[]{"cgm"};
  }

  public String[] getMIMETypes()
  {
    return new String[]{"image/cgm"};
  }

  public boolean hasOptionPanel()
  {
    return true;
  }

  public JPanel createOptionPanel(Properties user)
  {
    UserProperties options = new UserProperties(user, CGMGraphics2D.getDefaultProperties());

    OptionPanel formatPanel = new OptionPanel("Format");
    formatPanel.add(TableLayout.FULL, new OptionCheckBox(options, CGMGraphics2D.BINARY, "Binary"));

    String rootKey = CGMGraphics2D.class.getName();

    JPanel infoPanel = new InfoPanel(options, rootKey, new String[]{InfoConstants.AUTHOR, InfoConstants.TITLE,
        InfoConstants.SUBJECT, InfoConstants.KEYWORDS,});

    // TableLayout.LEFT Panel
    JPanel leftPanel = new OptionPanel();
    leftPanel.add(TableLayout.COLUMN, formatPanel);
    leftPanel.add(TableLayout.COLUMN_FILL, new JLabel());

    // TableLayout.RIGHT Panel
    JPanel rightPanel = new OptionPanel();
    rightPanel.add(TableLayout.COLUMN, new BackgroundPanel(options, rootKey, false));
    rightPanel.add(TableLayout.COLUMN_FILL, new JLabel());

    // Make the full panel.
    OptionPanel optionsPanel = new OptionPanel();
    optionsPanel.add("0 0 [5 5 5 5] wt", leftPanel);
    optionsPanel.add("1 0 [5 5 5 5] wt", rightPanel);
    optionsPanel.add("0 1 2 1 [5 5 5 5] wt", infoPanel);
    optionsPanel.add(TableLayout.COLUMN_FILL, new JLabel());

    return optionsPanel;
  }

  public VectorGraphics getGraphics(OutputStream os, Component target) throws IOException
  {

    return new CGMGraphics2D(os, target);
  }
}
