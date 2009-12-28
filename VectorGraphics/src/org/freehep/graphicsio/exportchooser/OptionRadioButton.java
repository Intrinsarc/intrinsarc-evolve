// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: OptionRadioButton.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class OptionRadioButton extends JRadioButton implements Options
{
  protected boolean initialState;
  protected String key;

  public OptionRadioButton(Properties options, String key, String text)
  {
    super(text, new Boolean(options.getProperty(key, "false")).booleanValue());
    this.key = key;
    initialState = isSelected();
  }

  public boolean applyChangedOptions(Properties options)
  {
    if (isSelected() != initialState)
    {
      options.setProperty(key, Boolean.toString(isSelected()));
      return true;
    }
    return false;
  }

  /**
   * Enables (otherwise disables) the supplied component if this radiobutton is
   * checked. Can be called for multiple components.
   */
  public void enables(final Component c)
  {
    if (c.isEnabled())
    {
      c.setEnabled(isSelected());

      addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          c.setEnabled(isSelected());
        }
      });
    }
  }

  /**
   * Shows (otherwise hides) the supplied component if this radiobutton is
   * checked. Can be called for multiple components.
   */
  public void shows(final Component c)
  {
    c.setVisible(isSelected());

    addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent e)
      {
        c.setVisible(isSelected());
      }
    });
  }
}
