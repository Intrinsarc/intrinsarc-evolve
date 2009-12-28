package com.hopstepjump.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Map.*;

import javax.swing.*;

public class DropDownButton<T>
{
  public static final ImageIcon DROPDOWN_ICON = IconLoader.loadIcon("bullet_arrow_down.png");
  private Map<String, T> entries;
  private Set<DropDownListener<T>> listeners = new LinkedHashSet<DropDownListener<T>>();
  private JButton button;

  public DropDownButton(String name)
  {
    button = new JButton(name, DROPDOWN_ICON);
    button.setHorizontalTextPosition(SwingConstants.LEFT);
    button.setEnabled(false);
    
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        JPopupMenu popup = new JPopupMenu();
        
        // add the entries
        if (entries != null)
          for (final Entry<String, T> entry : entries.entrySet())
          {
            popup.add(new AbstractAction(entry.getKey())
            {  
              public void actionPerformed(ActionEvent e)
              {
                for (DropDownListener<T> listener : listeners)
                {
                  listener.actionChosen(entry.getValue());
                }
              }
            });
          }
        
        popup.show(button, 0, button.getHeight() - 1);        
      }
    });
  }
  
  public void setEntries(Map<String, T> entries)
  {
    this.entries = entries;
    button.setEnabled(entries != null && !entries.isEmpty());
  }
  
  public void addListener(DropDownListener<T> listener)
  {
    listeners.add(listener);
  }
  
  public void removeListener(DropDownListener<T> listener)
  {
    listeners.remove(listener);
  }
  
  public void clearListeners()
  {
    listeners.clear();
  }

  public Component getComponent()
  {
    return button;
  }
}
