package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import net.xoetrope.editor.color.*;

import com.hopstepjump.idraw.foundation.persistence.*;

public class PreferenceTypeColor implements PreferenceType
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
      private JPanel colorPanel = new JPanel();
      private JButton defaultButton;

			private void update(Color col)
			{
				modified = true;
				value.setColorValue(col);
        colorPanel.setBackground(value.asColor());
				if (value.asString().equals(defaultValue))
					value.setStringValue(null);
				unapplied.storeUnappliedPreference(
						slot.getPreference(),
						value.asString());
				preferences.refresh();
			}
      
      {
        // add the line containing the set value
        JButton color = new JButton("Color...");
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorPanel.setPreferredSize(new Dimension(1, color.getPreferredSize().height));
        colorPanel.setBackground(value.asColor());
        color.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            Color newColor = ExtendedColorChooser.showExtendedDialog(panel, "Choose color", value.asColor());
            if (newColor != null)
            {
            	update(newColor);
            }
          }
        });
        panel.add(colorPanel, left);
        JPanel rightPanel = new JPanel();
        rightPanel.add(color);
        panel.add(rightPanel, right);
        
        // do we have a default?
        if (defaultValue != null)
        {
        	defaultButton = new JButton("Default");
      		refresh();
        	rightPanel.add(defaultButton);
        	defaultButton.addActionListener(new ActionListener()
        	{
						public void actionPerformed(ActionEvent e)
						{
							update(new PersistentProperty(defaultValue).asColor());
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
				return PreferenceTypeColor.class;
			}      
    };   
  }
}
