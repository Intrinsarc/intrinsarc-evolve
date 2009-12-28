// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.util.*;

import org.freehep.graphicsio.*;
import org.freehep.swing.layout.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: FontPanel.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class FontPanel extends OptionPanel
{
  public FontPanel(Properties options, String rootKey)
  {
    super("Fonts");

    final OptionCheckBox checkBox = new OptionCheckBox(options, rootKey + "." + FontConstants.EMBED_FONTS,
        "Embed Fonts as");
    add(TableLayout.LEFT, checkBox);
    final OptionComboBox comboBox = new OptionComboBox(options, rootKey + "." + FontConstants.EMBED_FONTS_AS,
        FontConstants.getEmbedFontsAsList());
    add(TableLayout.RIGHT, comboBox);
    checkBox.enables(comboBox);
  }
}
