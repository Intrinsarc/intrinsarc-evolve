package com.intrinsarc.idraw.environment;

import java.io.*;
import java.net.*;

public class BrowserInvoker
{
  private static final String BROWSER_EXECUTABLE = "Web browser executable";

  public static void registerPreferenceSlots()
  {
    // add a default browser slot
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference("Locations", BROWSER_EXECUTABLE),
        new PreferenceTypeFile(),
        "The browser executable used for showing html content.");
  }
  
  /**
   * parts of the code taken from http://forum.java.sun.com/thread.jspa?threadID=5163633
   * @param url
   * @return null for success, or a string for an error
   */
  public static String openBrowser(URL url)
  {
    PreferencesFacet prefs = GlobalPreferences.preferences;

    // if we have a specific browser executable, use this
    String browserExecutable = prefs.getSlot(
        new Preference("Locations", BROWSER_EXECUTABLE), true).getStringValue(prefs.getRegistry());
    if (browserExecutable != null && browserExecutable.trim().length() != 0)
    {
      Runtime rt = Runtime.getRuntime();
      try
      {
        rt.exec(new String[] { browserExecutable, url.toString() });
      }
      catch (IOException e)
      {
        return "Cannot open browser '" + browserExecutable + "'";
      }
    }
    else
    {
      String osName = System.getProperty("os.name").toLowerCase();
      Runtime rt = Runtime.getRuntime();
      if (osName.indexOf("win") >= 0)
      {
        try
        {
          rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
        }
        catch (IOException e)
        {
          return "Could not start default windows browser";
        }
      }
      else if (osName.indexOf("mac") >= 0)
      {
        try
        {
          rt.exec( "open " + url);
        }
        catch (IOException ex)
        {
          return "Could not start default macintosh browser";
        }
      }
      else if (osName.indexOf("ix") >=0 || osName.indexOf("ux") >=0 || osName.indexOf("sun") >=0)
      {
        String[] browsers = {"firefox", "safari", "mozilla", "konqueror", "netscape", "opera", "links", "lynx"};

        // Build a command string which looks like "browser1 "url" || browser2
        // "url" ||..."
        StringBuffer cmd = new StringBuffer();
        for (int i = 0 ; i < browsers.length ; i++)
          cmd.append((i == 0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

        try
        {
          rt.exec(new String[] { "sh", "-c", cmd.toString() });
        }
        catch (IOException ex)
        {
          return "Could not start up default browser";
        }
      }
    }
    return null;
  }

}
