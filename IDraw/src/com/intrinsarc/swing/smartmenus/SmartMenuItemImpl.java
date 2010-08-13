/*
 * Created on Dec 2, 2002 by Andrew McVeigh
 */
package com.intrinsarc.swing.smartmenus;

import java.awt.*;

import javax.swing.*;

import com.intrinsarc.idraw.foundation.*;

/**
 * @author Andrew
 */
public class SmartMenuItemImpl implements SmartMenuItem
{
	private String menuName;
	private String sectionName;
	private JMenuItem item;
	/** fix for windows and other LNF's that don't offset items with no icon */
	private Icon nullIcon = new Icon()
	{
    public void paintIcon(Component c, Graphics g, int x, int y) {}
    public int getIconWidth() { return 16; }
    public int getIconHeight() { return 16; }
  };
	
	public SmartMenuItemImpl(String menuName, String sectionName, JMenuItem item)
	{
		this.menuName = menuName;
		this.sectionName = sectionName;
		this.item = item;
		if (item.getIcon() == null)
		  item.setIcon(nullIcon);
	}
	/**
	 * @return
	 */
	public JMenuItem getItem()
	{
		return item;
	}

	/**
	 * @return
	 */
	public String getMenuName()
	{
		return menuName;
	}

	/**
	 * @return
	 */
	public String getSectionName()
	{
		return sectionName;
	}

}
