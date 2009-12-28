// Copyright 2000, SLAC, Stanford, California, U.S.A.
package org.freehep.util.commanddispatcher.test;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import org.freehep.util.commanddispatcher.*;
import org.freehep.xml.menus.*;
import org.xml.sax.*;


/**
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: Test.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class Test extends org.freehep.swing.test.TestFrame
{
  private final static Class base = org.freehep.xml.menus.test.Test.class;
  private CommandTargetManager cmdManager;

  public static void main(String[] argv) throws Exception
  {
    new Test();
  }

  protected JComponent createComponent()
  {
    JPanel main = new JPanel(new BorderLayout());
    try
    {
      cmdManager = new CommandTargetManager();

      URL xml = base.getResource("menus.xml");
      final XMLMenuBuilder menus = new MyXMLMenuBuilder(xml);

      cmdManager.add(new MyCommandProcessor());
      cmdManager.start();

      JMenuBar b = menus.getMenuBar("mainMenu");
      main.add(b, BorderLayout.NORTH);

      JPanel p = new JPanel();
      main.add(p, BorderLayout.CENTER);
      p.setLayout(new BorderLayout());

      JToolBar t = menus.getToolBar("applicationToolBar");
      p.add(t, BorderLayout.NORTH);
      addMouseListener(new MouseAdapter()
      {
        public void mousePressed(MouseEvent e)
        {
          if (e.isPopupTrigger())
            showPopup(e);
          super.mousePressed(e);
        }

        public void mouseReleased(MouseEvent e)
        {
          if (e.isPopupTrigger())
            showPopup(e);
          super.mouseReleased(e);
        }

        private void showPopup(MouseEvent e)
        {
          JPopupMenu popup = menus.getPopupMenu("toolbarPopupMenu");
          popup.show(Test.this, e.getX(), e.getY());
        }
      });
    }
    catch (Exception x)
    {
      x.printStackTrace();
      System.exit(0);
    }
    main.setPreferredSize(new Dimension(400, 200));
    return main;
  }

  public class MyCommandProcessor extends CommandProcessor
  {
    private boolean enablePrint = true;

    public void enableEnablePrint(BooleanCommandState state)
    {
      state.setEnabled(true);
      state.setSelected(enablePrint);
    }

    public void enablePrint(CommandState state)
    {
      state.setEnabled(enablePrint);
    }

    public void onEnablePrint(boolean state)
    {
      enablePrint = state;
      setChanged();
    }

    public void onExit()
    {
      quit();
    }

    public void onPrint()
    {
      System.out.println("Print");
    }
  }

  private class MyXMLMenuBuilder extends XMLMenuBuilder
  {
    // MD: Jikes had a problem compiling an anonymous inner class with a throw
    // clause in
    // the constructor, therefore I redefined it as an inner class
    MyXMLMenuBuilder(URL xml) throws SAXException, IOException
    {
      build(xml);
    }

    protected JMenuItem createMenuItem(String className, String name, String type, String command) throws SAXException
    {
      JMenuItem result = super.createMenuItem(className, name, type, command);
      cmdManager.add(new CommandSourceAdapter(result));
      return result;
    }

    protected AbstractButton createToolBarItem(String className, String name, String type, String command)
        throws SAXException
    {
      AbstractButton result = super.createToolBarItem(className, name, type, command);
      cmdManager.add(new CommandSourceAdapter(result));
      return result;
    }
  }
}
