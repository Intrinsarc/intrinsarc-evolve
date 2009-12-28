package com.hopstepjump.idraw.environment;

import java.util.prefs.*;

import com.hopstepjump.idraw.foundation.persistence.*;


public class PreferenceSlot
{
	private Preference pref;
  private PreferenceTab tab;
  private PreferenceType type;
  private String description;
  
  public PreferenceSlot(Preference pref, PreferenceType type, String description)
  {
  	this.pref = pref;
    this.type = type;
    this.description = description;
  }

  public String getDescription()
  {
    return description;
  }

  public String getName()
  {
    return pref.getName();
  }

  public PreferenceType getType()
  {
    return type;
  }
  
  public void setTab(PreferenceTab tab)
  {
    this.tab = tab;
  }

  public String getStringValue(Preferences registry)
  {
    return registry.get(pref.getRegistryName(), pref.getDefaultValue());
  }

  public String getStringValue(Preferences registry, String defaultValue)
  {
    return registry.get(pref.getRegistryName(), defaultValue);
  }

  public void setStringValue(Preferences registry, String newValue)
  {
    if (newValue == null)
      registry.remove(pref.getRegistryName());
    else
      registry.put(pref.getRegistryName(), newValue);
  }
  
  public PersistentProperty getPersistentProperty(Preferences registry)
  {
    return new PersistentProperty("", getStringValue(registry));
  }
  
  public PersistentProperty getPersistentProperty(Preferences registry, String defaultValue)
  {
    return new PersistentProperty(getStringValue(registry, defaultValue));
  }
  
  public Preference getPreference()
  {
    return pref;
  }

	public void remove(Preferences registry)
	{
		registry.remove(pref.getRegistryName());
	}
}
