package net.xoetrope.editor.color;


import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.colorchooser.*;

class MyColorWheelPanel extends ColorWheelPanel
{
  @Override
  public String getDisplayName()
  {
    return "Color wheel";
  } 
}

public class ExtendedColorChooser extends JColorChooser
{
  public ExtendedColorChooser()
  {
    addColorWheel(this);
  }
  
  public static Color showExtendedDialog(Component component, String title, Color initial)
  {
    final JColorChooser pane = new JColorChooser(initial != null ? initial : Color.white);

    addColorWheel(pane);
    
    ColorTracker ok = new ColorTracker(pane);
    JDialog dialog = JColorChooser.createDialog(component, title, true, pane, ok, null);

    dialog.setVisible(true); // blocks until user brings dialog down...

    return ok.getColor();
  }
  
  private static void addColorWheel(JColorChooser chooser)
  {
    AbstractColorChooserPanel[] oldPanels = chooser.getChooserPanels();
    AbstractColorChooserPanel[] newPanels = new AbstractColorChooserPanel[oldPanels.length + 1];
    System.arraycopy(oldPanels, 0, newPanels, 1, oldPanels.length);
    ColorWheelPanel wheel = new MyColorWheelPanel();
    wheel.setName("Foo");
    newPanels[0] = wheel;
    chooser.setChooserPanels(newPanels);        
  }
}


class ColorTracker implements ActionListener, Serializable
{
  JColorChooser chooser;
  Color color;

  public ColorTracker(JColorChooser c)
  {
    chooser = c;
  }

  public void actionPerformed(ActionEvent e)
  {
    color = chooser.getColor();
  }

  public Color getColor()
  {
    return color;
  }
}
