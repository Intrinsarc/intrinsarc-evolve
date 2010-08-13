package com.intrinsarc.swing.palette;

import java.awt.*;

import javax.swing.*;

public class ToolPaletteRun
{
  public static void changeLookAndFeel() throws Exception
  {
    // setup the look and feel properties
//    Properties props = new Properties();
//   
//    // Change the size if 10 point does not fit your needs
//    props.put("controlTextFont", "Arial 12");
//    props.put("systemTextFont", "Arial 12");
//    props.put("userTextFont", "Arial 12");
//    props.put("menuTextFont", "Arial 12");
//    props.put("windowTitleFont", "Arial bold 12");
//    props.put("subTextFont", "Arial 10");
    
    // set your theme
//    SmartLookAndFeel.setCurrentTheme(props);

    // select the Look and Feel
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    //UIManager.setLookAndFeel(SmartLookAndFeel.class.getName());
  }
  
  public static void main(String args[]) throws Exception
  {
    changeLookAndFeel();
    
    final javax.swing.JFrame frame = new JFrame("RichPalette test");
    frame.setPreferredSize(new Dimension(210, 700));
    final RichPalette p = new RichPalette();
    Icon folder = loadIcon("folder.png");
    RichPaletteCategory c1 = new RichPaletteCategory(folder, "Selection", null);
    c1.addEntry(new RichPaletteEntry(p, loadIcon("select.png"), "Select", null, null, false, null));
    p.addCategory(c1);
    RichPaletteCategory c2 = new RichPaletteCategory(folder, "Class", null);
    ImageIcon widget = loadIcon("widget.png");
    for (int lp = 0; lp < 10; lp++)
      c2.addEntry(new RichPaletteEntry(p, widget, "Component" + lp, null, null, false, null));
    p.addCategory(c2);
    RichPaletteCategory c3 = new RichPaletteCategory(folder, "Component", null);
    c3.addEntry(new RichPaletteEntry(p, widget, "Component", null, null, false, null));
    p.addCategory(c3);
    RichPaletteCategory c4 = new RichPaletteCategory(folder, "Class", null);
    for (int lp = 0; lp < 30; lp++)
      c4.addEntry(new RichPaletteEntry(p, widget, "Component" + lp, null, null, false, null));
    p.addCategory(c4);

    frame.add(p.getComponent());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private static ImageIcon loadIcon(String icon)
  {
    return RichPaletteCategory.loadIcon(icon);
  }
  
}
