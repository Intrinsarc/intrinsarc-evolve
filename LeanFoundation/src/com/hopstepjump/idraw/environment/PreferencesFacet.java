package com.hopstepjump.idraw.environment;

import java.io.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.persistence.*;

public interface PreferencesFacet extends Facet
{
	/** the prefix for this preferences */
  public String getPrefix();
  public JComponent getVisualComponent(JDialog dialog, boolean showButtons);

  public PreferenceSlot addPreferenceSlot(Preference pref, PreferenceType type, String description);
  /** the default values only get used if the slot is not set, and does not have a default value set */
  public PreferenceSlot getSlot(Preference pref, boolean reportIfMissing);
  public PersistentProperty getPreference(Preference pref) throws PreferenceNotFoundException;
  public PersistentProperty getRawPreference(Preference pref);


	public String expandVariables(String text) throws VariableNotFoundException;
	public String expandVariables(String text, Set<String> variablesReferenced) throws VariableNotFoundException;
	public String getRawVariableValue(String name) throws VariableNotFoundException;
	public Set<String> getVariablesReferenced(String text) throws VariableNotFoundException;
	
  public void exportTo(File file) throws IOException, BackingStoreException;
  public void importFrom(File file) throws IOException, InvalidPreferencesFormatException;
  public Preferences getRegistry();
  public void clearCache();
  public void refresh();
	public void apply();
	public void registerTabIcon(String tabName, ImageIcon icon);
	public void removeAll();
}
