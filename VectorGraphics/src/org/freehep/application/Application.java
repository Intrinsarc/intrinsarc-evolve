/*
 * Application.java
 *
 * Created on January 29, 2001, 3:13 PM
 */

package org.freehep.application;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import org.freehep.application.services.*;
import org.freehep.swing.*;
import org.freehep.swing.popup.*;
import org.freehep.util.*;
import org.freehep.util.commanddispatcher.*;
import org.freehep.util.commandline.*;
import org.freehep.xml.menus.*;
import org.xml.sax.*;

// import kitchen.sink.*;

/**
 * A framework for a simple swing application. This framework assumes there will
 * be only one top level window associated with the application, which can
 * always be accessed using the static method Application.getApplication();
 * <p>
 * The framework provides the following facilities:
 * <ul>
 * <li>Access to a ServiceManager to make it easy for an application to run in
 * the Java Web Start Environment as well as as a standalone application.
 * Support for Java Applets (using the Java Plugin) could be added as well in
 * future.
 * <li>Print Preview capability
 * <li>Recent file menu
 * <li>A simple method of reporting error messages to the user.
 * <li>Access to help files, using JavaHelp
 * <li>Use of XML to define menus and toolbars
 * <li>Use of a command manager for dispatching commands to command targets.
 * <li>Support for user preferences to be stored between invocations.
 * </ul>
 * The easiest way to use the framework is to provide an application properties
 * file. The following properies are supported:
 * <dl>
 * <dt>mainClass</dt>
 * <dd>The main class (which must extend Application)</dd>
 * <dt>title</dt>
 * <dd>The initial title for the main frame</dd>
 * <dt>menus</dt>
 * <dd>The name of an XML file to load which defines menus</dd>
 * </dl>
 * 
 * The framework basically ties together a number of features that are
 * implemented elsewhere in the FreeHEP library. By using this framework you are
 * able to easily use some or all of these features. The framework has been
 * implemented in such a way as to ensure there is almost no overhead paid for
 * features that are not used.
 * 
 * @author tonyj
 * @version $Id: Application.java,v 1.1 2009-03-04 22:46:56 andrew Exp $
 */
