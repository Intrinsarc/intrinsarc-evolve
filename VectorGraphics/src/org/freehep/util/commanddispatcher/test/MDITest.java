package org.freehep.util.commanddispatcher.test;

import java.awt.*;
import java.beans.*;

import javax.swing.*;

import org.freehep.swing.test.*;
import org.freehep.util.commanddispatcher.*;


/**
 * This class illustrates the use of CommandProcessors in a simple MDI
 * application. There are three classes of command processors in this sample
 * application.
 * <ol>
 * <li>The MDICommandProcessor in the main class, which is always active and
 * deals with creating new windows, and exiting the application.
 * <li>The PageCommandProcessor in each page, which is active only when its
 * page is the selected page.
 * <li>The PrintingPageCommandProcessor, which extends PageCommandProcessor and
 * provides support for the print command in PrintablePages.
 * </ol>
 */
public class MDITest extends TestFrame
{
  private CommandTargetManager manager;
  private JDesktopPane desktop;

  public void addToMenuBar(JMenuBar bar)
  {
    manager = new CommandTargetManager();
    manager.add(new MDICommandProcessor());
    bar.add(new FileMenu());
    manager.start();
  }

  public JComponent createComponent()
  {
    desktop = new JDesktopPane();
    desktop.setPreferredSize(new Dimension(600, 400));
    return desktop;
  }

  public static void main(String[] argv)
  {
    new MDITest();
  }

  public class MDICommandProcessor extends CommandProcessor
  {
    public void onExit()
    {
      quit();
    }

    public void onNewNonPrintablePage()
    {
      JInternalFrame f = new NonPrintablePage(manager);
      desktop.add(f);
      f.show();
    }

    public void onNewPrintablePage()
    {
      JInternalFrame f = new PrintablePage(manager);
      desktop.add(f);
      f.show();
    }
  }

  class FileMenu extends JMenu
  {
    FileMenu()
    {
      super("File");

      add("New Printable Page");
      add("New Non Printable Page");
      add("Close");
      addSeparator();
      add("Print");
      addSeparator();
      add("Exit");
    }

    public JMenuItem add(String name)
    {
      JMenuItem item = new JMenuItem(name);
      manager.add(new CommandSourceAdapter(item));
      super.add(item);
      return item;
    }
  }
}


class PrintablePage extends NonPrintablePage
{
  PrintablePage(CommandTargetManager manager)
  {
    super("Printable", manager);
  }

  protected CommandProcessor createCommandProcessor()
  {
    return new PrintingPageCommandProcessor();
  }

  public class PrintingPageCommandProcessor extends PageCommandProcessor
  {
    public void onPrint()
    {
      System.out.println("Print!");
    }
  }
}


class NonPrintablePage extends JInternalFrame
{
  private CommandProcessor pageCommandProcessor = createCommandProcessor();
  private CommandTargetManager manager;

  NonPrintablePage(CommandTargetManager manager)
  {
    this("Non-printable", manager);
  }

  NonPrintablePage(String title, CommandTargetManager manager)
  {
    super(title);
    this.manager = manager;
    setSize(300, 300);
  }

  public void setSelected(boolean isSelected) throws PropertyVetoException
  {
    super.setSelected(isSelected);
    if (isSelected)
      manager.add(pageCommandProcessor);
    else
      manager.remove(pageCommandProcessor);
  }

  protected CommandProcessor createCommandProcessor()
  {
    return new PageCommandProcessor();
  }

  public class PageCommandProcessor extends CommandProcessor
  {
    public void onClose()
    {
      dispose();
    }
  }
}
