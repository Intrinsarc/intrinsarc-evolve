/*
 * TabbedPageManager.java
 *
 * Created on March 20, 2001, 4:16 PM
 */

package org.freehep.application.mdi;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

/**
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: TabbedPageManager.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class TabbedPageManager extends PageManager
{
  /** Creates new TabbedPageManager */
  public TabbedPageManager()
  {
    tabs.addChangeListener(cl);
  }
  public void setTabPlacement(int placement)
  {
    tabs.setTabPlacement(placement);
  }
  public int getTabPlacement()
  {
    return tabs.getTabPlacement();
  }
  protected void show(PageContext page)
  {
    tabs.setSelectedComponent(page.getPage());
  }
  protected Component getEmbodiment()
  {
    return tabs;
  }
  protected boolean close(PageContext page)
  {
    boolean ok = super.close(page);
    if (ok)
      tabs.removeTabAt(indexOfPage(page));
    return ok;
  }
  private String hackedTitle(String title)
  {
    return title == null ? CloseButtonTabbedPane.TAB_NAME_TRAILING_SPACE : title
        + CloseButtonTabbedPane.TAB_NAME_TRAILING_SPACE;
  }
  protected void titleChanged(PageContext page)
  {
    tabs.setTitleAt(indexOfPage(page), hackedTitle(page.getTitle()));
  }
  protected void iconChanged(PageContext page)
  {
    tabs.setIconAt(indexOfPage(page), page.getIcon());
  }
  protected int indexOfPage(PageContext page)
  {
    return tabs.indexOfComponent(page.getPage());
  }
  public PageContext openPage(Component c, String title, Icon icon)
  {
    PageContext context = super.openPage(c, title, icon);
    tabs.addTab(hackedTitle(title), icon, c);
    super.firePageOpened(context);
    tabs.setSelectedComponent(c);
    return context;
  }
  protected void init(List pages, PageContext selected)
  {
    tabs.removeChangeListener(cl);
    Iterator i = pages.iterator();
    while (i.hasNext())
    {
      PageContext context = (PageContext) i.next();
      tabs.addTab(hackedTitle(context.getTitle()), context.getIcon(), context.getPage());
      if (context == selected)
        tabs.setSelectedComponent(context.getPage());
    }
    super.init(pages, selected);
    tabs.addChangeListener(cl);
    if (selected == null && pages.size() > 0)
      fireSelectionChanged((PageContext) pages().get(tabs.getSelectedIndex()));
  }
  protected JTabbedPane tabs = new CloseButtonTabbedPane()
  {
    protected void fireCloseTabAt(int index)
    {
      close((PageContext) pages().get(index));
    }
  };
  private ChangeListener cl = new ChangeListener()
  {
    public void stateChanged(ChangeEvent e)
    {
      int index = tabs.getSelectedIndex();
      if (index < 0)
        fireSelectionChanged(null);
      else
        fireSelectionChanged((PageContext) pages().get(index));
    }
  };
}
