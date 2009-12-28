// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.util.*;

import javax.swing.*;

import org.freehep.graphicsio.*;
import org.freehep.swing.layout.*;
import org.freehep.util.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: ImageTypePanel.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class ImageTypePanel extends OptionPanel
{

  private String key;
  private String initialType;
  private JComboBox imageSizeCombo;

  public ImageTypePanel(Properties user, String rootKey, String[] types)
  {
    super("Image Type");
    key = rootKey + "." + ImageConstants.WRITE_IMAGES_AS;

    UserProperties options = new UserProperties(user);
    initialType = options.getProperty(key);

    OptionComboBox imageTypeCombo = new OptionComboBox(options, key, types);
    add(TableLayout.LEFT, new JLabel("Include Images as "));
    add(TableLayout.RIGHT, imageTypeCombo);
  }
}