public class Application extends JPanel
{
  /** Creates new Application */
  protected Application(String appName)
  {
    super(new BorderLayout());
    if (theApp != null)
      throw new RuntimeException("Attempt to create multiple instances of Application");
    this.appName = appName;
    theApp = this;

    // Check the JVM version ASAP

    try
    {
      String actual = System.getProperty("java.version");
      String required = getAppProperties().getProperty("minimumJavaVersion");
      if (versionNumberCompare(actual, required) < 0)
        throw new RuntimeException("Wrong Java version required: " + required + " actual: " + actual);
    }
    catch (SecurityException x)
    {
    }
    catch (NumberFormatException x)
    {
    }
  }
  /**
   * Override this method to provide a specialized ServiceManager. By default
   * this method checks the System property
   * org.freehep.application.ServiceManager, and uses its value as the name of
   * the class to instantiate. If the property is undefined we use
   * org.freehep.application.services.app.AppServiceManager
   * 
   * @see org.freehep.application.services.app.AppServiceManager
   */
  protected ServiceManager createServiceManager() throws InitializationException
  {
    String className = System.getProperty("jnlp.org.freehep.application.ServiceManager");
    if (className == null)
      className = System.getProperty("org.freehep.application.ServiceManager");
    if (className == null)
      className = "org.freehep.application.services.app.AppServiceManager";
    return (ServiceManager) createObjectFromProperty(className, ServiceManager.class);
  }
  public ServiceManager getServiceManager()
  {
    if (serviceManager == null)
      serviceManager = createServiceManager();
    return serviceManager;
  }
  public static Application getApplication()
  {
    return theApp;
  }
  public RecentFileList getRecentFileList(String name)
  {
    if (rflHash == null)
      rflHash = new Hashtable();
    RecentFileList rfl = (RecentFileList) rflHash.get(name);
    if (rfl == null)
    {
      rfl = new RecentFileList(name);
      rfl.load(getUserProperties());
      rflHash.put(name, rfl);
    }
    return rfl;
  }
  protected void saveUserProperties()
  {
    Properties userProperties = getUserProperties();
    // Save any recent file lists
    if (rflHash != null)
    {
      Enumeration e = rflHash.elements();
      while (e.hasMoreElements())
      {
        RecentFileList rfl = (RecentFileList) e.nextElement();
        rfl.save(userProperties);
      }
    }
    // Save the window size/position
    JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
    if (f != null && f.getState() == f.NORMAL)
      PropertyUtilities.setRectangle(userProperties, "window", f.getBounds());
    storeUserProperties(getUserProperties());
  }
  private void loadUserProperties(Properties user)
  {
    getServiceManager().loadUserPreferences(user);
  }
  private void storeUserProperties(Properties user)
  {
    getServiceManager().storeUserPreferences(user);
  }
  public void setLookAndFeel(String lookAndFeelName)
  {
    try
    {
      UIManager.setLookAndFeel(lookAndFeelName);
      Frame f = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this);
      if (f != null)
        SwingUtilities.updateComponentTreeUI(f);
      getUserProperties().setProperty("lookAndFeel", lookAndFeelName);
    }
    catch (Exception x)
    {
      error("Could not set look and feel " + lookAndFeelName, x);
    }
  }
  public String getVersion()
  {
    return getAppProperties().getProperty("version");
  }
  public String getFullVersion()
  {
    Properties prop = getAppProperties();
    return prop.getProperty("fullVersion");
  }
  /**
   * Compares two version numbers of the form 1.2.3.4
   * 
   * @return >0 if v1>v2, <0 if v1<v2 or 0 if v1=v2
   */
  public static int versionNumberCompare(String v1, String v2) throws NumberFormatException
  {
    StringTokenizer t1 = new StringTokenizer(v1, ".");
    StringTokenizer t2 = new StringTokenizer(v2, ".");
    while (t1.hasMoreTokens())
    {
      String vv1 = t1.nextToken();
      if (t2.hasMoreTokens())
      {
        String vv2 = t2.nextToken();
        int i1 = Integer.parseInt(vv1);
        int i2 = Integer.parseInt(vv2);
        if (i1 == i2)
          continue;
        return i1 - i2;
      }
      else
        return 1;
    }
    if (t2.hasMoreTokens())
      return -1;
    else
      return 0;
  }
  public PrintPreview createPrintPreview()
  {
    return new AppPrintPreview();
  }
  /**
   * Shows a dialog and remembers its screen position (across sessions)
   */
  public void showDialog(JDialog dlg, String key)
  {
    Rectangle bounds = PropertyUtilities.getRectangle(getUserProperties(), key, null);
    if (bounds == null)
    {
      dlg.pack();
      dlg.setLocationRelativeTo(this);
    }
    else
      dlg.setBounds(bounds);
    dlg.show();
    PropertyUtilities.setRectangle(getUserProperties(), key, dlg.getBounds());
  }
  public CommandProcessor getCommandProcessor()
  {
    if (commandProcessor == null)
      commandProcessor = createCommandProcessor();
    return commandProcessor;
  }
  protected CommandProcessor createCommandProcessor()
  {
    return new ApplicationCommandProcessor();
  }
  public CommandTargetManager getCommandTargetManager()
  {
    if (commandTargetManager == null)
      commandTargetManager = createCommandTargetManager();
    return commandTargetManager;
  }
  protected CommandLine createCommandLine()
  {
    Properties props = getAppProperties();
    int n = PropertyUtilities.getInteger(props, "numberOfParameters");
    boolean multiLevel = n < 0;
    return new CommandLine(getAppName(), getFullVersion(), Math.max(n, 0), multiLevel);
  }
  public CommandLine getCommandLine()
  {
    if (commandLine == null)
    {
      commandLine = createCommandLine();
      // register standard options
      commandLine.addOption("noSplash", null, "Suppress splash screen on startup");
      commandLine.addOption("clearUserProperties", null, "Clears the user properties");
      commandLine.addBailOutOption("help", "h", "Display this message");
      commandLine.addMultiOption("D", "key=value", "Set application property");
      commandLine.addMultiOption("d", "key=value", "Set user property, persists between sessions");
    }
    return commandLine;
  }
  protected CommandTargetManager createCommandTargetManager()
  {
    return new ApplicationCommandTargetManager();
  }
  public final void showHelpTopic(String helpTopicTarget)
  {
    showHelpTopic(helpTopicTarget, "TOC");
  }
  public void showHelpTopic(String helpTopicTarget, Component owner)
  {
    showHelpTopic(helpTopicTarget, "TOC", owner);
  }
  private void showHelpTopic(String helpTopicTarget, String navigatorView)
  {
    showHelpTopic(helpTopicTarget, navigatorView, this);
  }
  private void showHelpTopic(final String helpTopicTarget, final String navigatorView, final Component owner)
  {
    Runnable r2 = new Runnable()
    {
      public void run()
      {
        try
        {
          HelpService hs = getHelpService();
          hs.showHelpTopic(helpTopicTarget, navigatorView, owner);
        }
        catch (IllegalArgumentException x)
        {
          error("Help topic not found", x);
        }
      }
    };
    whenAvailable("help", r2);
  }
  /**
   * Run Runnable when part is downloaded and available. Runnable is always run
   * on the event dispatch thread.
   * 
   * @param The
   *          part will be downloaded (if necessary)
   * @param The
   *          Runnable that will be run
   */
  public void whenAvailable(final String part, final Runnable run)
  {
    try
    {
      if (!getServiceManager().isAvailable(part))
      {
        final Runnable r1 = new Runnable()
        {
          public void run()
          {
            if (getServiceManager().makeAvailable(part))
            {
              SwingUtilities.invokeLater(run);
            }
          }
        };
        Thread download = new Thread(r1);
        download.start();
      }
      else
      {
        run.run();
      }
    }
    catch (InitializationException x)
    {
      error("Could not download " + part, x);
    }
  }
  private HelpService getHelpService() throws InitializationException
  {
    if (helpService == null)
      helpService = createHelpService();
    return helpService;
  }
  protected HelpService createHelpService() throws InitializationException
  {
    try
    {
      return (HelpService) Class.forName("org.freehep.application.HelpServiceImpl").newInstance();
    }
    catch (ExceptionInInitializerError x)
    {
      Throwable t = x.getException();
      throw new InitializationException("Could not create help service", t == null ? x : t);
    }
    catch (Throwable x)
    {
      throw new InitializationException("Could not create help service", x);
    }
  }
  /**
   * Shows the table of contents for the help system.
   */
  public final void showHelpContents()
  {
    showHelpTopic(null);
  }
  /**
   * Shows the index for the help system.
   */
  public final void showHelpIndex()
  {
    showHelpTopic(null, "Index");
  }
  /**
   * Opens a search window for the help system.
   */
  public final void showHelpSearch()
  {
    showHelpTopic(null, "Search");
  }
  public void about()
  {
    JDialog dlg = createAboutDialog();
    dlg.pack();
    dlg.setLocationRelativeTo(this);
    dlg.show();
  }
  /**
   * Show an error dialog with a simple message This method is thread safe and
   * can be called from any thread.
   * 
   * @param message
   *          The message to display
   */
  public void error(String message)
  {
    error(message, null);
  }
  /**
   * Show an error dialog with a message and supporting detail. If the detail
   * implements HasNestedException the nested exception(s) will also be shown.
   * This method is thread safe and can be called from any thread.
   * 
   * @param source
   *          The owner of the error dialog
   * @param message
   *          The error message
   * @see org.freehep.util.HasNestedException
   */
  public void error(String message, Throwable detail)
  {
    error(this, message, detail);
  }
  /**
   * Show an error message in an error dialog. This method is thread safe and
   * can be called from any thread.
   * 
   * @param source
   *          The owner of the error dialog
   * @param message
   *          The error message
   */
  public void error(Component source, String message)
  {
    error(source, message, null);
  }
  /**
   * Show an error message in an error dialog, with optional supporting detail.
   * If the detail implements HasNestedException the nested exception(s) will
   * also be shown. This method is thread safe and can be called from any
   * thread.
   * 
   * @param source
   *          The owner of the error dialog
   * @param message
   *          The error message
   * @param detail
   *          A throwable giving more details on the error.
   * @see org.freehep.util.HasNestedException
   */
  public void error(final Component source, final String message, final Throwable detail)
  {
    if (!SwingUtilities.isEventDispatchThread())
    {
      Runnable run = new Runnable()
      {
        public void run()
        {
          error(source, message, detail);
        }
      };
      SwingUtilities.invokeLater(run);
    }
    else
    {
      final JButton details = new JButton("Details...");
      details.setEnabled(detail != null);
      details.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          JComponent messageComponent = null;
          JTabbedPane tabs = null;
          Throwable ex = detail;
          for (; ex != null;)
          {
            JTextArea ta = new JTextArea();
            PrintWriter pw = new PrintWriter(new DocumentOutputStream(ta.getDocument()));
            ex.printStackTrace(pw);
            pw.close();
            JScrollPane scroll = new JScrollPane(ta);
            ta.setCaretPosition(0);
            ta.setEditable(false);
            scroll.setPreferredSize(new Dimension(400, 300));
            if (ex instanceof HasNestedException)
            {
              if (messageComponent == null)
              {
                messageComponent = tabs = new JTabbedPane();
                tabs.addTab("Exception", scroll);
              }
              else
              {
                tabs.addTab("Nested Exception", scroll);
              }
              ex = ((HasNestedException) ex).getNestedException();
            }
            else
            {
              if (messageComponent == null)
                messageComponent = scroll;
              else
                tabs.addTab("Nested Exception", scroll);
              break;
            }
          }
          JDialog owner = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, details);
          final JDialog dlg = new JDialog(owner, "Error Details...");
          dlg.getContentPane().add(messageComponent);
          JButton close = new JButton("Close")
          {
            public void fireActionPerformed(ActionEvent event)
            {
              dlg.dispose();
            }
          };
          JPanel panel = new JPanel();
          panel.add(close);
          dlg.getContentPane().add(panel, BorderLayout.SOUTH);
          dlg.setDefaultCloseOperation(dlg.DISPOSE_ON_CLOSE);
          dlg.pack();
          dlg.setLocationRelativeTo(owner);
          dlg.show();
        }
      });
      getToolkit().beep();
      Object[] options = {"OK", details};
      JOptionPane.showOptionDialog(source, message, "Error...", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
          null, options, options[0]);
    }
  }
  protected JDialog createAboutDialog()
  {
    return new AboutDialog(this);
  }
  protected JPanel createToolBarHolder()
  {
    return new JPanel(new BorderLayout());
  }
  public JPanel getToolBarHolder()
  {
    if (toolBarHolder == null)
    {
      toolBarHolder = createToolBarHolder();
      if (toolBarVisible)
      {
        toolBarHolder.add(this);
        menuPane.add(toolBarHolder);
      }
    }
    return toolBarHolder;
  }
  /**
   * Override this to provide an application specific status bar
   */
  protected StatusBar createStatusBar()
  {
    return new StatusBar();
  }
  public StatusBar getStatusBar()
  {
    if (statusBar == null)
      statusBar = createStatusBar();
    return statusBar;
  }
  /**
   * Display a message in the applications status bar. This method is thread
   * safe and can be called from any thread.
   * 
   * @param message
   *          The message to display
   */
  public void setStatusMessage(String message)
  {
    if (l != null)
      l.setStage(message);
    else
      getStatusBar().setMessage(message);
  }
  public boolean getShowStatusBar()
  {
    return statusBarVisible;
  }
  /**
   * Show or Hide the status bar. This setting presists between sessions.
   * 
   * @param show
   *          true to display the status bar
   */
  public void setShowStatusBar(boolean show)
  {
    if (show != statusBarVisible)
    {
      StatusBar sb = getStatusBar();

      if (show)
      {
        menuPane.add(sb, BorderLayout.SOUTH);
      }
      else
      {
        menuPane.remove(sb);
      }
      statusBarVisible = show;
      menuPane.revalidate();
      PropertyUtilities.setBoolean(getUserProperties(), "showStatusBar", show);
    }
  }
  public boolean getShowToolBar()
  {
    return toolBarVisible;
  }
  /**
   * Show or Hide the tool bar. This setting presists between sessions.
   * 
   * @param show
   *          true to display the tool bar
   */
  public void setShowToolBar(boolean show)
  {
    if (show != toolBarVisible)
    {
      if (toolBarHolder != null)
      {
        if (show)
        {
          toolBarHolder.add(this);
          menuPane.add(toolBarHolder);
        }
        else
        {
          menuPane.remove(toolBarHolder);
          menuPane.add(this);
        }
      }
      menuPane.revalidate();
      menuPane.repaint();
      toolBarVisible = show;
      PropertyUtilities.setBoolean(getUserProperties(), "showToolBar", show);
    }
  }

  /**
   * Override this method to provide specialized application exit handling. For
   * example to double check with the user before exiting. The default
   * implementation saves the user preferences and then calls System.exit(0).
   */
  protected void exit()
  {
    String confirm = getUserProperties().getProperty("confirmQuit");
    if (!confirm.equals("never"))
    {
      String confirmQuitMessage = getUserProperties().getProperty("confirmQuitMessage");
      String confirmQuitTitle = getUserProperties().getProperty("confirmQuitTitle");
      Box message = Box.createVerticalBox();
      message.add(new JLabel(confirmQuitMessage));
      JCheckBox ask = new JCheckBox("Don't ask me this again");
      if (!confirm.equals("always"))
        message.add(ask);
      if (JOptionPane.showConfirmDialog(this, message, confirmQuitTitle, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
        return;
      if (ask.isSelected())
        getUserProperties().setProperty("confirmQuit", "never");
    }
    fireAboutToExit(new ApplicationEvent(this, ApplicationEvent.APPLICATION_EXITING));
    saveUserProperties();
    System.exit(0);
  }
  protected InitializationListener createSplashScreen()
  {
    final Properties prop = getUserProperties();
    boolean noSplash = getCommandLine().getOption("noSplash") != null;
    if (!noSplash && PropertyUtilities.getBoolean(prop, "showSplash", true))
    {
      String splashTitle = prop.getProperty("splashTitle");
      java.net.URL url = PropertyUtilities.getURL(prop, "splashImage", null);
      if (url == null)
        throw new InitializationException("Could not find splash image");
      final SplashScreen splash = new SplashScreen(new ImageIcon(url), "Starting...", splashTitle);
      final int nSteps = PropertyUtilities.getInteger(prop, "numberOfInitializationStages", 10);
      splash.setVisible(true);
      return new InitializationListener()
      {
        private int n = 0;
        public void setStage(String message)
        {
          splash.showStatus(message, 100 * n++ / nSteps);
        }
        public void failed(Throwable t)
        {
          splash.close();
        }
        public void succeeded()
        {
          splash.close();
          PropertyUtilities.setInteger(prop, "numberOfInitializationStages", n);
        }
      };
    }
    else
      return null;
  }
  /**
   * Creates a JFrame that holds the application. If the application has been
   * run before, and the user properties were saved, will attempt to restore the
   * old window position/size.
   */
  public JFrame createFrame(String[] argv) throws InitializationException
  {
    try
    {
      CommandLine cl = getCommandLine();
      boolean rc = cl.parse(argv);
      if (!rc)
      {
        System.out.println(cl.getHelp());
        System.exit(0);
      }
      // Deal with clearUserProperties
      if (cl.hasOption("clearUserProperties"))
        getUserProperties().clear();
      // Deal with explicitly set properties
      Vector v = cl.getMultiOption("D");
      Properties prop = getAppProperties();
      for (Enumeration e = v.elements(); e.hasMoreElements();)
      {
        String opt = e.nextElement().toString();
        StringTokenizer s = new StringTokenizer(opt, "=");
        int n = s.countTokens();
        if (n == 1)
          prop.setProperty(s.nextToken(), "true");
        else
          if (n == 2)
            prop.setProperty(s.nextToken(), s.nextToken());
          else
            throw new InitializationException("Illegal value \"" + opt + "\" for option");
      }
      // Deal with explicitly set user properties
      v = cl.getMultiOption("d");
      prop = getUserProperties();
      for (Enumeration e = v.elements(); e.hasMoreElements();)
      {
        String opt = e.nextElement().toString();
        StringTokenizer s = new StringTokenizer(opt, "=");
        int n = s.countTokens();
        if (n == 1)
          prop.setProperty(s.nextToken(), "true");
        else
          if (n == 2)
            prop.setProperty(s.nextToken(), s.nextToken());
          else
            throw new InitializationException("Illegal value \"" + opt + "\" for option");
      }
    }
    catch (CommandLineException x)
    {
      throw new InitializationException("Illegal Command Line Argument", x);
    }
    InitializationListener splash = createSplashScreen();
    if (splash != null)
      l = splash;
    try
    {
      setStatusMessage("Setting Look and Feel...");
      String laf = getUserProperties().getProperty("lookAndFeel", "System");

      if (laf.equalsIgnoreCase("System"))
        laf = UIManager.getSystemLookAndFeelClassName();
      else
        if (laf.equalsIgnoreCase("Java"))
          laf = UIManager.getCrossPlatformLookAndFeelClassName();
      try
      {
        UIManager.setLookAndFeel(laf);
        SwingUtilities.updateComponentTreeUI(this);
        // This is UGLY, we need a better way to handle it!
        if (statusBar != null)
          SwingUtilities.updateComponentTreeUI(statusBar);
      }
      catch (Exception x)
      {
      }


      setStatusMessage("Creating command manager");
      CommandTargetManager ctm = getCommandTargetManager();
      ctm.add(getCommandProcessor());

      setStatusMessage("Creating main frame");
      Properties appProp = getUserProperties();
      String title = appProp.getProperty("title");
      URL iconURL = PropertyUtilities.getURL(appProp, "icon", null);
      Image iconImage = iconURL == null ? null : new ImageIcon(iconURL).getImage();

      // TODO: Can we do this only when the menuPane is needed?
      menuPane = new JPanel(new BorderLayout());
      menuPane.add(this);

      String toolbar = appProp.getProperty("topLevelToolBar");
      if (toolbar != null)
      {
        JToolBar tb = getXMLMenuBuilder().getToolBar(toolbar);
        if (tb == null)
          throw new InitializationException("Could not find toolbar " + toolbar);
        getToolBarHolder().add(tb, BorderLayout.NORTH);
      }

      String topLevelMenu = appProp.getProperty("topLevelMenuBar");
      if (topLevelMenu != null)
      {
        menuBar = getXMLMenuBuilder().getMenuBar(topLevelMenu);
        if (menuBar == null)
          throw new InitializationException("Could not find menuBar " + topLevelMenu);
        menuPane.add(menuBar, BorderLayout.NORTH);
      }
      String topLevelPopupMenu = appProp.getProperty("topLevelPopupMenu");
      if (topLevelPopupMenu != null)
      {
        JPopupMenu popup = getXMLMenuBuilder().getPopupMenu(topLevelPopupMenu);
        if (popup == null)
          throw new InitializationException("Could not find popup menu " + topLevelPopupMenu);
        this.addMouseListener(new PopupListener(popup));
      }

      boolean showToolBar = PropertyUtilities.getBoolean(appProp, "showToolBar", true);
      setShowToolBar(showToolBar);

      boolean showStatus = PropertyUtilities.getBoolean(appProp, "showStatusBar", true);
      setShowStatusBar(showStatus);

      init();
      fireInitializationComplete(new ApplicationEvent(this, ApplicationEvent.INITIALIZATION_COMPLETE));

      setStatusMessage("Starting command manager");
      ctm.start();

      Rectangle bounds = getPreferredBounds();

      // Once the frame is created the application will not stop unless we
      // explicitly call
      // System.exit, which does not report the exception back to the invoking
      // application as
      // neatly as just throwing an exception from main(). We delay creating the
      // JFrame until
      // the last minute to attempt to mitigate this problem.

      JFrame frame = new JFrame(title);
      frame.setIconImage(iconImage);
      frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      frame.addWindowListener(new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          exit();
        }
      });
      GlobalMouseListener gml = new GlobalMouseListener(frame);
      gml.addMouseListener(new GlobalPopupListener());
      frame.setContentPane(menuPane);
      if (bounds == null)
        frame.pack();
      else
        frame.setBounds(bounds);

      l.succeeded();
      l = null;
      return frame;
    }
    catch (InitializationException x)
    {
      l.failed(x);
      throw x;
    }
  }
  protected void init()
  {
  }
  protected Rectangle getPreferredBounds()
  {
    // TODO: Check that we are not off the screen
    return PropertyUtilities.getRectangle(getUserProperties(), "window", null);
  }
  public Properties getUserProperties()
  {
    if (userProperties == null)
    {
      userProperties = createUserProperties();
      loadUserProperties(userProperties);
    }
    return userProperties;
  }
  protected Properties createUserProperties()
  {
    return new PropertyUtilities.TranslatedProperties(getAppProperties());
  }
  protected XMLReader createXMLReader()
  {
    try
    {
      return javax.xml.parsers.SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    }
    catch (SecurityException x) // JAXP doesn't work if downloaded into webstart
    {
      String className = getUserProperties().getProperty("SAXParser", "org.apache.crimson.parser.XMLReaderImpl");
      return (XMLReader) createObjectFromProperty(className, XMLReader.class);
    }
    catch (Exception x)
    {
      throw new NestedRuntimeException("Error creating XML Parser", x);
    }
  }
  protected XMLMenuBuilder createXMLMenuBuilder()
  {
    setStatusMessage("Adding menus");
    URL xml = PropertyUtilities.getURL(getUserProperties(), "menuXML", null);
    if (xml == null)
      throw new InitializationException("menuXML file not found");
    try
    {
      XMLMenuBuilder builder = new ApplicationXMLMenuBuilder();
      XMLReader reader = createXMLReader();
      builder.build(xml, reader);
      return builder;
    }
    catch (IOException x)
    {
      throw new InitializationException("IO Exception while reading " + xml, x);
    }
    catch (SAXException x)
    {
      x.printStackTrace();
      throw new InitializationException("XML Exception while reading " + xml, x);
    }
  }
  public XMLMenuBuilder getXMLMenuBuilder() throws InitializationException
  {
    if (xmlMenuBuilder == null)
      xmlMenuBuilder = createXMLMenuBuilder();
    return xmlMenuBuilder;
  }
  public Properties getAppProperties()
  {
    if (appProperties == null)
      appProperties = createAppProperties();
    return appProperties;
  }
  protected Properties createAppProperties() throws InitializationException
  {
    Properties prop = new PropertyUtilities.TranslatedProperties(System.getProperties());
    prop.setProperty("appName", appName);
    try
    {
      loadDefaultProperties(prop);
      String propFile = appName + ".properties";
      InputStream in = this.getClass().getResourceAsStream(propFile);
      if (in != null)
      {
        prop.load(in);
        in.close();
      }
      // else throw new InitializationException("Could not find application
      // properties: "+propFile);
      return prop;
    }
    catch (IOException x)
    {
      throw new InitializationException("Error initializing appplication properties", x);
    }
  }
  protected void loadDefaultProperties(Properties app) throws IOException
  {
    // load Default Properties
    InputStream in = Application.class.getResourceAsStream("Default.properties");
    app.load(in);
    in.close();
  }
  public String getAppName()
  {
    return appName;
  }
  private static Object createObjectFromProperty(String className, Class type) throws InitializationException
  {
    try
    {
      Class klass = Class.forName(className);
      if (!type.isAssignableFrom(klass))
        throw new InitializationException("Class " + className + " is not of type " + type);
      return klass.newInstance();
    }
    catch (InitializationException x)
    {
      throw x;
    }
    catch (Throwable t)
    {
      throw new InitializationException("Error creating " + className, t);
    }
  }
  public void addApplicationListener(ApplicationListener l)
  {
    listenerList.add(ApplicationListener.class, l);
  }
  public void removeApplicationListener(ApplicationListener l)
  {
    listenerList.remove(ApplicationListener.class, l);
  }
  protected void fireInitializationComplete(ApplicationEvent event)
  {
    if (listenerList.getListenerCount(ApplicationListener.class) > 0)
    {
      ApplicationListener[] listener = (ApplicationListener[]) listenerList.getListeners(ApplicationListener.class);
      for (int i = 0; i < listener.length; i++)
      {
        listener[i].initializationComplete(event);
      }
    }
  }
  protected void fireAboutToExit(ApplicationEvent event)
  {
    if (listenerList.getListenerCount(ApplicationListener.class) > 0)
    {
      ApplicationListener[] listener = (ApplicationListener[]) listenerList.getListeners(ApplicationListener.class);
      for (int i = 0; i < listener.length; i++)
      {
        listener[i].aboutToExit(event);
      }
    }
  }
  public JMenuBar getMenuBar()
  {
    return menuBar;
  }
  private static Application theApp;
  private String appName;
  private ServiceManager serviceManager;
  private Properties appProperties;
  private Properties userProperties;
  private XMLMenuBuilder xmlMenuBuilder;
  private CommandProcessor commandProcessor;
  private CommandTargetManager commandTargetManager;
  private CommandLine commandLine;
  private HelpService helpService;
  private StatusBar statusBar;
  private JToolBar toolBar;
  private JMenuBar menuBar;
  private JPanel toolBarHolder;
  private JPanel menuPane;
  private Hashtable rflHash;
  private boolean statusBarVisible = false;
  private boolean toolBarVisible = false;
  private boolean init = false;
  private InitializationListener l = new DefaultInitlizationListener();

  private class DefaultInitlizationListener implements InitializationListener
  {
    public void setStage(String message)
    {
      System.out.println(message);
    }
    public void failed(Throwable t)
    {
      System.out.println("Initialization failed " + t);
    }
    public void succeeded()
    {
      System.out.println("Initialization complete");
    }
  }
  /**
   * Extends CommandProcessor to handle some standard Application commands. Also
   * allows the onXXX() enableXXX() methods to be declared on the application
   * itself, as well as the command processor, as a convenience for simple
   * applications.
   */
  public class ApplicationCommandProcessor extends CommandProcessor
  {
    public void onExit()
    {
      exit();
    }
    public void onAbout()
    {
      about();
    }
    public void onHelpContents()
    {
      showHelpContents();
    }
    public void onHelpSearch()
    {
      showHelpSearch();
    }
    public void onHelpIndex()
    {
      showHelpIndex();
    }
    public void onShowStatusBar(boolean show)
    {
      setShowStatusBar(show);
    }
    public void enableShowStatusBar(BooleanCommandState state)
    {
      state.setSelected(statusBarVisible);
      state.setEnabled(true);
    }
    public void onShowToolBar(boolean show)
    {
      setShowToolBar(show);
    }
    public void enableShowToolBar(BooleanCommandState state)
    {
      state.setSelected(toolBarVisible);
      state.setEnabled(toolBarHolder != null);
    }
    public CommandTarget acceptCommand(String command)
    {
      CommandTarget retValue = super.acceptCommand(command);
      if (retValue != null)
        return retValue;
      return super.acceptCommand(getApplication().getClass(), command);
    }
    protected void invoke(java.lang.reflect.Method method, Object[] args) throws IllegalAccessException,
        java.lang.reflect.InvocationTargetException
    {
      if (method.getDeclaringClass().isInstance(getApplication()))
      {
        method.invoke(getApplication(), args);
      }
      else
        super.invoke(method, args);
    }
  }
  protected class ApplicationCommandTargetManager extends CommandTargetManager
  {
    public void handleCommandError(Throwable t)
    {
      error("Error during command processing", t);
    }
  }
  private class ApplicationXMLMenuBuilder extends XMLMenuBuilder
  {
    protected JMenuItem createMenuItem(String className, String name, String type, String command) throws SAXException
    {
      JMenuItem result = super.createMenuItem(className, name, type, command);
      getCommandTargetManager().add(new CommandSourceAdapter(result));
      return result;
    }
    protected AbstractButton createToolBarItem(String className, String name, String type, String command)
        throws SAXException
    {
      AbstractButton result = super.createToolBarItem(className, name, type, command);
      getCommandTargetManager().add(new CommandSourceAdapter(result));
      return result;
    }
  }

  public static class InitializationException extends NestedRuntimeException
  {
    public InitializationException(String message, Throwable detail)
    {
      super(message, detail);
    }
    public InitializationException(String message)
    {
      super(message);
    }
  }
  public interface InitializationListener
  {
    void setStage(String stage);
    void failed(Throwable x);
    void succeeded();
  }
  public class AppPrintPreview extends PrintPreview
  {
    public void setPrintable(Printable painter) throws PrinterException
    {
      setPrintable(painter, getServiceManager().getDefaultPage());
    }
    protected boolean onPrint(Pageable document) throws PrinterException
    {
      return getServiceManager().print(document);
    }
    protected void onError(PrinterException x)
    {
      error(this, "Print Error", x);
    }
  }
}
