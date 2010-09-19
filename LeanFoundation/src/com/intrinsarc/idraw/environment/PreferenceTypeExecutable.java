package com.intrinsarc.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

import com.intrinsarc.swing.*;

public class PreferenceTypeExecutable implements PreferenceType
{
	private String option;

	public PreferenceTypeExecutable(String option)
	{
		this.option = option;
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
  		private String defaultValue = slot.getPreference().getDefaultValue();
      private String oldValue = slot.getStringValue(registry, defaultValue);
      private String newValue = oldValue;
      private JTextField field; 

      {
        // add the line containing the set value
        JButton file = new JButton("File...");
        field = new JTextField(newValue);
        field.setPreferredSize(new Dimension(10, file.getPreferredSize().height));
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
        JPanel group = new JPanel();
        JButton test = new JButton("Test");
        group.add(test);
        group.add(file);
        panel.add(group, right);
        file.addActionListener(new ActionListener()
            {
              public void actionPerformed(ActionEvent e)
              {
                JFileChooser chooser = new CustomisedFileChooser(newValue);
                if (chooser.showOpenDialog(panel) != JFileChooser.APPROVE_OPTION)
                  return;
                field.setText(newValue = chooser.getSelectedFile().getAbsolutePath());
              }
            });
        
        test.addActionListener(new ActionListener()
        {
					public void actionPerformed(ActionEvent e)
					{
						String cmd[] = new String[]{field.getText(), option};
						try
						{
							Process proc = Runtime.getRuntime().exec(cmd);
							StringBuilder out = new StringBuilder(); 
					    attachStreamListener(proc.getInputStream(), out);
					    attachStreamListener(proc.getErrorStream(), out);
					    proc.waitFor();
					    JOptionPane.showMessageDialog(null, out.toString(), "Command tested successfully", JOptionPane.INFORMATION_MESSAGE);
						}
						catch (Exception ex)
						{
					    JOptionPane.showMessageDialog(null, ex.getMessage(), "Problem running command", JOptionPane.ERROR_MESSAGE);
						}
					}
        });
      }
  
      public void apply()
      {
    		if (newValue.equals(defaultValue))
        	newValue = null;
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

			public Class<?> getType()
			{
				return PreferenceTypeFile.class;
			}      
    };   
  }
  
  private Thread attachStreamListener(final InputStream stream, final StringBuilder out)
  {
    Thread thread = new Thread()
    {
      public void run()
      {
        BufferedReader is = 
          new BufferedReader(new InputStreamReader(stream));
        
        String line;
        try
        {
          while ((line = is.readLine()) != null)
          {
            out.append(line + "\n");
          }
        }
        catch (IOException e)
        {
        }
      }
    };
    thread.start();
    return thread;
  }
}
