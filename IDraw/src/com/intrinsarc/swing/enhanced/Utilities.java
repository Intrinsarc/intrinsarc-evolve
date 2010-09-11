package com.intrinsarc.swing.enhanced;

import java.awt.*;

import javax.swing.*;
import javax.swing.JPopupMenu.*;

public class Utilities
{
  public static void setEnabledRecursively(Container root, boolean enabled)
  {
    root.setEnabled(enabled);
    for (Component c : root.getComponents())
    {
      if (c instanceof Container)
        setEnabledRecursively((Container) c, enabled);
      else
      {
      	if (c.getName() == null || !c.getName().equals("uuid"))
      		c.setEnabled(enabled);
      	if (c.getName() != null && c.getName().equals("uuid"))
      		System.out.println("$$ found uuid");
      }
    }
    root.validate();
  }
  
  public static void addSeparator(JPopupMenu menu)
  {
  	// don't add if this is empty or if the last entry was also a separator
  	int size = menu.getComponentCount(); 
  	if (size == 0)
  		return;
  	if (menu.getComponent(size -1) instanceof Separator)
  		return;
  	menu.addSeparator();
  }

	public static void printStackTrace()
	{
		try
		{
			throw new Exception();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
