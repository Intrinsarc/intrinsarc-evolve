package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.persistence.*;
import com.l2fprod.common.swing.*;

public class PreferenceTypeFont implements PreferenceType
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
      private JTextField fontName = new JTextField(value.asString());
      private JButton defaultButton;

			private void update(Font newFont)
			{
        modified = true;
      	value.setFontValue(newFont);
        fontName.setText(value.asString());
				if (value.asString().equals(defaultValue))
					value.setStringValue(null);
        unapplied.storeUnappliedPreference(slot.getPreference(), value.asString());
        preferences.refresh();
			}
      
      {
        // add the line containing the set value
        JButton font = new JButton("Font...");
        fontName.setEditable(false);
        fontName.setPreferredSize(new Dimension(1, font.getPreferredSize().height));
        font.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            Font newFont = JFontChooser.showDialog(panel, "Choose font", value.asFont());
            if (newFont != null)
            	update(newFont);
          }
        });
        panel.add(fontName, left);
        
        JPanel buttons = new JPanel();
        buttons.add(font);
        panel.add(buttons, right);
        
        // do we have a default?
        if (defaultValue != null)
        {
        	defaultButton = new JButton("Default");
      		refresh();
        	buttons.add(defaultButton);
        	defaultButton.addActionListener(new ActionListener()
        	{
						public void actionPerformed(ActionEvent e)
						{
							update(new PersistentProperty(defaultValue).asFont());
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
      		String def = slot.getPreference().getDefaultValue();
      		defaultButton.setEnabled(value.asString() != null && !value.asString().equals(def));
      	}
      }

      public String getInterimValue()
      {
        return value.asString() == null ? defaultValue : value.asString();
      }

			public Class getType()
			{
				return PreferenceTypeFont.class;
			}      
    };   
  }
}