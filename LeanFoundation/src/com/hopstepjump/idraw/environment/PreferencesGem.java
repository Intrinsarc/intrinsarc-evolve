package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.prefs.*;

import javax.swing.*;

import com.hopstepjump.gem.*;
import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.swing.*;

public class PreferencesGem implements Gem
{
  public static ImageIcon OK_ICON = IconLoader.loadIcon("tick.png");
  public static ImageIcon CANCEL_ICON = IconLoader.loadIcon("icon_cancel.png");

  private PreferencesFacet preferences = new EnvironmentPreferencesFacetImpl();
  private ArrayList<PreferenceTab> tabs = new ArrayList<PreferenceTab>();
	private Preferences registry;
  private Map<String, String> cachedValues = new HashMap<String, String>();
  private String prefix = System.getProperty("preferencePrefix");
  private Map<String, ImageIcon> tabIcons = new HashMap<String, ImageIcon>();
  
  public PreferencesGem(String subNode1, String subNode2)
  {
  	if (subNode1 == null)
  		registry = Preferences.userNodeForPackage(this.getClass());
  	else
  	if (subNode2 == null)
  		registry = Preferences.userNodeForPackage(this.getClass()).node(subNode1);
  	else
  		registry = Preferences.userNodeForPackage(this.getClass()).node(subNode1).node(subNode2);
  	if (prefix != null)
  		registry = registry.node(prefix);
  }
  
  public PreferencesFacet getPreferencesFacet()
  {
    return preferences;
  }
  
