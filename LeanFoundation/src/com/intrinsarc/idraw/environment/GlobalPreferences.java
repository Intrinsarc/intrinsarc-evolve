package com.intrinsarc.idraw.environment;

import java.io.*;
import java.util.prefs.*;

import javax.swing.*;

import com.intrinsarc.idraw.foundation.persistence.*;


public class GlobalPreferences
{
  public static PreferencesFacet preferences = new PreferencesGem("Evolve", "preferences").getPreferencesFacet();
  
	public static void importPreferences(String fileName) throws IOException, InvalidPreferencesFormatException
	{
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(fileName));
		try
		{
			preferences.removeAll();
			Preferences.importPreferences(is);
		}
		finally
		{
			is.close();
		}
	}

	public static void registerKeyAction(String menus, JMenuItem item, String keyStroke, String description)
	{
		// does the slot already exist?
		String arrow = " \u27a4 ";
		String namePrefix = "";
		String[] split = menus.split("/");
		for (String s : split)
			namePrefix += s + arrow;
		String actionName = "<html><b>" + namePrefix + "</b>" + item.getText();
		Preference pref = new Preference("Keys", actionName, new PersistentProperty(keyStroke));
		PreferenceSlot slot = preferences.getSlot(pref, false);
		
		if (slot == null)
		{
			PreferenceTypeKey keyType = new PreferenceTypeKey(item);
			slot = 
				preferences.addPreferenceSlot(
						pref, 
						keyType,
						description);
		}
		else
		{
			// add this also to the key preference type
			PreferenceTypeKey keyType = (PreferenceTypeKey) slot.getType();
			keyType.addMenuItem(item);
		}
		
		// set the accelerator
  	String defaultValue = slot.getPreference().getDefaultValue();
  	String value =  slot.getStringValue(preferences.getRegistry(), defaultValue);
		item.setAccelerator(KeyStroke.getKeyStroke(value == null ? defaultValue : value));
	}
}
