package org.freehep.graphicsio.swf;

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
 * @version $Id: SWFExportFileType.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class SWFExportFileType extends AbstractExportFileType
{

  public String getDescription()
  {
    return "MacroMedia Flash File Format";
  }

  public String[] getExtensions()
  {
    return new String[]{"swf"};
  }

  public String[] getMIMETypes()
  {
    return new String[]{"application/x-shockwave-flash"};
  }

  public boolean hasOptionPanel()
  {
    return true;
  }

  public JPanel createOptionPanel(Properties user)
  {
    UserProperties options = new UserProperties(user, SWFGraphics2D.getDefaultProperties());

    String rootKey = SWFGraphics2D.class.getName();

    // Make the full panel.
    OptionPanel optionsPanel = new OptionPanel();
    optionsPanel.add("0 0 [5 5 5 5] wt", new BackgroundPanel(options, rootKey, true));
    optionsPanel.add("0 1 [5 5 5 5] wt", new ImageTypePanel(options, rootKey, new String[]{ImageConstants.SMALLEST,
        ImageConstants.ZLIB, ImageConstants.JPG}));
    optionsPanel.add(TableLayout.COLUMN_FILL, new JLabel());

    return optionsPanel;
  }

  public VectorGraphics getGraphics(OutputStream os, Component target) throws IOException
  {

    return new SWFGraphics2D(os, target);
  }

}
