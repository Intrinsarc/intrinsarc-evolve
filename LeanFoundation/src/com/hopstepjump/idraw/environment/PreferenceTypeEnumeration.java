package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.prefs.*;

import javax.swing.*;

public class PreferenceTypeEnumeration implements PreferenceType
{
  List<String> values = new ArrayList<String>();
  private boolean allowRefresh = true;
  
  public PreferenceTypeEnumeration()
  {    
  }

  public PreferenceTypeEnumeration(String[] arrayValues)
  {
    for (String value : arrayValues)
      values.add(value);
  }

  public PreferenceTypeEnumeration(Collection<String> collectionValues)
  {
    for (String value : collectionValues)
      values.add(value);
  }

  public PreferenceSlotEditor makeEditor(
      final UnappliedPreferencesFacet unapplied,
      final PreferencesFacet preferences,
      final PreferenceSlot slot,
      final JPanel panel,
      final GridBagConstraints left,
      final GridBagConstraints right,
      final Preferences registry,
      final Map<String, PreferenceSlotEditor> editors)
  {
    return new PreferenceSlotEditor()
    {
      private String oldValue = slot.getStringValue(registry, "");
      private String newValue = oldValue;
      private JComboBox combo;

      {
        // add the line containing the set value
        combo = new JComboBox();
        for (String value : getValues(unapplied))
          combo.addItem(value);
        
        // select the appropriate value
        combo.setSelectedItem(oldValue);
        combo.setPreferredSize(new Dimension(1, new JButton("A").getPreferredSize().height));
        combo.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        		// this is called even if we set the model directly, rather than through user interaction
          	// so we need to suppress it
          	if (allowRefresh)
          	{
          		newValue = (String) combo.getSelectedItem();
          		unapplied.storeUnappliedPreference(slot.getPreference(), newValue);
              preferences.refresh();
          	}
          }
        });
        left.gridwidth = 2;
        panel.add(combo, left);
        left.gridwidth = 1;
      }

      public void apply()
      {
        if (newValue != null && !newValue.equals(oldValue))
          slot.setStringValue(registry, newValue);
        applyChanges();
      }

      public void refresh()
      {
        allowRefresh = false;
        List<String> newValues = refreshValues(unapplied);
        if (newValues != null)
        {
          values = newValues;
          combo.setModel(new DefaultComboBoxModel());
          for (String value : getValues(unapplied))
            combo.addItem(value);
        }
        allowRefresh = true;
      }

      public String getInterimValue()
      {
        return newValue;
      }

			public Class<?> getType()
			{
				return PreferenceTypeEnumeration.class;
			}      
    };   
  }
  
  public List<String> getValues(UnappliedPreferencesFacet unapplied)
  {
    return values;
  }
  
  public List<String> refreshValues(UnappliedPreferencesFacet unapplied)
  {
    return null;
  }
  
  public void addValue(String value)
  {
    values.add(value);
  }
  
  public void applyChanges()
  {	
  }
}
