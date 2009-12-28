/*
 * ControlPageManager.java
 *
 * Created on March 21, 2001, 2:05 PM
 */

package org.freehep.application.mdi;
import java.awt.*;

import javax.swing.*;

/**
 * A TabbedPageManager that only shows its tabs when there is more than one
 * page. This is the default PageManager used by the control and console areas.
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: ControlPageManager.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class ControlPageManager extends TabbedPageManager
{
  /** Creates new ControlPageManager */
  public ControlPageManager()
  {
    setTabPlacement(JTabbedPane.BOTTOM);
  }
  protected Component getEmbodiment()
  {
    return top;
  }
  protected boolean close(PageContext page)
  {
    boolean ok = super.close(page);
    if (!ok)
      return ok;
    int nPages = getPageCount();
    if (nPages == 1)
    {
      justOne = tabs.getComponentAt(0);
      tabs.setComponentAt(0, new JPanel()); // just a placeholder
      justOne.setVisible(true);
      top.remove(tabs);
      top.add(justOne, BorderLayout.CENTER);
      top.revalidate();
    }
    else
      if (nPages == 0)
      {
        top.remove(justOne);
        justOne = null;
        top.revalidate();
      }
    return ok;
  }
  public PageContext openPage(Component c, String title, Icon icon)
  {
    PageContext context = super.openPage(c, title, icon);
    int nPages = getPageCount();
    if (nPages == 1)
    {
      tabs.setComponentAt(0, new JPanel()); // just a placeholder
      justOne = c;
      c.setVisible(true);
      top.add(justOne, BorderLayout.CENTER);
      top.revalidate();
    }
    else
      if (nPages == 2)
      {
        top.remove(justOne);
        tabs.setComponentAt(0, justOne);
        justOne = null;
        top.add(tabs);
        top.revalidate();
        top.repaint(); // Fixes JAS-161 (but why exactly? See
                        // http://www.eos.dk/archive/swing/msg02250.html)
      }
    return context;
  }
  protected int indexOfPage(PageContext page)
  {
    if (page.getPage() == justOne)
      return 0;
    else
      return super.indexOfPage(page);
  }

  protected void show(PageContext page)
  {
    if (getPageCount() > 1)
      super.show(page);
  }

  private JPanel top = new JPanel(new BorderLayout());
  private Component justOne;
}