/*
 * PageContext.java
 *
 * Created on March 20, 2001, 2:54 PM
 */

package org.freehep.application.mdi;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Allows the user to interact with a Page in an abstract way
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: PageContext.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class PageContext
{
  PageContext(Component c, String title, Icon icon)
  {
    this.component = c;
    this.title = title;
    this.icon = icon;
  }
  void setPageManager(PageManager manager)
  {
    this.pageManager = manager;
  }
  PageManager getPageManager()
  {
    return pageManager;
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
  void firePageEvent(PageEvent event, int id)
  {
    if (listenerList != null)
    {
      Object[] listeners = listenerList.getListenerList();
      for (int i = listeners.length - 2; i >= 0; i -= 2)
      {
        if (listeners[i] == PageListener.class)
        {
          // Lazily create the event:
          if (event == null)
            event = new PageEvent(this, id);
          ((PageListener) listeners[i + 1]).pageChanged(event);
        }
      }
    }
  }
  /**
   * Requests that the associated page be shown If the page is iconized it is
   * deiconized, and bought to the top
   */
  public void requestShow()
  {
    pageManager.show(this);
  }
  /**
   * Closes the associated window
   */
  public void close()
  {
    pageManager.close(this);
  }
  /**
   * Get the component associated with this page
   */
  public Component getPage()
  {
    return component;
  }
  /**
   * Get the name associated with the page
   */
  public String getTitle()
  {
    return title;
  }
  /**
   * Get the icon associated with the page
   */
  public Icon getIcon()
  {
    return icon;
  }
  /**
   * Set the title of the window
   */
  public void setTitle(String title)
  {
    this.title = title;
    pageManager.titleChanged(this);
  }
  /**
   * Set the icon associated with the window
   */
  public void setIcon(Icon icon)
  {
    this.icon = icon;
    pageManager.iconChanged(this);
  }
  public String toString()
  {
    return "PageContext: " + title;
  }
  private Component component;
  private String title;
  private Icon icon;
  private PageManager pageManager;
  private EventListenerList listenerList;
}
