package org.freehep.application.studio;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import javax.swing.*;

import org.freehep.application.*;
import org.freehep.application.mdi.*;
import org.freehep.util.*;
import org.freehep.util.commandline.*;
import org.freehep.xml.util.*;
import org.jdom.*;
import org.jdom.input.*;

/**
 * 
 * @author tonyj
 * @version $Id: Studio.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public class Studio extends MDIApplication
{
  private FreeHEPLookup lookup;
  private List loadedPlugins = new ArrayList();
  private EventSender sender = new EventSender();
  private boolean debugExtensions = System.getProperty("debugExtensions") != null;
  private SAXBuilder builder;
  private ExtensionClassLoader extensionLoader;

  protected Studio(String name)
  {
    super(name);
    // For the moment at least we will use JDOM for parsing the plugin.xml files
    builder = new SAXBuilder(true);
    builder.setEntityResolver(new ClassPathEntityResolver("plugin.dtd", Studio.class));
  }
  private Studio()
  {
    this("Studio");
  }
  protected FreeHEPLookup createLookup()
  {
    return FreeHEPLookup.instance();
  }
  public EventSender getEventSender()
  {
    return sender;
  }
  public FreeHEPLookup getLookup()
  {
    if (lookup == null)
      lookup = createLookup();
    return lookup;
  }
  /**
   * Return the list of installed plugins. Each element in the list will be an
   * instance of PluginInfo
   * 
   * @see PluginInfo
   */
  public List getPlugins()
  {
    return loadedPlugins;
  }
  public static void main(String[] args)
  {
    new Studio().createFrame(args).show();
  }
  protected CommandLine createCommandLine()
  {
    CommandLine cl = super.createCommandLine();
    // register standard options
    cl.addOption("extdir", null, "directory", "Sets the directory to scan for plugins");
    return cl;
  }
  protected void init()
  {
    super.init();
    setStatusMessage("Loading extensions...");
    loadExtensions();
  }
  private void loadExtensions()
  {
    Map extensionClasspath = new TreeMap();
    Set plugins = new LinkedHashSet();

    // We look for extensions:
    // a) In the directory specified by the
    // org.freehep.application.studio.user.extensions property
    // b) In the directory specified by the
    // org.freehep.application.studio.group.extensions property
    // c) In the directory specified by the
    // org.freehep.application.studio.system.extensions property
    // The following defaults apply if the property is not specified
    // a) {user.home}/.FreeHEPPlugins
    // b) none
    // c) {java.home}/.FreeHEPPlugins

    // Note, we scan system dirs first, because files/plugins found later
    // replace earlier ones
    String[] extDirs = {getSystemExtensionsDir(), getGroupExtensionsDir(), getUserExtensionsDir()};
    scanExtensionDirectories(extDirs, extensionClasspath, plugins);

    // Create the extension Loader.

    URL[] urls = new URL[extensionClasspath.size()];
    extensionClasspath.values().toArray(urls);
    extensionLoader = new ExtensionClassLoader(urls);
    createLookup().setClassLoader(extensionLoader);
    // Make sure the extensionClassLoader is set as the contextClassLoader
    // so that services etc can be looked up in jar files from the extension
    // directory.
    Runnable lola = new Runnable()
    {
      public void run()
      {
        Thread.currentThread().setContextClassLoader(extensionLoader);
      }
    };
    lola.run();
    SwingUtilities.invokeLater(lola);

    // Now try loading the plugins
    loadPlugins(new ArrayList(plugins), extensionLoader);
  }
  public List buildPluginList(InputStream in) throws IOException
  {
    List result = new ArrayList();
    try
    {
      Document doc = builder.build(in);
      Element root = doc.getRootElement();
      for (Iterator iter = root.getChildren().iterator(); iter.hasNext();)
      {
        Element node = (Element) iter.next();
        PluginInfo plugin = new PluginInfo(node);
        result.add(plugin);
        if (debugExtensions)
          System.out.println("\t\tPlugin: " + plugin.getName());
      }
    }
    catch (JDOMException x)
    {
      if (debugExtensions)
        x.printStackTrace();
    }
    finally
    {
      in.close();
    }
    return result;
  }
  public void loadPlugins(List plugins, ClassLoader loader)
  {
    Iterator iter = plugins.iterator();
    while (iter.hasNext())
    {
      PluginInfo plugin = (PluginInfo) iter.next();
      if (plugin.loadAtStart())
      {
        try
        {
          if (debugExtensions)
            System.out.println("Loading plugin: " + plugin.getName());
          getAppProperties().putAll(plugin.getProperties());
          Class c = loader.loadClass(plugin.getMainClass());
          Plugin plug = (Plugin) c.newInstance();
          plug.setContext(this);
          loadedPlugins.add(plugin);
        }
        catch (Throwable t)
        {
          System.err.println("Unable to load plugin " + plugin.getName());
          t.printStackTrace();
        }
      }
    }
    // plugins may have added menus etc, so for good measure!
    revalidate();
  }
  private void scanExtensionDirectories(String[] dirs, Map extensionClasspath, Set plugins)
  {
    for (int i = 0; i < dirs.length; i++)
    {
      if (dirs[i] == null)
        continue;
      File extdir = new File(dirs[i]);
      if (debugExtensions)
        System.out.println("Seaching for extensions in: " + extdir);
      if (extdir.isDirectory())
        scanExtensionDirectory(extdir, extensionClasspath, plugins);
    }
  }
  private void scanExtensionDirectory(File extdir, Map extensionClasspath, Set plugins)
  {
    String[] files = extdir.list();
    for (int i = 0; i < files.length; i++)
    {
      if (files[i].endsWith(".jar"))
      {
        // Try to open the jar file, and see if it has a plugin manifest

        File f = new File(extdir, files[i]);
        if (f.length() > 0)
        {
          try
          {
            if (debugExtensions)
              System.out.println("\tFound: " + files[i]);
            JarFile jarFile = new JarFile(f);
            JarEntry manifest = jarFile.getJarEntry("PLUGIN-inf/plugins.xml");
            if (manifest != null)
            {
              InputStream in = jarFile.getInputStream(manifest);
              plugins.addAll(buildPluginList(in));
            }
            jarFile.close();
            extensionClasspath.put(f.getName(), f.toURI().toURL());
          }
          catch (IOException x)
          {
            System.err.println("Extension jar file " + files[i] + " could not be loaded" + x);
          }
        }
        else
        { // Files with length 0 have been flagged for deletion by the plugin
          // manager.
          boolean removed = f.delete();
        }
      }
    }
  }
  public String getUserExtensionsDir()
  {
    String extdir = getCommandLine().getOption("extdir");
    if (extdir != null)
      return extdir;
    return getAppProperties().getProperty("org.freehep.application.studio.user.extensions",
        "{user.home}/.FreeHEPPlugins");
  }
  public String getGroupExtensionsDir()
  {
    return getAppProperties().getProperty("org.freehep.application.studio.group.extensions");
  }
  public String getSystemExtensionsDir()
  {
    return getAppProperties().getProperty("org.freehep.application.studio.system.extensions",
        "{java.home}/FreeHEPPlugins");
  }
  public ExtensionClassLoader getExtensionLoader()
  {
    return extensionLoader;
  }

  protected void fireInitializationComplete(ApplicationEvent event)
  {
    super.fireInitializationComplete(event);
    getEventSender().broadcast(event);
  }

  protected void fireAboutToExit(ApplicationEvent event)
  {
    super.fireAboutToExit(event);
    getEventSender().broadcast(event);
  }

}