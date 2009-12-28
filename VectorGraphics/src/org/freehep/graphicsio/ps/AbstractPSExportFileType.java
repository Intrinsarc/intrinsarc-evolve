// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.ps;

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
 * 
 * @author Charles Loomis, Simon Fischer
 * @version $Id: AbstractPSExportFileType.java,v 1.1 2006-07-03 13:32:43
 *          amcveigh Exp $
 */
public abstract class AbstractPSExportFileType extends AbstractExportFileType
{

  protected static final String bitsList[] = {"1", "2", "4", "8"};
  protected OptionPanel preview;
  protected OptionCheckBox previewCheckBox;

  public boolean hasOptionPanel()
  {
    return true;
  }

  public String[] getMIMETypes()
  {
    return new String[]{"application/postscript"};
  }

  public JPanel createOptionPanel(Properties user)
  {
    UserProperties options = new UserProperties(user, PSGraphics2D.getDefaultProperties());
    preview = new OptionPanel("Preview Image");
    previewCheckBox = new OptionCheckBox(options, PSGraphics2D.PREVIEW, "Include preview");

    preview.add(TableLayout.FULL, previewCheckBox);

    final JLabel previewLabel = new JLabel("Bits per sample");
    preview.add(TableLayout.LEFT, previewLabel);
    previewCheckBox.enables(previewLabel);

    final OptionComboBox previewComboBox = new OptionComboBox(options, PSGraphics2D.PREVIEW_BITS, bitsList);
    preview.add(TableLayout.RIGHT, previewComboBox);
    previewCheckBox.enables(previewComboBox);
    preview.setVisible(false);

    String rootKey = PSGraphics2D.class.getName();

    JPanel infoPanel = new InfoPanel(options, rootKey, new String[]{InfoConstants.FOR, InfoConstants.TITLE});

    // TableLayout.LEFT Panel
    JPanel leftPanel = new OptionPanel();
    leftPanel.add(TableLayout.COLUMN, new PageLayoutPanel(options, rootKey));
    leftPanel.add(TableLayout.COLUMN, new PageMarginPanel(options, rootKey));
    leftPanel.add(TableLayout.COLUMN_FILL, new JLabel());

    // TableLayout.RIGHT Panel
    JPanel rightPanel = new OptionPanel();
    rightPanel.add(TableLayout.COLUMN, new BackgroundPanel(options, rootKey, false));
    rightPanel.add(TableLayout.COLUMN, preview);
    rightPanel.add(TableLayout.COLUMN, new ImageTypePanel(options, rootKey, new String[]{ImageConstants.SMALLEST,
        ImageConstants.ZLIB, ImageConstants.JPG}));
    rightPanel.add(TableLayout.COLUMN, new FontPanel(options, rootKey));
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

    return new PSGraphics2D(os, target.getSize());
  }
}
