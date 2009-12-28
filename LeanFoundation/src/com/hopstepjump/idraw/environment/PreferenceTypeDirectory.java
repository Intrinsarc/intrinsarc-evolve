package com.hopstepjump.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

/**
 * class must be stateless
 * @author amcveigh
 */
public class PreferenceTypeDirectory implements PreferenceType
{
  public static RecentFileList recent = new RecentFileList();

  public static void registerPreferenceSlots()
  {
    GlobalPreferences.preferences.addPreferenceSlot(
        new Preference(RecentFileList.RECENT_DOCUMENTS, "Last visited directory"),
        new PreferenceTypeDirectory(),
        "The last directory visited by a file chooser.");
    recent.registerPreferenceSlots();
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

      {
        // add the line containing the set value
        JButton file = new JButton("Folder...");
        final JTextField field = new JTextField(newValue);
        field.setPreferredSize(new Dimension(1, file.getPreferredSize().height));
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
    
        // add a file chooser
        panel.add(file, right);
        file.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                if (newValue == null || newValue.length() == 0)
                  newValue = PreferenceTypeDirectory.recent.getLastVisitedDirectory().getPath();
                  
                JFileChooser chooser = new JFileChooser(newValue);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (chooser.showOpenDialog(panel) != JFileChooser.APPROVE_OPTION)
                  return;
                field.setText(newValue = chooser.getSelectedFile().getAbsolutePath());
                PreferenceTypeDirectory.recent.setLastVisitedDirectory(new File(newValue));
              }
            });
      }

      public void apply()
      {
        if (newValue != null && !newValue.equals(oldValue))
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
				return PreferenceTypeDirectory.class;
			}      
    };   
  }
}

