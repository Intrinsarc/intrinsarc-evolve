// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.swing.test;

import java.awt.*;

import javax.swing.*;

import org.freehep.swing.*;

/**
 * @author Mark Donszelmann
 * @version $Id: JTriStateTest.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class JTriStateTest extends TestFrame
{

  Icon icon;

  protected void addToMenuBar(JMenuBar menuBar)
  {
    icon = new ImageIcon(getClass().getResource("Stop.gif"));

    JMenu m1 = new JMenu("CheckBoxes");
    menuBar.add(m1);
    m1.add(new JCheckBoxMenuItem("CheckBox1", true));
    m1.add(new JCheckBoxMenuItem("CheckBox2", false));
    m1.add(new JCheckBoxMenuItem("CheckBox3"));
    m1.add(new JCheckBoxMenuItem("CheckBox4", icon, false));

    JMenu m2 = new JMenu("TriStateBoxes");
    menuBar.add(m2);
    m2.add(new JTriStateMenuItem("TriStateBox1", 1));
    m2.add(new JTriStateMenuItem("TriStateBox2", 0));
    m2.add(new JTriStateMenuItem("TriStateBox3", -1));
    m2.add(new JTriStateMenuItem("TriStateBox4", icon));
  }

  protected JComponent createComponent()
  {
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(4, 2));
    p.add(new JCheckBox("CheckBox1", true));
    p.add(new JTriStateBox("TriStateBox1", 1));
    p.add(new JCheckBox("CheckBox2", false));
    p.add(new JTriStateBox("TriStateBox2", 0));
    p.add(new JCheckBox("CheckBox3"));
    p.add(new JTriStateBox("TriStateBox3", -1));
    p.add(new JCheckBox("CheckBox4", icon, false));
    p.add(new JTriStateBox("TriStateBox4", icon));

    return p;
  }


  public static void main(String[] argv)
  {
    new JTriStateTest();
  }
}
