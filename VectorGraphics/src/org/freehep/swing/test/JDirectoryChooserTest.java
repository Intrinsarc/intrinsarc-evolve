package org.freehep.swing.test;

import java.awt.event.*;

import javax.swing.*;

import org.freehep.swing.*;

public class JDirectoryChooserTest extends TestFrame
{
  protected JComponent createComponent()
  {
    JPanel p = new JPanel();
    final JDirectoryChooser chooser = new JDirectoryChooser();
    JButton browse = new JButton("Browse...")
    {
      public void fireActionPerformed(ActionEvent e)
      {
        chooser.showDialog(this);
      }
    };
    p.add(browse);
    JCheckBox dirOnly = new JCheckBox("Directories Only")
    {
      public void fireActionPerformed(ActionEvent e)
      {
        chooser.setFileSelectionMode(isSelected() ? chooser.DIRECTORIES_ONLY : chooser.FILES_AND_DIRECTORIES);
      }
    };
    dirOnly.setSelected(chooser.getFileSelectionMode() == chooser.DIRECTORIES_ONLY);
    p.add(dirOnly);
    JCheckBox multi = new JCheckBox("Multiple Selection")
    {
      public void fireActionPerformed(ActionEvent e)
      {
        chooser.setMultiSelectionEnabled(isSelected());
      }
    };
    multi.setSelected(chooser.isMultiSelectionEnabled());
    p.add(multi);
    return p;
  }
  public static void main(String[] arg) throws Exception
  {
    new JDirectoryChooserTest();
  }
}
