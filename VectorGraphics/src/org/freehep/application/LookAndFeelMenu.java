/*
 * LookAndFeelMenu.java
 *
 * Created on February 23, 2001, 4:12 PM
 */

package org.freehep.application;
import java.awt.event.*;

import javax.swing.*;

/**
 * A menu for setting the look and feel of an application
 * 
 * @author tonyj
 * @version $Id: LookAndFeelMenu.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class LookAndFeelMenu extends JMenu
{
  /**
   * Creates a Look and Feel menu
   */
  public LookAndFeelMenu()
  {
    super("Look and Feel");
  }
  public void fireMenuSelected()
  {
    removeAll();
    ActionListener listener = new LAFActionListener();
    UIManager.LookAndFeelInfo info[] = UIManager.getInstalledLookAndFeels();
    String currentName = UIManager.getLookAndFeel().getName();
    for (int i = 0; i < info.length; i++)
    {
      JRadioButtonMenuItem radio = new JRadioButtonMenuItem(info[i].getName());
      radio.setActionCommand(info[i].getClassName());
      radio.setSelected(info[i].getName().equals(currentName));
      radio.addActionListener(listener);
      add(radio);
    }
    super.fireMenuSelected();
  }
  private class LAFActionListener implements ActionListener
  {
    public void actionPerformed(ActionEvent e)
    {
      Application.getApplication().setLookAndFeel(e.getActionCommand());
    }
  }
}