  private class EnvironmentPreferencesFacetImpl implements PreferencesFacet
  {
		public JComponent getVisualComponent(final JDialog dialog, boolean addButtons)
    {
      JPanel outer = new JPanel(new BorderLayout());
      JTabbedPane pane = new JTabbedPane();
      
      UnappliedPreferencesFacet unapplied = new UnappliedPreferencesFacet()
      {
        private Map<Preference, String> unappliedValues = new HashMap<Preference, String>();
        
        public String getUnappliedPreference(Preference pref)
        {
          if (!unappliedValues.containsKey(pref))
            return getRawPreference(pref).asString();
          return unappliedValues.get(pref);
        }

        public void storeUnappliedPreference(Preference pref, String value)
        {
      		unappliedValues.put(pref, value);
        }
      };
      
      // make the tabs
      JButton apply = new JButton("OK");
      apply.setIcon(OK_ICON);
      apply.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          dialog.dispose();
        }
      });
      
      JButton cancel = new JButton("Cancel");
      cancel.setIcon(CANCEL_ICON);
      cancel.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              dialog.dispose();
            }
          });
      
      Map<String, PreferenceSlotEditor> editors = new HashMap<String, PreferenceSlotEditor>();
      for (PreferenceTab tab : tabs)
        pane.addTab(
        		tab.getName(),
        		tabIcons.get(tab.getName()),
        		tab.getVisualComponent(unapplied, registry, apply, editors));
      for (PreferenceTab tab : tabs)
      	tab.refresh();
      
    	createVariableEditor(pane, unapplied, apply, tabIcons);
      
    	outer.add(pane, BorderLayout.CENTER);
      if (addButtons)
      {
	      JPanel buttons = new JPanel();
	      buttons.add(apply);
	      buttons.add(cancel);
	      outer.add(buttons, BorderLayout.SOUTH);
      }
      
      // clear the cache, as values may be changed
      clearCache();
      
      return outer;
    }

		protected void createVariableEditor(JTabbedPane pane, UnappliedPreferencesFacet unapplied, JButton apply, Map<String, ImageIcon> tabIcons)
		{
			// add the environment folders
      JPanel panel = new JPanel();
      // create the slots
      PreferenceTab envTab = new PreferenceTab(preferences, VariableEditor.VARIABLES);
      VariableEditor editor = new VariableEditor(registry, panel, envTab, unapplied, apply);
      editor.createPanel();
      pane.addTab(envTab.getName(), tabIcons.get(VariableEditor.VARIABLES), panel);
		}


		public PreferenceTab getTab(String name)
    {
      for (PreferenceTab tab : tabs)
        if (tab.getName().equals(name))
          return tab;
      return null;
    }
    
    public void refresh()
    {
      for (PreferenceTab tab : tabs)
        tab.refresh();
    }

    public PreferenceSlot addPreferenceSlot(Preference pref, PreferenceType type, String description)
    {
      // if the tab isn't there add it
      String tab = pref.getTab();
      PreferenceTab actual = getTab(tab);
      if (actual == null)
        tabs.add(actual = new PreferenceTab(preferences, tab));

      PreferenceSlot slot = new PreferenceSlot(pref, type, description); 
      actual.addSlot(slot);
      return slot;
    }
    
    private String checkPreferenceExistenceAndGetString(Preference pref)
    {
      // is this safe?
      if (System.getProperty("safePreferences") != null)
        return "";
      
      // if this is cached, return immediately
      String registryName = makeRegistryName(pref);
      if (cachedValues.containsKey(registryName))
        return cachedValues.get(registryName);        
      
      String tab = pref.getTab();
      String name = pref.getName();
      for (PreferenceTab actual : tabs)
      {
        if (actual.getName().equals(tab))
        {
          PreferenceSlot slot = actual.getSlot(name);
          if (slot == null)
            break;
          
          String value = slot.getStringValue(registry);
          cachedValues.put(registryName, value);
          return value;
        }
      }
      
      // don't throw an exception -- just complain instead
      System.err.println("$$ cannot find slot definition for " + makeRegistryName(pref));
      return null;
    }
    
    private String makeRegistryName(Preference pref)
    {
      return pref.getTab() + "/" + pref.getAccessName();
    }

    public PersistentProperty getPreference(Preference pref) throws PreferenceNotFoundException
    {
      String value = checkPreferenceExistenceAndGetString(pref);
      if (value == null)
        throw new PreferenceNotFoundException(
            "Cannot find preference for " + makeRegistryName(pref));
      return new PersistentProperty(value);
    }    
    
    public String getPrefix()
    {
      return prefix;
    }

    public void exportTo(File file) throws IOException, BackingStoreException
    {
      FileOutputStream os = new FileOutputStream(file);
      BufferedOutputStream bos = new BufferedOutputStream(os);
      try
      {
        registry.exportNode(bos);
      }
      finally
      {
        os.close();
      }
    }

    public void importFrom(File file) throws IOException, InvalidPreferencesFormatException
    {
      FileInputStream is = new FileInputStream(file);
      BufferedInputStream bis = new BufferedInputStream(is);
      try
      {
        Preferences.importPreferences(bis);
      }
      finally
      {
        is.close();
      }
    }

    public PreferenceSlot getSlot(Preference pref, boolean reportIfMissing)
    {
      // is this safe?
      if (System.getProperty("safePreferences") != null)
        return null;
      
      String tab = pref.getTab();
      String name = pref.getName();
      for (PreferenceTab actual : tabs)
      {
        if (actual.getName().equals(tab))
        {
          PreferenceSlot slot = actual.getSlot(name);
          if (slot == null)
            break;
          
          return slot;
        }
      }
      
      if (reportIfMissing)
      {
      	// don't throw an exception -- just complain instead
      	System.err.println("$$ cannot find slot definition for " + makeRegistryName(pref));
      }
      return null;
    }

    public Preferences getRegistry()
    {
      return registry;
    }

    public void clearCache()
    {
      cachedValues.clear();
    }

		public void apply()
		{
      for (PreferenceTab actual : tabs)
      	actual.apply();
      
      // make sure these are actually applied now.
      // if we get an error, just print it out and carry on...
      try
			{
				registry.flush();
			}
      catch (BackingStoreException e)
			{
				e.printStackTrace();
			}
		}

		public PersistentProperty getRawPreference(Preference pref)
		{
      String registryName = makeRegistryName(pref);
      return new PersistentProperty(registry.get(registryName, pref.getDefaultValue()));      
		}

		class Location { public Location(int start, int end) { this.start = start; this.end = end; } public int start; public int end; }
		
		public String expandVariables(String text) throws VariableNotFoundException
		{
			return expandVariables(text, new HashSet<String>());
		}
		
		public String getRawVariableValue(String name) throws VariableNotFoundException
		{
			String variableContents = registry.get(VariableEditor.VARIABLES + "/" + name, null);
			if (variableContents == null)
				throw new VariableNotFoundException("Cannot find variable " + name);
			return variableContents;
		}
		
		public String expandVariables(String text, Set<String> referencedVariables) throws VariableNotFoundException
		{
			// loop around until we have no variables left
			Location loc;
			int count = 0;
			while ((loc = locateFirstVariable(text)) != null)
			{
				// is this a problem?
				if (loc.end == loc.start + 1)
					throw new VariableNotFoundException("Problem with lone $ as variable");

				// find the variable
				String name = text.substring(loc.start + 1, loc.end);
				String variableContents = registry.get(VariableEditor.VARIABLES + "/" + name, null);
				referencedVariables.add(name);
				if (variableContents == null)
					throw new VariableNotFoundException("Cannot find variable " + name);
				
				text = text.substring(0, loc.start) + variableContents + text.substring(loc.end);
				
				// try really hard to resolve
				if (++count == 100)
					throw new VariableNotFoundException("Circular variable references found");
			}
			return text;
		}

		private Location locateFirstVariable(String text)
		{
			// scan to the first $ and then look for the next alphanumeric characters
			int start = text.indexOf('$');
			if (start == -1)
				return null;
			int length  = text.length();
			int end;
			for (end = start + 1; end < length; end++)
			{
				char c = text.charAt(end);
				if (!Character.isJavaIdentifierPart(c))
					break;
			}
			
			return new Location(start, end);
		}

		public Set<String> getVariablesReferenced(String text) throws VariableNotFoundException
		{
			Set<String> referenced = new LinkedHashSet<String>();
			expandVariables(text, referenced);
			return referenced;
		}

		public void registerTabIcon(String tabName, ImageIcon icon)
		{
			tabIcons.put(tabName, icon);
		}

		public void removeAll()
		{
			try
			{
				for (String key : registry.keys())
					registry.remove(key);
			}
			catch (BackingStoreException e)
			{
				// nothing to do...
			}
		}
		
		public void addPossibleVariableValue(String name, String value)
		{
	  	PreferenceTypeVariable variableType = new PreferenceTypeVariable()
	  	{
				@Override
				public void haveDeleted(Preference preference)
				{
				}
	  	};
	  	
	  	Preference pref = new Preference(VariableEditor.VARIABLES, name);
			PreferenceSlot slot = new PreferenceSlot(
				pref,
				variableType,
				"Environment variable");
	  	
			try
			{
		  	List<String> keys = new ArrayList<String>(Arrays.asList(registry.keys()));
				for (String check : keys)
				{
					if (VariableEditor.isVariable(check) && check.equals(pref.getRegistryName()))
						return;
				}
			}
			catch (BackingStoreException ex)
			{
			}
			
			// not found, so add in
			slot.setStringValue(registry, value);
		}
  }  
}
