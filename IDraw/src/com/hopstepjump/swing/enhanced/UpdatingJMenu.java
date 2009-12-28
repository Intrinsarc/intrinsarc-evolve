/*
 * Created on Dec 11, 2003 by Andrew McVeigh
 */
package com.hopstepjump.swing.enhanced;

import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * @author Andrew
 */
public class UpdatingJMenu extends JMenu implements UpdatingMenuItem
{
	private List<JMenuItem> items = new ArrayList<JMenuItem>();
	
	public UpdatingJMenu()
	{
		addListener();
	}

	/**
	 * @param s
	 */
	public UpdatingJMenu(String s)
	{
		super(s);
		addListener();
	}

	private void addListener()
	{
		addMenuListener(new MenuListener()
		{
			public void menuCanceled(MenuEvent e)
			{
			}

			public void menuDeselected(MenuEvent e)
			{
				// reenable the items (even if it is really disabled) so we get the key accelerators registered
				reenableItems();
			}

			public void menuSelected(MenuEvent e)
			{
				updateItems();
			}
		});
	}


	/**
	 *  Sets up the children items depending on the current desktop state
	 */
	private void updateItems()
	{
		for (JMenuItem item : items)
		{
			if (item instanceof UpdatingMenuItem)
				item.setEnabled(((UpdatingMenuItem) item).update());
		}
	}
	
	/**
	 *  Sets up the children menus depending on the current desktop state
	 */
	private void reenableItems()
	{
    for (JMenuItem item : items)
			if (item instanceof UpdatingJMenuItem)
				((UpdatingJMenuItem) item).setEnabled(true);
	}
	
	public JMenuItem add(JMenuItem menuItem)
	{
		items.add(menuItem);
		return super.add(menuItem);
	}

  public boolean update()
  {
  	return true;
  }
}
