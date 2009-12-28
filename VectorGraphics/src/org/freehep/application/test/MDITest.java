/*
 * MDITest.java
 *
 * Created on March 20, 2001, 4:41 PM
 */

package org.freehep.application.test;
import java.awt.event.*;

import javax.swing.*;

import org.freehep.application.mdi.*;
/**
 * 
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: MDITest.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class MDITest extends MDIApplication
{
  /** Creates new MDITest */
  public MDITest()
  {
    super("MDITest");
    onNewPage();
    onNewPage();
    onNewConsole();
    onNewControl();
    onNewToolBar();
  }
  public static void main(String[] args)
  {
    new MDITest().createFrame(args).show();
  }
  public void onNewPage()
  {
    getPageManager().openPage(new CloseablePanel(), "Page " + (page++), icon);
  }
  public void onNewConsole()
  {
    getConsoleManager().openPage(new CloseablePanel(), "Console " + (console++), icon);
  }
  public void onNewControl()
  {
    getControlManager().openPage(new CloseablePanel(), "Control " + (control++), icon);
  }
  public void onNewToolBar()
  {
    JToolBar tb = new CloseableToolBar();
    addToolBar(tb, "Toolbar " + (toolbar++));
  }
  private int page;
  private int console;
  private int control;
  private int toolbar;
  private Icon icon = org.freehep.util.images.ImageHandler.getIcon("/toolbarButtonGraphics/development/Bean24.gif",
      MDITest.class);
  private class CloseablePanel extends JPanel implements ManagedPage, ActionListener
  {
    CloseablePanel()
    {
      JButton close = new JButton("Close");
      add(close);
      close.addActionListener(this);
    }
    public void setPageContext(PageContext context)
    {
      this.context = context;
    }
    public boolean close()
    {
      return true;
    }
    public void actionPerformed(ActionEvent e)
    {
      context.close();
    }
    public void pageSelected()
    {
    }
    public void pageDeselected()
    {
    }
    public void pageIconized()
    {
    }
    public void pageDeiconized()
    {
    }
    public void pageClosed()
    {
    }
    private PageContext context;
  }
  private class CloseableToolBar extends JToolBar implements ActionListener
  {
    CloseableToolBar()
    {
      JButton close = new JButton("Close");
      add(close);
      close.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e)
    {
    }
  }
}
