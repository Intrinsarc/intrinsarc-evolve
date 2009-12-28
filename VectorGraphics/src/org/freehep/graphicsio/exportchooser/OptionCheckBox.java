// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: OptionCheckBox.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class OptionCheckBox extends JCheckBox implements Options
{
  protected boolean initialState;
  protected String key;

  public OptionCheckBox(Properties options, String key, String text)
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
   * Enables (otherwise disables) the supplied component if this checkbox is
   * checked. Can be called for multiple components.
   */
  public void enables(final Component c)
  {
    if (c.isEnabled())
    {
      c.setEnabled(isSelected());

      // selecting
      addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          c.setEnabled(isSelected());
        }
      });

      // disabling
      addPropertyChangeListener("enabled", new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          if (e.getNewValue().equals(Boolean.TRUE))
          {
            c.setEnabled(isSelected());
          }
          else
          {
            c.setEnabled(false);
          }
        }
      });
    }
  }

  /**
   * Disabled (otherwise enables) the supplied component if this checkbox is
   * checked. Can be called for multiple components.
   */
  public void disables(final Component c)
  {
    if (c.isEnabled())
    {
      c.setEnabled(!isSelected());

      // selecting
      addItemListener(new ItemListener()
      {
        public void itemStateChanged(ItemEvent e)
        {
          c.setEnabled(!isSelected());
        }
      });

      // disabling
      addPropertyChangeListener("enabled", new PropertyChangeListener()
      {
        public void propertyChange(PropertyChangeEvent e)
        {
          if (e.getNewValue().equals(Boolean.TRUE))
          {
            c.setEnabled(!isSelected());
          }
          else
          {
            c.setEnabled(false);
          }
        }
      });
    }
  }

  /**
   * Shows (otherwise hides) the supplied component if this checkbox is checked.
   * Can be called for multiple components.
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

  /**
   * Hides (otherwise shows) the supplied component if this checkbox is checked.
   * Can be called for multiple components.
   */
  public void hides(final Component c)
  {
    c.setVisible(!isSelected());

    addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent e)
      {
        c.setVisible(!isSelected());
      }
    });
  }
}
