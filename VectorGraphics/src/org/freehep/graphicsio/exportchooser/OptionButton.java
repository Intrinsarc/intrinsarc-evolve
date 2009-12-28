// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.exportchooser;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: OptionButton.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class OptionButton extends JButton implements Options
{

  protected String key;

  public OptionButton(Properties options, String key, String text, final JDialog dialog)
  {
    super(text);
    this.key = key;
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        dialog.show();
        dialog.dispose();
      }
    });
  }

  public boolean applyChangedOptions(Properties options)
  {
    return false;
  }


}
