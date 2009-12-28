package org.freehep.application.studio;
import java.util.*;

import org.jdom.*;

/**
 * Provides a description of a plugin. The plugin may or may not be downloaded
 * or started.
 * 
 * @author tonyj
 * @version $Id: PluginInfo.java,v 1.1 2009-03-04 22:46:58 andrew Exp $
 */
public class PluginInfo
{
  /**
   * Builds a PluginInfo from a JDOM element
   */
  public PluginInfo(Element node)
  {
    Element info = node.getChild("information");
    name = info.getChildTextNormalize("name");
    author = info.getChildTextNormalize("author");
    version = info.getChildTextNormalize("version");
    loadAtStart = info.getChild("load-at-start") != null;
    mainClass = node.getChild("plugin-desc").getAttributeValue("class");
    List desc = info.getChildren("description");
    for (Iterator i = desc.iterator(); i.hasNext();)
    {
      Element d = (Element) i.next();
      String type = d.getAttributeValue("type");
      String text = d.getTextNormalize();
      if (type != null && type.equals("short"))
        title = text;
      else
        description = text;
    }

    Element resources = node.getChild("resources");
    if (resources != null)
    {
      List fileList = resources.getChildren("file");
      if (!fileList.isEmpty())
        files = new HashMap();
      for (Iterator i = fileList.iterator(); i.hasNext();)
      {
        Element f = (Element) i.next();
        String href = f.getAttributeValue("href");
        String location = f.getAttributeValue("location");
        files.put(location, href);
      }
      List propList = resources.getChildren("property");
      if (!propList.isEmpty())
        properties = new HashMap();
      for (Iterator i = propList.iterator(); i.hasNext();)
      {
        Element f = (Element) i.next();
        String name = f.getAttributeValue("name");
        String value = f.getAttributeValue("value");
        properties.put(name, value);
      }
    }
  }
  public boolean equals(Object o)
  {
    if (o instanceof PluginInfo)
    {
      return name.equals(((PluginInfo) o).name);
    }
    return false;
  }
  public int hashCode()
  {
    return name.hashCode();
  }
  String getMainClass()
  {
    return mainClass;
  }
  public String getName()
  {
    return name;
  }
  public String getAuthor()
  {
    return author;
  }
  public String getVersion()
  {
    return version;
  }
  public String getTitle()
  {
    return title;
  }
  public String getDescription()
  {
    return description;
  }
  boolean loadAtStart()
  {
    return loadAtStart;
  }
  public Map getFiles()
  {
    return files == null ? Collections.EMPTY_MAP : files;
  }
  public Map getProperties()
  {
    return properties == null ? Collections.EMPTY_MAP : properties;
  }
  private String name;
  private String author;
  private String version;
  private String mainClass;
  private String title;
  private String description;
  private Map files;
  private Map properties;
  private boolean loadAtStart;
}