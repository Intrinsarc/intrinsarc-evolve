package com.hopstepjump.idraw.environment;

import java.io.*;
import java.util.*;

public class RecentFileList
{
  public static final String RECENT = "Recent repository ";
  public static final String RECENT_DOCUMENTS = "Recent documents";
  private static final int SIZE = 10;
  private PreferencesFacet prefs = GlobalPreferences.preferences;
  private List<String> recent = new ArrayList<String>();
  
  public RecentFileList()
  {
  }
  
  public void registerPreferenceSlots()
  {
    // register the slots
    for (int lp = 0; lp < SIZE; lp++)
      prefs.addPreferenceSlot(new Preference(RECENT_DOCUMENTS, RECENT + lp), new PreferenceTypeFile(), "The recently opened file, number " + lp + ".");
  }
  
  public File getLastVisitedDirectory()
  {
    String file = prefs.getRawPreference(new Preference(RECENT_DOCUMENTS, "Last visited directory")).asString();
    if (file == null || file.length() == 0)
      return new File(".");
    return new File(file);
  }
  
  public void setLastVisitedDirectory(File file)
  {
    if (file == null)
      return;
    
    File dir = file;
    if (file.isFile())
    {
      String parent = file.getParent();
      if (parent == null)
        return;
      dir = new File(parent);
    }
    prefs.getSlot(new Preference(RECENT_DOCUMENTS, "Last visited directory"), true).setStringValue(prefs.getRegistry(), dir.getPath());
  }

  private void load()
  {
    // read the recent files
    recent = new ArrayList<String>();
    for (int lp = 0; lp < SIZE; lp++)
    {
      String value = prefs.getRawPreference(new Preference(RECENT_DOCUMENTS, RECENT + lp)).asString();
      if (value != null && value.length() != 0)
      {
        recent.add(value);
      }
    }
  }
  
  private void save()
  {
    int index = 0;
    for (String name : recent)
    {
      PreferenceSlot slot = prefs.getSlot(new Preference(RECENT_DOCUMENTS, RECENT + index++), true);
      if (slot != null)
      {
        slot.setStringValue(prefs.getRegistry(), name);
      }
    }
    // clear out the rest to avoid scrolling
    for (; index < SIZE; index++)
    {
      PreferenceSlot slot = prefs.getSlot(new Preference(RECENT_DOCUMENTS, RECENT + index++), true);
      if (slot != null)
      {
        slot.setStringValue(prefs.getRegistry(), "");
      }
    }
    
    prefs.clearCache();
  }
  
  public String selectFile(int index)
  {
    load();
    if (index < 0 || index > SIZE)
      return null;
    bump(index);
    save();
    return recent.get(0);
  }
  
  private void bump(int index)
  {
    // move the index to the top
    String value = recent.get(index);
    recent.remove(index);
    recent.add(0, value);
  }

  public void addFile(String file)
  {
    load();
    
    // look for a match
    int index = 0;
    for (String recentName: recent)
    {
      if (file.equals(recentName))
      {
        // move it to the top
        bump(index);
        save();
        return;
      }
      index++;
    }
    
    // otherwise we need to add it
    recent.add(0, file);
    if (recent.size() > SIZE)
      recent.remove(SIZE);
    save();
  }

  public List<String> getRecentFiles()
  {
    load();
    return recent;
  }

  /**
   * remove the file from the list, usually done in the case of a file load error
   * @param name
   */
  public void removeFile(String name)
  {
    // find the index and remove the entry
    load();
    int index = 0;
    for (String recentName : recent)
    {
      if (name.equals(recentName))
      {
        recent.remove(index);
        save();
        return;
      }
      index++;
    }
  }
}
