package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.persistence.*;
import com.hopstepjump.swing.*;

public class PreferenceTypeBoolean implements PreferenceType
{	
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
    	private String defaultValue = slot.getPreference().getDefaultValue();
      private PersistentProperty value = new PersistentProperty(slot.getStringValue(registry, defaultValue));
      private boolean modified;
      private JCheckBox check = new VerboseCheckBox();
      private JButton defaultButton;
      
			private void update()
			{
				modified = true;
				value.setBooleanValue(check.isSelected());
				if (value.asString().equals(defaultValue))
					value.setStringValue(null);
				unapplied.storeUnappliedPreference(
						slot.getPreference(),
						value.asString());
				preferences.refresh();
			}

			{
        // add the line containing the set value
        check.setSelected(value.asBoolean());
        check.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
          	update();
          }
        });
        panel.add(check, left);
        
        // do we have a default?
        if (defaultValue != null)
        {
        	defaultButton = new JButton("Default");
      		refresh();
        	panel.add(defaultButton, right);
        	defaultButton.addActionListener(new ActionListener()
        	{
						public void actionPerformed(ActionEvent e)
						{
							check.setSelected(new PersistentProperty(defaultValue).asBoolean());
							update();
						}
        	});
        }
      }

      public void apply()
      {
        if (modified)
          slot.setStringValue(registry, value.asString());
      }

      public void refresh()
      {
      	if (defaultButton != null)
      	{
      		boolean bDef = new PersistentProperty(slot.getPreference().getDefaultValue()).asBoolean();
      		defaultButton.setEnabled(bDef != check.isSelected());
      	}
      }

      public String getInterimValue()
      {
        return value.asString() == null ? defaultValue : value.asString();
      }

			public Class getType()
			{
				return PreferenceTypeBoolean.class;
			}      
    };   
  }
}
