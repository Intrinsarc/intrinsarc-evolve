package com.intrinsarc.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import com.intrinsarc.swing.*;

public abstract class PreferenceTypeVariable implements PreferenceType
{
	public PreferenceTypeVariable()
	{
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
      private String oldValue;
      private String newValue;

      {
        oldValue = unapplied.getUnappliedPreference(slot.getPreference());
        if (oldValue == null)
        	oldValue = slot.getStringValue(registry, null);
        newValue = oldValue == null ? "" : oldValue;

      	// add the line containing the set value
        JButton delete = new JButton("Delete");
        final JTextField field = new JTextField(oldValue);
        field.setPreferredSize(new Dimension(1, delete.getPreferredSize().height));
        field.addKeyListener(new KeyAdapter()
        {
          public void keyReleased(KeyEvent e)
          {
            newValue = field.getText();
            unapplied.storeUnappliedPreference(slot.getPreference(), newValue);
            preferences.refresh();
          }
        });
        panel.add(field, left);
    
        // add a deleter
        panel.add(delete, right);
        delete.addActionListener(new ActionListener()
        {
					public void actionPerformed(ActionEvent e)
					{
						haveDeleted(slot.getPreference());
					}
        });
        
        // add a file chooser
        JButton file = new JButton("Folder...");
        right.gridx++;
        panel.add(file, right);
        right.gridx--;
        file.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            if (newValue == null || newValue.length() == 0)
              newValue = PreferenceTypeDirectory.recent.getLastVisitedDirectory().getPath();
              
            JFileChooser chooser = new CustomisedFileChooser(newValue);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(panel) != JFileChooser.APPROVE_OPTION)
              return;
            field.setText(chooser.getSelectedFile().getAbsolutePath());
            newValue = field.getText();
            unapplied.storeUnappliedPreference(slot.getPreference(), newValue);
            preferences.refresh();
            PreferenceTypeDirectory.recent.setLastVisitedDirectory(new File(newValue));
          }
        });
      }

      public void apply()
      {
        if (newValue != null)
        	slot.setStringValue(registry, newValue);
      }

      public void refresh()
      {
      }

      public String getInterimValue()
      {
        return newValue;
      }

			public Class getType()
			{
				return PreferenceTypeVariable.class;
			}      
    };   
  }
  
	protected abstract void haveDeleted(Preference preference);
}

