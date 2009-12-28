/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.hopstepjump.swing.smartmenus;

import java.util.*;

import javax.swing.*;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.swing.enhanced.*;

class SmartEntry implements Comparable<SmartEntry>
{
	private String name;
	private String orderingHint;
	
	public SmartEntry(String name, String orderingHint)
	{
		this.name = name;
		this.orderingHint = orderingHint;		
	}
	
	public int compareTo(SmartEntry other)
	{
		// if the ordering hints are equal, base it on the name
		if (orderingHint.equals(other.orderingHint))
			return name.compareTo(other.name);
		return orderingHint.compareTo(other.orderingHint);
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof SmartEntry))
			return false;
			
		SmartEntry other = (SmartEntry) object;
		return name.equals(other.name);
	}

	public int hashCode()
	{
		return name.hashCode();
	}
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getOrderingHint()
	{
		return orderingHint;
	}

}



class MenuOrders
{
	private int defaultOrderingHint = 1;
	private Map<SmartEntry, SectionOrders> menus = new HashMap<SmartEntry, SectionOrders>();
	
	public MenuOrders()
	{
	}
	
	public SectionOrders addMenuOrderingHint(String menuName, String orderingHint)
	{
		SmartEntry menuEntry = new SmartEntry(menuName, orderingHint);
		if (!menus.containsKey(menuEntry))
		{
			if (orderingHint.equals(""))
				menuEntry = new SmartEntry(menuName, "z" + defaultOrderingHint++);
			menus.put(menuEntry, new SectionOrders());
		}
		return menus.get(menuEntry);
	}
	
	public void addSectionOrderingHint(String menuName, String sectionName, String orderingHint)
	{
		SectionOrders itemOrders = addMenuOrderingHint(menuName, "");
		itemOrders.addSectionOrderingHint(sectionName, orderingHint);
	}
	
	/**
	 * returns an iterator for the strings representing the menus
	 * -- the iterator is in the order specified by the ordering hints
	 */
	public Iterator menuOrderingIterator()
	{
		List<SmartEntry> smartEntries = new ArrayList<SmartEntry>(menus.keySet());
		Collections.sort(smartEntries);
		List<String> entries = new ArrayList<String>();
		
		for (Iterator iter = smartEntries.iterator(); iter.hasNext();)
		{
			SmartEntry smartEntry = (SmartEntry) iter.next();
			entries.add(smartEntry.getName());
		}
		return entries.iterator();
	}
	
	public Iterator sectionOrderingIterator(String menuName)
	{
		SectionOrders orders = menus.get(new SmartEntry(menuName, ""));
		return orders.sectionOrderingIterator();
	}
}


class SectionOrders
{
	private Set<SmartEntry> sections = new HashSet<SmartEntry>();
	
	public SectionOrders()
	{
	}
	
	public void addSectionOrderingHint(String sectionName, String orderingHint)
	{
		sections.add(new SmartEntry(sectionName, orderingHint));
	}
	
	/**
	 * returns an iterator for the strings representing the sections
	 * -- the iterator is in the order specified by the ordering hints
	 */
	public Iterator sectionOrderingIterator()
	{
		List<SmartEntry> smartEntries = new ArrayList<SmartEntry>(sections);
		Collections.sort(smartEntries);
		List<String> entries = new ArrayList<String>();
		
		for (Iterator iter = smartEntries.iterator(); iter.hasNext();)
		{
			SmartEntry smartEntry = (SmartEntry) iter.next();
			entries.add(smartEntry.getName());
		}
		return entries.iterator();
	}
}



/**
 * @author Andrew
 */
public class SmartMenuBarGem
{
	private SmartMenuBarFacetImpl smartMenuBarFacet = new SmartMenuBarFacetImpl();
	private MenuOrders orders = new MenuOrders();
	private Map<String, SmartMenuContributorFacet> contributors = new LinkedHashMap<String, SmartMenuContributorFacet>();
	private JMenuBar menuBar;
	
	public SmartMenuBarGem(JMenuBar menuBar)
	{
		this.menuBar = menuBar;
	}

