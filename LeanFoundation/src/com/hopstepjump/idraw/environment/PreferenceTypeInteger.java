package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import com.hopstepjump.swing.*;

public class PreferenceTypeInteger implements PreferenceType
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
      private String oldValue = slot.getStringValue(registry, defaultValue);
      private String newValue = oldValue;
      private JIntegerTextField field = new JIntegerTextField(number(newValue));
      private JButton defaultButton;

			private void update()
			{
        newValue = field.getText();
    		if (newValue.equals(defaultValue))
        	newValue = null;
        unapplied.storeUnappliedPreference(slot.getPreference(), newValue);
        preferences.refresh();
			}
      
      private Integer number(String val)
			{
      	try
      	{
      		return new Integer(val);
      	}
      	catch (NumberFormatException ex)
      	{
      		return 0;
      	}
			}

			{
        // add the line containing the set value
        field.setPreferredSize(new Dimension(1, new JButton("A").getPreferredSize().height));
        field.addKeyListener(new KeyAdapter()
            {
              public void keyReleased(KeyEvent e)
              {
              	update();
              }
            });
        
        // do we have a default?
        final String def = slot.getPreference().getDefaultValue();
        if (def != null)
        {
        	defaultButton = new JButton("Default");
      		refresh();
        	panel.add(defaultButton, right);
        	defaultButton.addActionListener(new ActionListener()
        	{
						public void actionPerformed(ActionEvent e)
						{
							field.setText(def);
							update();
						}
        	});
        }
        
        if (def == null)
        	left.gridwidth = 2;
        panel.add(field, left);    
        left.gridwidth = 1;
      }

      public void apply()
      {
        if (newValue == null || !newValue.equals(oldValue))
          slot.setStringValue(registry, newValue);
      }

      public void refresh()
      {
      	if (defaultButton != null)
      		defaultButton.setEnabled(!field.getText().equals(defaultValue));
      }

      public String getInterimValue()
      {
        return newValue == null ? defaultValue : newValue;
      }

			public Class getType()
			{
				return PreferenceTypeInteger.class;
			}      
    };   
  }
}
