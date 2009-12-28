/*
 * PageManager.java
 *
 * Created on March 20, 2001, 2:46 PM
 */

package org.freehep.application.mdi;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import org.freehep.util.commanddispatcher.*;

/**
 * A PageManager manages a set of pages.
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: PageManager.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public abstract class PageManager
{
  public PageContext openPage(Component c, String title, Icon icon)
  {
    PageContext context = new PageContext(c, title, icon);
    context.setPageManager(this);
    pages.add(context);
    return context;
  }
  protected void firePageOpened(PageContext context)
  {
    ManagedPage mp = getManagedPage(context.getPage());
    if (mp != null)
      mp.setPageContext(context);
    firePageEvent(context, PageEvent.PAGEOPENED);
    getCommandProcessor().setChanged();
  }
  ManagedPage getManagedPage(Component c)
  {
    if (c instanceof ManagedPage)
      return (ManagedPage) c;
    else
      if (c instanceof JScrollPane)
      {
        Component cc = ((JScrollPane) c).getViewport().getView();
        if (cc instanceof ManagedPage)
          return (ManagedPage) cc;
      }
    return null;
  }
  public boolean closeAll()
  {
    Iterator i = new ArrayList(pages).iterator();
    while (i.hasNext())
    {
      if (!close((PageContext) i.next()))
        return false;
    }
    return true;
  }
  public int getPageCount()
  {
    return pages.size();
  }
  public PageContext getSelectedPage()
  {
    return selected;
  }
  protected abstract void show(PageContext page);
  protected boolean close(PageContext page)
  {
    ManagedPage mp = getManagedPage(page.getPage());
    if (mp != null && !mp.close())
      return false;
    pages.remove(page);
    if (page == selected)
      fireSelectionChanged(null);
    if (mp != null)
      mp.pageClosed();
    firePageEvent(page, PageEvent.PAGECLOSED);
    getCommandProcessor().setChanged();
    return true;
  }
  protected abstract void titleChanged(PageContext page);
  protected abstract void iconChanged(PageContext page);

  protected abstract Component getEmbodiment();
  protected CommandProcessor createCommandProcessor()
  {
    return new PageManagerCommandProcessor();
  }
  protected CommandProcessor getCommandProcessor()
  {
    if (commandProcessor == null)
      commandProcessor = createCommandProcessor();
    return commandProcessor;
  }
  public List pages()
  {
    return pages;
  }
  protected void init(List pages, PageContext selected)
  {
    this.pages = pages;
    this.selected = selected;
    for (Iterator i = pages.iterator(); i.hasNext();)
    {
      PageContext page = (PageContext) i.next();
      page.setPageManager(this);
    }
  }
  protected void fireSelectionChanged(PageContext context)
  {
    if (selected != null)
    {
      ManagedPage mp = getManagedPage(selected.getPage());
      if (mp != null)
        mp.pageDeselected();
      firePageEvent(selected, PageEvent.PAGEDESELECTED);
    }
    selected = context;
    if (context != null)
    {
      ManagedPage mp = getManagedPage(selected.getPage());
      if (mp != null)
        mp.pageSelected();
      firePageEvent(selected, PageEvent.PAGESELECTED);
    }
    getCommandProcessor().setChanged();
  }
  protected void firePageEvent(PageContext context, int id)
  {
    PageEvent event = null;
    if (listenerList != null)
    {
      Object[] listeners = listenerList.getListenerList();

      for (int i = listeners.length - 2; i >= 0; i -= 2)
      {
        if (listeners[i] == PageListener.class)
        {
          // Lazily create the event:
          if (event == null)
            event = new PageEvent(context, id);
          ((PageListener) listeners[i + 1]).pageChanged(event);
        }
      }
    }
    context.firePageEvent(event, id);
  }

  /**
   * Add a page listener to receive notifications of user initiated changes
   * 
   * @param listener
   *          The PageListener to install
   */
  public void addPageListener(PageListener listener)
  {
    if (listenerList == null)
      listenerList = new EventListenerList();
    listenerList.add(PageListener.class, listener);
  }
  /**
   * Remove a previously installed PageListener
   * 
   * @param listener
   *          The PageListener to remove
   */
  public void removePageListener(PageListener listener)
  {
    listenerList.remove(PageListener.class, listener);
  }
  public class PageManagerCommandProcessor extends CommandProcessor
  {
    public void onCloseAllPages()
    {
      closeAll();
    }
    public void enableCloseAllPages(CommandState state)
    {
      state.setEnabled(getPageCount() > 0);
    }
    public void onClosePage()
    {
      close(getSelectedPage());
    }
    public void enableClosePage(CommandState state)
    {
      state.setEnabled(getSelectedPage() != null);
    }
  }
  private CommandProcessor commandProcessor;
  protected EventListenerList listenerList;
  private List pages = new ArrayList();
  private PageContext selected;
}
