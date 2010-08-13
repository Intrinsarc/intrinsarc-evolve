package com.intrinsarc.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;
import javax.swing.border.*;



public class PreferenceTab
{
  private String name;
  private Set<PreferenceSlot> slots = new TreeSet<PreferenceSlot>(
  		new Comparator<PreferenceSlot>()
  		{
				public int compare(PreferenceSlot o1, PreferenceSlot o2)
				{
					return o1.getName().compareTo(o2.getName());
				}
  			
  		});
  private Set<PreferenceSlotEditor> myEditors;
  private PreferencesFacet preferences;
  
  public PreferenceTab(PreferencesFacet preferences, String name)
  {
    this.preferences = preferences;
    this.name = name;
  }

  public String getName()
  {
    return name;
  }
  
  public void addSlot(PreferenceSlot slot)
  {
    if (getSlot(slot.getName()) == null)
    {
      slot.setTab(this);
      slots.add(slot);
    }
  }
  
  public PreferenceSlot getSlot(String name)
  {
    for (PreferenceSlot slot : slots)
      if (slot.getName().equals(name))
        return slot;
    return null;
  }
  
  public Set<PreferenceSlot> getSlots()
  {
    return slots;
  }
  
  public void refresh()
  {
    for (PreferenceSlotEditor editor : myEditors)
      editor.refresh();    
  }

  public JComponent getVisualComponent(UnappliedPreferencesFacet unapplied, Preferences registry, JButton apply, final Map<String, PreferenceSlotEditor> editors)
  {
    // assemble the attributes, then ask them for their visual components
    GridBagConstraints gbcLeft = new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);
    GridBagConstraints gbcRight = new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 0, 1, 0), 0, 0);
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
    // add each slot in turn
    myEditors = new HashSet<PreferenceSlotEditor>();
    for (PreferenceSlot slot : slots)
    {
      // add the name and the setter
      gbcLeft.insets = new Insets(10, 0, 1, 0);
      JLabel label = new JLabel(slot.getName());
      label.setToolTipText(slot.getDescription());
      panel.add(label, gbcLeft);
      gbcLeft.insets = new Insets(1, 0, 1, 0);
      
      // increment
      gbcLeft.gridy++;
      gbcRight.gridy++;
      
      PreferenceType type = slot.getType();
      String fullName = slot.getPreference().getRegistryName();
      PreferenceSlotEditor editor = type.makeEditor(unapplied, preferences, slot, panel, gbcLeft, gbcRight, registry, editors); 
      editors.put(fullName, editor);
      myEditors.add(editor);
      
      // increment
      gbcLeft.gridy++;
      gbcRight.gridy++;
    }
    gbcLeft.fill = GridBagConstraints.BOTH;
    gbcLeft.weighty = 1;
    panel.add(new JPanel(), gbcLeft);
    
    // listen to the apply button
    apply.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        for (PreferenceSlotEditor editor : myEditors)
          editor.apply();
      }
    });

    JScrollPane scroller = new JScrollPane(panel);
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    return scroller;
  }

	public void apply()
	{
    for (PreferenceSlotEditor editor : myEditors)
      editor.apply();
	}

	public void clearSlots()
	{
	  slots.clear();
	  myEditors = null;
	}
}

