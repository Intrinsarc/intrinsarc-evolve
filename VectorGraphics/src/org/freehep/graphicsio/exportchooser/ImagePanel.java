// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.util.*;

import javax.swing.*;

import org.freehep.graphicsio.*;
import org.freehep.swing.layout.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: ImagePanel.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class ImagePanel extends OptionPanel
{
  public ImagePanel(Properties options, String rootKey, String[] formats)
  {
    super("Images");

    add(TableLayout.FULL, new JLabel("Write Images as"));
    ButtonGroup group = new ButtonGroup();
    OptionRadioButton imageType[] = new OptionRadioButton[formats.length];
    for (int i = 0; i < formats.length; i++)
    {
      imageType[i] = new OptionRadioButton(options, rootKey + "." + ImageConstants.WRITE_IMAGES_AS, formats[i]);
      add(TableLayout.FULL, imageType[i]);
      group.add(imageType[i]);
      // add(TableLayout.RIGHT, new OptionTextField(options,
      // rootKey+"."+keys[i], 40));
    }
  }
}