	private class SmartMenuBarFacetImpl implements SmartMenuBarFacet
	{
			/*
		 * @see com.hopstepjump.menubuilder.SmartMenuBarFacet#addSmartMenuContributorFacet(java.lang.String, com.hopstepjump.menubuilder.SmartMenuContributorFacet)
		 */
		public void addSmartMenuContributorFacet(String name, SmartMenuContributorFacet contributor)
		{
			if (contributor != null)
				contributors.put(name, contributor);
		}

		/*
		 * @see com.hopstepjump.menubuilder.SmartMenuBarFacet#removeSmartMenuContributorFacet(java.lang.String)
		 */
		public void removeSmartMenuContributorFacet(String name)
		{
			contributors.remove(name);
		}

		public void addMenuOrderingHint(String menuName, String orderingHint)
		{
			orders.addMenuOrderingHint(menuName, orderingHint);
		}

		public void addSectionOrderingHint(String menuName, String sectionName, String orderingHint)
		{
			orders.addSectionOrderingHint(menuName, sectionName, orderingHint);
		}

		/*
		 * @see com.hopstepjump.menubuilder.SmartMenuBarFacet#rebuild(javax.swing.JMenuBar)
		 */
		public void rebuild()
		{
			List<SmartMenuItem> menuItems = formMenuItemsAndSetOrders();
			
			// make sure we have all the menu and section names
			for (SmartMenuItem item : menuItems)
				orders.addSectionOrderingHint(item.getMenuName(), item.getSectionName(), "y");
			
			// rebuild the menu bar
			menuBar.removeAll();
			
      for (int side = 0; side < 2; side++)
      {
  			for (Iterator menuIter = orders.menuOrderingIterator(); menuIter.hasNext();)
  			{
  				String menuName = (String) menuIter.next();

          // if the menu name starts with > it is on the right hand side
          boolean rightSide = menuName.startsWith(">");
          if (side == 0 && rightSide || side == 1 && !rightSide)
            continue;
          
          String realMenuName = rightSide ? menuName.substring(1) : menuName;
  				JMenu menu = new UpdatingJMenu(realMenuName);
  				
  				// get the sections of the menu
  				boolean start = true;
  				// loop around first for the left side, and next for the right side
  				for (Iterator sectionIter = orders.sectionOrderingIterator(menuName); sectionIter.hasNext();)
  				{
  					String sectionName = (String) sectionIter.next();
  					
  					// get the menu items
  					JMenuItem items[] = findMenuItems(menuItems, menuName, sectionName);
  					if (items.length != 0)
  					{
  						if (!start)
  							menu.addSeparator();
  						start = false;
  						
  						for (int lp = 0; lp < items.length; lp++)
  						{
  							JMenuItem item = items[lp];
  							menu.add(item);
  						}
  					}    				
  				}
  				
  				// add the menu to the bar
          if (!start)
            menuBar.add(menu);
  			}
  			
        // add horizontal glue so we can display on the right now
  			if (side == 0)
  			  menuBar.add(Box.createHorizontalGlue());          
      }
			
			menuBar.validate();
			menuBar.repaint();
		}

		/**
		 * @param menuName
		 * @param sectionName
		 * @return
		 */
		private JMenuItem[] findMenuItems(List items, String menuName, String sectionName)
		{
			List<JMenuItem> jMenuItems = new ArrayList<JMenuItem>();
			for (Iterator iter = items.iterator(); iter.hasNext();)
			{
				SmartMenuItem item = (SmartMenuItem) iter.next();
				if (item.getMenuName().equals(menuName) && item.getSectionName().equals(sectionName))
					jMenuItems.add(item.getItem());
			}
			return jMenuItems.toArray(new JMenuItem[0]);
		}

		/**
		 * @return
		 */
		private List<SmartMenuItem> formMenuItemsAndSetOrders()
		{
			List<SmartMenuItem> items = new ArrayList<SmartMenuItem>();
			for (Iterator contributorIter = contributors.values().iterator(); contributorIter.hasNext();)
			{
				SmartMenuContributorFacet contributor = (SmartMenuContributorFacet) contributorIter.next();
				
				List<SmartMenuItem> contributedItems = contributor.getSmartMenuItems(smartMenuBarFacet);
				if (contributedItems != null)  
					items.addAll(contributedItems);
			}
			return items;
		}

	}
	/**
	 * @return
	 */
	public SmartMenuBarFacet getSmartMenuBarFacet()
	{
		return smartMenuBarFacet;
	}
}
