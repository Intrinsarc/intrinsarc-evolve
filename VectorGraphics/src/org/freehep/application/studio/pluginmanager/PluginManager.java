package org.freehep.application.studio.pluginmanager;

import java.util.*;

import javax.swing.*;

import org.freehep.application.*;
import org.freehep.application.studio.*;
import org.freehep.util.commanddispatcher.*;
import org.freehep.xml.menus.*;
import org.freehep.xml.util.*;
import org.jdom.*;
import org.jdom.input.*;

/**
 * A Plugin which provides a Plugin Manager.
 * 
 * @author tonyj
 * @version $Id: PluginManager.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public class PluginManager extends Plugin implements Runnable
{
  private boolean checkForPluginsAtStart;
  private String pluginURL;
  private List availablePlugins;

  protected void init() throws org.xml.sax.SAXException, java.io.IOException
  {
    final Studio app = getApplication();
    Properties user = app.getUserProperties();
    pluginURL = user.getProperty("pluginURL");
    checkForPluginsAtStart = PropertyUtilities.getBoolean(user, "checkForPluginsAtStart", false);

    XMLMenuBuilder builder = app.getXMLMenuBuilder();
    java.net.URL xml = PluginManager.class.getResource("PluginManager.menus");
    builder.build(xml);

    app.getCommandTargetManager().add(new Commands());

    if (checkForPluginsAtStart && pluginURL != null)
    {
      Thread t = new Thread(this);
      t.setDaemon(true);
      t.setPriority(t.NORM_PRIORITY - 1);
      t.start();
    }
  }
  List getAvailablePlugins()
  {
    return availablePlugins;
  }
  public void run()
  {
    try
    {
      // For the moment at least we will use JDOM here
      SAXBuilder builder = new SAXBuilder(true);
      builder.setEntityResolver(new ClassPathEntityResolver("plugin.dtd", Studio.class));
      java.net.URL url = new java.net.URL(pluginURL);
      java.io.InputStream in = url.openStream();
      try
      {
        availablePlugins = new ArrayList();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        for (Iterator iter = root.getChildren().iterator(); iter.hasNext();)
        {
          Element node = (Element) iter.next();
          PluginInfo plugin = new PluginInfo(node);
          availablePlugins.add(plugin);
        }
        System.out.println("Done");
      }
      finally
      {
        in.close();
      }
    }
    catch (Throwable t)
    {
      t.printStackTrace();
    }
  }
  public class Commands extends CommandProcessor
  {
    public void onPluginManager()
    {
      JDialog dlg = new PluginManagerDialog(getApplication(), PluginManager.this);
      dlg.pack();
      dlg.setModal(true);
      dlg.setLocationRelativeTo(getApplication());
      dlg.setTitle("Plugin Manager");
      dlg.show();
    }
  }
}