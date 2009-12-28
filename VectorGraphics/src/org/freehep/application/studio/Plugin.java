package org.freehep.application.studio;
import java.awt.*;

import javax.swing.*;

import org.freehep.xml.menus.*;


/**
 * A base class which Plugins must extend
 */
public abstract class Plugin
{
  protected void init() throws Throwable
  {

  }
  public Studio getApplication()
  {
    return app;
  }
  protected void addMenu(JMenuItem item, long location)
  {
    JMenuBar bar = app.getMenuBar();
    String loc = String.valueOf(location);
    addMenu(bar, loc, item);
  }
  private void addMenu(Container parent, String loc, JMenuItem item)
  {
    int l = loc.length() % 3;
    if (l == 0)
      l = 3;
    int ll = Integer.parseInt(loc.substring(0, l));

    Component[] c = parent instanceof JMenu ? ((JMenu) parent).getPopupMenu().getComponents() : parent.getComponents();
    for (int i = 0; i < c.length; i++)
    {
      Component comp = c[i];
      if (comp instanceof JComponent)
      {
        JComponent child = (JComponent) comp;
        Object location = child.getClientProperty(XMLMenuBuilder.LOCATION_PROPERTY);
        if (!(location instanceof Integer))
          continue;
        int locat = ((Integer) location).intValue();
        if (locat == ll)
        {
          String remainder = loc.substring(l);
          if (remainder.length() > 0 && child instanceof Container)
          {
            addMenu((Container) comp, remainder, item);
            return;
          }
          else
            throw new RuntimeException("Invalid location for addMenu");
        }
        else
          if (locat > ll)
          {
            ((Container) parent).add(item, i);
            item.putClientProperty(XMLMenuBuilder.LOCATION_PROPERTY, new Integer(ll));
            return;
          }
      }
    }
    ((Container) parent).add(item);
    item.putClientProperty(XMLMenuBuilder.LOCATION_PROPERTY, new Integer(ll));
  }
  void setContext(Studio app) throws Throwable
  {
    this.app = app;
    init();
  }

  private Studio app;
}
