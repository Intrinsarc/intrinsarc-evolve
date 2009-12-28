// Copyright 2000-2003 FreeHEP
package org.freehep.graphicsio.emf;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.exportchooser.*;
import org.freehep.swing.layout.*;
import org.freehep.util.*;

/**
 * // FIXME, check all options
 * 
 * @author Mark Donszelmann
 * @version $Id: EMFExportFileType.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class EMFExportFileType extends AbstractExportFileType
{

  public String getDescription()
  {
    return "Windows Enhanced Metafile";
  }

  public String[] getExtensions()
  {
    return new String[]{"emf"};
  }

  public String[] getMIMETypes()
  {
    return new String[]{"image/x-emf"};
  }

  public boolean hasOptionPanel()
  {
    return true;
  }

  public JPanel createOptionPanel(Properties user)
  {
    UserProperties options = new UserProperties(user, EMFGraphics2D.getDefaultProperties());

    String rootKey = EMFGraphics2D.class.getName();

    // Make the full panel.
    OptionPanel optionsPanel = new OptionPanel();
    optionsPanel.add("0 0 [5 5 5 5] wt", new BackgroundPanel(options, rootKey, true));
    optionsPanel.add(TableLayout.COLUMN_FILL, new JLabel());

    return optionsPanel;
  }

  public VectorGraphics getGraphics(OutputStream os, Component target) throws IOException
  {

    return new EMFGraphics2D(os, target);
  }

}
