package org.freehep.swing.test;

import java.awt.event.*;

import javax.swing.*;

import org.freehep.swing.*;

public class JSpinBoxTest extends TestFrame
{
  protected JComponent createComponent()
  {
    JPanel p = new JPanel();
    final JSpinBox spin = new JSpinBox(50, 0, 100);
    spin.setEnabled(false);
    p.add(spin);
    JCheckBox enable = new JCheckBox("enable")
    {
      public void fireActionPerformed(ActionEvent e)
      {
        spin.setEnabled(isSelected());
      }
    };
    enable.setSelected(spin.isEnabled());
    p.add(enable);
    return p;
  }
  public static void main(String[] arg) throws Exception
  {
    new JSpinBoxTest();
  }
}
