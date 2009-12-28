// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.text.*;
import java.util.*;

import javax.swing.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: OptionFormattedTextField.java,v 1.1 2006-07-03 13:32:56
 *          amcveigh Exp $
 */
public class OptionFormattedTextField extends JFormattedTextField implements Options
{
  protected String initialText;
  protected String key;

  public OptionFormattedTextField(Properties options, String key, String text, int columns, Format format)
  {
    super(format);
    setText(options.getProperty(key, text));
    setColumns(columns);
    this.key = key;
    initialText = getText();
  }

  public boolean applyChangedOptions(Properties options)
  {
    if (!getText().equals(initialText))
    {
      options.setProperty(key, getText());
      return true;
    }
    return false;
  }
}
