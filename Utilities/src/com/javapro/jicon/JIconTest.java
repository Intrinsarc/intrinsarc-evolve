package com.javapro.jicon;

/*
=====================================================================

  JIconTest.java
  
  Created by Claude Duguay
  Copyright (c) 2002
  
=====================================================================
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class JIconTest extends JPanel
  implements ChangeListener
{
  protected static final Icon[] LED_ICONS =
  {
    new ImageIcon("icons/RedLight.gif"),
    new ImageIcon("icons/GreenLight.gif"),
    new ImageIcon("icons/BlueLight.gif")
  };

  protected static final Icon[] CHECK_ICONS =
  {
    new CheckBoxIcon(CheckBoxIcon.NOTHING, 14, 14, true),
    new CheckBoxIcon(CheckBoxIcon.INCLUDE, 14, 14, true),
    new CheckBoxIcon(CheckBoxIcon.EXCLUDE, 14, 14, true)
  };
    
  protected JIcon ledIcon, checkIcon;
  protected JLabel ledLabel, checkLabel;
  
  public JIconTest()
  {
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(250, 150));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    Icon books = new ImageIcon("icons/Books.gif");
    Icon shortcut = new ImageIcon("icons/Shortcut.gif");
    Icon decorated = new DecoratedIcon(books, shortcut,
      DecoratedIcon.LEFT, DecoratedIcon.BOTTOM);
    
    Icon compound = new CompoundIcon(
      books, decorated, CompoundIcon.HORIZONTAL, 4);
    add(BorderLayout.CENTER, new JLabel(compound));
    
    JPanel north = new JPanel(new FlowLayout());
    north.add(new JIcon(new FilterIcon(
      books, new GrayFilter(true, 48))));
    add(BorderLayout.NORTH, north);

    JPanel exampleCheckIcon = new JPanel(new BorderLayout(4, 4));
    exampleCheckIcon.add(BorderLayout.WEST,
      checkIcon = new JIcon(CHECK_ICONS));
    exampleCheckIcon.add(BorderLayout.CENTER,
      checkLabel = new JLabel("state 1"));
    checkIcon.getModel().addChangeListener(this);
    //checkIcon.setFocusable(true);
    
    JPanel exampleLEDIcon = new JPanel(new BorderLayout(4, 4));
    exampleLEDIcon.add(BorderLayout.WEST,
      ledIcon = new JIcon(LED_ICONS));
    exampleLEDIcon.add(BorderLayout.CENTER,
      ledLabel = new JLabel("state 1"));
    ledIcon.getModel().addChangeListener(this);
    //ledIcon.setFocusable(true);
    
    JPanel south = new JPanel(new GridLayout(1, 2, 4, 4));
    south.add(exampleCheckIcon);
    south.add(exampleLEDIcon);
    
    add(BorderLayout.SOUTH, south);
  }

  public void stateChanged(ChangeEvent event)
  {
    Object source = event.getSource();
    if (source ==  ledIcon.getModel())
    {
      int index = ledIcon.getModel().getCurrentIndex();
      ledLabel.setText("state " + (index + 1));
    }
    if (source == checkIcon.getModel())
    {
      int index = checkIcon.getModel().getCurrentIndex();
      checkLabel.setText("state " + (index + 1));
    }
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame("JIcon Test");
    frame.getContentPane().setLayout(new GridLayout());
    frame.getContentPane().add(new JIconTest());
    frame.pack();
    frame.setVisible(true);
  }
}
