// Copyright 2000, CERN, Geneva, Switzerland and University of Santa Cruz, California, U.S.A.
package org.freehep.graphicsio.gif;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.spi.*;
import javax.swing.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.exportchooser.*;
import org.freehep.swing.layout.*;
import org.freehep.util.*;

/**
 * 
 * @author Charles Loomis
 * @version $Id: GIFExportFileType.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class GIFExportFileType extends ImageExportFileType
{

  static
  {
    try
    {
      Class clazz = Class.forName("org.freehep.graphicsio.gif.GIFImageWriterSpi");
      IIORegistry.getDefaultInstance().registerServiceProvider((ImageWriterSpi) clazz.newInstance(),
          ImageWriterSpi.class);
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }

  public static final String[] quantizeModes = new String[]{"WebColor"};

  public GIFExportFileType()
  {
    super("gif");
  }

  public boolean hasOptionPanel()
  {
    return true;
  }

  public JPanel createOptionPanel(Properties user)
  {
    UserProperties options = new UserProperties(user, GIFGraphics2D.getDefaultProperties());
    JPanel panel = super.createOptionPanel(options);

    OptionCheckBox quantize = new OptionCheckBox(options, GIFGraphics2D.QUANTIZE_COLORS, "Quantize Colors");
    panel.add(TableLayout.FULL, quantize);

    JLabel quantizeModeLabel = new JLabel("Quantize using ");
    panel.add(TableLayout.LEFT, quantizeModeLabel);
    quantize.enables(quantizeModeLabel);

    OptionComboBox quantizeMode = new OptionComboBox(options, GIFGraphics2D.QUANTIZE_MODE, quantizeModes);
    panel.add(TableLayout.RIGHT, quantizeMode);
    quantize.enables(quantizeMode);

    // disable for now
    quantize.setEnabled(false);
    quantizeModeLabel.setEnabled(false);
    quantizeMode.setEnabled(false);

    return panel;
  }

  public VectorGraphics getGraphics(OutputStream os, Component target) throws IOException
  {

    return new GIFGraphics2D(os, target.getSize());
  }

}
