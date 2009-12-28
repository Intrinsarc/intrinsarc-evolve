/*
 * ExtensionClassLoader.java
 *
 * Created on September 24, 2002, 10:42 AM
 */

package org.freehep.application.studio;
import java.net.*;

/**
 * 
 * @author tonyj
 */
public class ExtensionClassLoader extends URLClassLoader
{
  public ExtensionClassLoader(URL[] urls)
  {
    super(urls);
  }
  public void addURL(URL url)
  {
    super.addURL(url);
  }
}
