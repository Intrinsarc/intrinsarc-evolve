package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.prefs.*;

import javax.swing.*;
import javax.swing.border.*;

public class VariableEditor
{
  public static final String VARIABLES = "Variables";
  
	private Preferences registry;
	private JPanel panel;
	private PreferenceTab envTab;
	private UnappliedPreferencesFacet unapplied;
	private JButton apply;
	
	// keeping track of deleted variables to apply later
	private Set<String> deleted = new HashSet<String>();
	private List<String> added = new ArrayList<String>();

	public VariableEditor(
			final Preferences registry,
			JPanel panel,
			PreferenceTab envTab,
			UnappliedPreferencesFacet unapplied,
			JButton apply)
	{
		this.registry = registry;
		this.panel = panel;
		this.envTab = envTab;
		this.unapplied = unapplied;
		this.apply = apply;
		
		apply.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// remove all entries in the delete list
				for (String delete : deleted)
					registry.remove(delete);
			}			
		});
	}

  /** create the panel, based on the slots present */
	public void createPanel()
	{
  	panel.removeAll();
  	panel.setLayout(new BorderLayout());
          
    // handle addition
  	JPanel addPanel = new JPanel(new BorderLayout());
  	addPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    final JButton add = new JButton("Add");
  	JPanel addInside = new JPanel(new BorderLayout());
  	addInside.add(add, BorderLayout.CENTER);
  	addInside.setBorder(new EmptyBorder(0, 0, 0, 10));
    addPanel.add(addInside, BorderLayout.WEST);
    final JTextField addName = new JTextField();
    addName.setPreferredSize(new Dimension(1, add.getPreferredSize().height));
    addName.addActionListener(new ActionListener()
    {
			public void actionPerformed(ActionEvent e)
			{
				add.doClick();
			}
    });    
    addPanel.add(addName, BorderLayout.CENTER);
    panel.add(addPanel, BorderLayout.NORTH);
    
    add.addActionListener(new ActionListener()
    {
			public void actionPerformed(ActionEvent e)
			{
				String name = addName.getText().trim();
				if (name.length() != 0)
				{
					String full = formFullName(name);
					if (!added.contains(full))
					{
						deleted.remove(full);
						added.add(full);
						createPanel();
					}
				}
			}
    });
    
    // add any slots
  	envTab.clearSlots();
  	PreferenceTypeVariable variableType = new PreferenceTypeVariable()
  	{
			@Override
			public void haveDeleted(Preference preference)
			{
				String full = preference.getRegistryName();
				added.remove(full);
				deleted.add(full);
				createPanel();
			}
  	};
  	
  	int size = VARIABLES.length() + 1;
  	
  	Map<String, PreferenceSlotEditor> editors = new HashMap<String, PreferenceSlotEditor>();
  	try
		{
    	List<String> keys = new ArrayList<String>(Arrays.asList(registry.keys()));
    	List<String> addTo = new ArrayList<String>(added);
    	addTo.removeAll(keys);
    	keys.addAll(addTo);
    	Collections.sort(keys);
    	
			for (String name : keys)
				if (isVariable(name) && !deleted.contains(name))
	    		envTab.addSlot(
	    				new PreferenceSlot(
	    						new Preference(VARIABLES, name.substring(size)),
	    						variableType,
	    						"Environment variable"));
		} catch (BackingStoreException e)
		{
		}
    
    // add the visual component to the panel
    panel.add(envTab.getVisualComponent(unapplied, registry, apply, editors), BorderLayout.CENTER);
    panel.revalidate();
  }
	
  
	private boolean isVariable(String key)
	{
		return key.startsWith(VARIABLES + "/");
	}
	
	private String formFullName(String key)
	{
		return VARIABLES + "/" + key;
	}
}
