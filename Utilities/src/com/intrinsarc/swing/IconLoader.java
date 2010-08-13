/*
 * Created on Dec 15, 2003 by Andrew McVeigh
 */
package com.intrinsarc.swing;

import java.net.*;
import java.util.*;

import javax.swing.*;

/**
 * @author Andrew
 */
public class IconLoader
{
	private static final Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	
	public static final ImageIcon loadIcon(String iconName)
	{
		ImageIcon icon = icons.get(iconName);
		if (icon != null)
			return icon;
		URL resource = ClassLoader.getSystemResource(iconName);
		if (resource == null)
			System.err.println("Cannot find image " + iconName);
		icon = new ImageIcon(resource);
		icons.put(iconName, icon);
		return icon;
	}
}
