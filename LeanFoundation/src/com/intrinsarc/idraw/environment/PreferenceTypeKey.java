package com.intrinsarc.idraw.environment;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.*;

import javax.swing.*;

public class PreferenceTypeKey implements PreferenceType
{
	private WeakHashMap<JMenuItem, Boolean> items = new WeakHashMap<JMenuItem, Boolean>();
	
	public PreferenceTypeKey(JMenuItem item)
	{
		items.put(item, true);
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
    	private String oldValue =  slot.getStringValue(registry, defaultValue);
    	private String newValue = oldValue;
    	private JButton defaultButton;
    	private JTextField field = new JTextField(newValue);
  
    	private void update(String val)
    	{
    		newValue = val;
    		if (newValue != null && newValue.equals(defaultValue))
        	newValue = null;
        unapplied.storeUnappliedPreference(slot.getPreference(), newValue);
        preferences.refresh();
    	}
      
      {      	
        // add the line containing the set value
        field.addKeyListener(new KeyAdapter()
        {
					public void keyPressed(KeyEvent e)
					{
						KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(e);
						field.setText(replaceUnwanted(stroke.toString()));
						e.consume();
					}
					
					private String replaceUnwanted(String str)
					{
						return
						str.replace("pressed ", "").replace(" CONTROL", "").
						    replace(" SHIFT", "").replace(" ALT", "").replace("META", "");
					}

					public void keyTyped(KeyEvent e)
					{
						e.consume();
					}
        });
        final JButton clear = new JButton("Clear");
        field.setPreferredSize(new Dimension(10, clear.getPreferredSize().height));
        field.addKeyListener(new KeyAdapter()
        {
          public void keyReleased(KeyEvent e)
          {
            update(field.getText());
          }
        });
        panel.add(field, left);
        
        JPanel buttons = new JPanel();
        buttons.add(clear);
        defaultButton = new JButton("Default");
        buttons.add(defaultButton);
        panel.add(buttons, right);
        defaultButton.addActionListener(new ActionListener()
        {
					public void actionPerformed(ActionEvent e)
					{
						field.setText(defaultValue);
						update(defaultValue);
						field.requestFocus();
					}        	
        });
        clear.addActionListener(new ActionListener()
        {
					public void actionPerformed(ActionEvent e)
					{
						field.setText("");
						update("");
					}        	
        });
        lookForConflicts();
      }
  
      public void apply()
      {
        if (newValue != null && !newValue.equals(oldValue))
        {
          slot.setStringValue(registry, newValue);
          for (JMenuItem item : items.keySet())
          	item.setAccelerator(KeyStroke.getKeyStroke(newValue));
        }
        else
        if (newValue == null)
        {
        	slot.remove(registry);
          for (JMenuItem item : items.keySet())
          	item.setAccelerator(KeyStroke.getKeyStroke(defaultValue));
        }
      }

      private void lookForConflicts()
			{
      	// see if we have any key tab that conflicts with us
      	boolean conflict = false;
      	String me = getInterimValue();
      	if (me != null)
      	{
      		for (PreferenceSlotEditor editor : editors.values())
      		{
      			if (editor != this && getType() == editor.getType() && me.equals(editor.getInterimValue()))
      			{
      				conflict = true;
      				break;
      			}
      		}
      	}
      	field.setForeground(conflict ? Color.RED : null);
			}

			public void refresh()
      {
      	if (defaultButton != null)
      	{
      		String def = slot.getPreference().getDefaultValue();
      		defaultButton.setEnabled(newValue != null && !newValue.equals(def));
      	}
      	lookForConflicts();
      }

      public String getInterimValue()
      {
        return newValue == null ? defaultValue : newValue;
      }

			public Class getType()
			{
				return PreferenceTypeKey.class;
			}      
    };    
  }

	public void addMenuItem(JMenuItem item)
	{
		items.put(item, true);
	}  
}
