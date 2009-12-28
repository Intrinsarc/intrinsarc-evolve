package com.hopstepjump.idraw.foundation;

import java.util.*;

import javax.swing.*;

public class ToolFigureClassification
{
	private List<String> elements;
	private List<String> hints;
	private List<JMenuItem> items;
	
	public ToolFigureClassification(String elements, String hints)
	{
		this.elements = ToolClassification.expand(elements);
		this.hints = ToolClassification.expand(hints);
	}
	
	public void addMenuItem(JMenuItem item)
	{
		if (items == null)
			items = new ArrayList<JMenuItem>();
		items.add(item);
	}
	
	public List<JMenuItem> getMenuItems()
	{
		return items;
	}
	
	public boolean matches(ToolClassification palette)
	{
		return matches(elements, palette.getElements()) && matches(hints, palette.getHints());
	}
	
	private boolean matches(List<String> elements, List<String> paletteElements)
	{
		if (paletteElements != null)
		{
			if (elements == null)
				return false;

			// look for some intersection
			List<String> inter = new ArrayList<String>(elements);
			inter.retainAll(paletteElements);
			if (inter.size() == 0)
				return false;
		}
		return true;
	}
	
	public String toString()
	{
		return "ScreenToolClassification(" + elements + ", " + hints + ")";
	}
}
