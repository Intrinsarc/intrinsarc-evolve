package com.intrinsarc.swing.enhanced;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MenuAccordion
{
	/** make it so that the maximum height of a menu is MAX_HEIGHT, in columns
	 * 
	 * @param menu
	 * @param favoured
	 */
	private static final int MAX_VERTICAL_ITEMS = 26;
	public static void makeMultiColumn(JMenu menu, List<JMenuItem> favoured, boolean sort)
	{
		if (sort)
		{
			List<JMenuItem> items = new ArrayList<JMenuItem>();
			int size = menu.getItemCount();
			for (int lp = 0; lp < size; lp++)
				items.add(menu.getItem(lp));
			// sort, and re-add
			Collections.sort(items, new Comparator<JMenuItem>()
			{
				public int compare(JMenuItem o1, JMenuItem o2)
				{
					return o1.getText().compareTo(o2.getText());
				}			
			});
			menu.removeAll();
			for (JMenuItem item : items)
				menu.add(item);
		}
		
		if (menu.getItemCount() <= MAX_VERTICAL_ITEMS)
			return;
		
		// otherwise, make this multi-column
		menu.getPopupMenu().setLayout(new VerticalGridLayout(MAX_VERTICAL_ITEMS, 0));

		// add dummy menu items to fill it out...  makes sure the color is uniform
		int fill = (MAX_VERTICAL_ITEMS - menu.getItemCount() % MAX_VERTICAL_ITEMS) % MAX_VERTICAL_ITEMS;
			
		for (int lp = 0; lp < fill; lp++)
		{
			JMenuItem padding = new JMenuItem();
			padding.setEnabled(false);
			menu.add(padding);
		}
		
		// make the favoured items bold
		if (favoured != null)
			for (JMenuItem f : favoured)
				f.setText("<html><b>" + f.getText());
	}
	
	/**
	 * turn the children of the menu into a set of child menus which are parented by a menu of their first letter
	 * @param parent
	 */
	private static final int MAX_CHILD_SIZE = 70;
	public static void makeAccordianX(JMenu menu, List<JMenuItem> favoured)
	{
		// don't bother if we have a small number of elements
		int size = menu.getMenuComponentCount();
		if (size <= MAX_CHILD_SIZE)
			return;
		
		// get the 1st letters in order
		Set<String> firstSet = new HashSet<String>();
		for (int lp = 0; lp < size; lp++)
		{
			JMenuItem item = menu.getItem(lp);
			if (item.getText().length() == 0)
				firstSet.add(" ");
			else
				firstSet.add(item.getText().substring(0, 1));
		}
		List<String> firsts = new ArrayList<String>(firstSet);
		Collections.sort(firsts);
		
		// get the menu items in order
		List<JMenuItem> items = new ArrayList<JMenuItem>();
		for (int lp = 0; lp < size; lp++)
			items.add(menu.getItem(lp));
		Collections.sort(items, new Comparator<JMenuItem>()
		{
			public int compare(JMenuItem o1, JMenuItem o2)
			{
				return o1.getText().compareTo(o2.getText());
			}			
		});
		
		// reconstruct the menus
		menu.removeAll();
		
		// possibly add the favoured first
		if (favoured != null && !favoured.isEmpty())
		{
			for (JMenuItem item : favoured)
			{
				JMenuItem clone = new JMenuItem(item.getText());
				clone.setIcon(item.getIcon());
				clone.setHorizontalTextPosition(item.getHorizontalTextPosition());
				for (ActionListener listener : item.getActionListeners())
					clone.addActionListener(listener);
				menu.add(clone);				
			}
			menu.addSeparator();
		}
		
		for (String first : firsts)
		{
			// add this submenu and all the elements that start with this letter
			JMenu sub = new JMenu(first + "...  ");
			menu.add(sub);
			for (JMenuItem item : items)
				if (item.getText().startsWith(first) || item.getText().length() == 0 && first.equals(" "))
					sub.add(item);
		}
	}

	public static void sort(JMenu menu)
	{
		// sort the elements
		List<JMenuItem> items = new ArrayList<JMenuItem>();
		int size = menu.getItemCount();
		for (int lp = 0; lp < size; lp++)
			items.add(menu.getItem(lp));
		Collections.sort(items, new Comparator<JMenuItem>()
				{
					public int compare(JMenuItem o1, JMenuItem o2)
					{
						return o1.getText().compareTo(o2.getText());
					}
				});
		
		menu.removeAll();
		for (JMenuItem i : items)
			menu.add(i);
	}
}
