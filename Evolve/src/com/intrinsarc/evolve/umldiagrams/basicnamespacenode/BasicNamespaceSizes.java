/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.basicnamespacenode;


import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;

import edu.umd.cs.jazz.*;


/**
 * @version 	1.0
 * @author
 */
public final class BasicNamespaceSizes implements ContainerCalculatedSizes
{
	/** outer is the non-containment size of this shape.  in non-icon mode, it is the same as full, otherwise, it only covers the icon */
	private UBounds outer;
	/** full is the full size of the shape, always */
	private UBounds full;
	private UBounds tab;
	private UBounds text;     // relative to (0,0)
	private UBounds body;
	private UBounds contents;
	private UBounds icon;	
	private UBounds owner;

	private boolean displayOnlyIcon;

	private ZTransformGroup zName;
	private ZTransformGroup zOwningName;

	/**
	 * Returns the displayOnlyIcon.
	 * @return boolean
	 */
	public boolean isDisplayOnlyIcon()
	{
		return displayOnlyIcon;
	}

	/**
	 * Returns the icon.
	 * @return UBounds
	 */
	public UBounds getIcon()
	{
		return icon;
	}

	/**
	 * Returns the outer.
	 * @return UBounds
	 */
	public UBounds getOuter()
	{
		return outer;
	}

	/**
	 * Returns the tab.
	 * @return UBounds
	 */
	public UBounds getTab()
	{
		return tab;
	}

	/**
	 * Returns the text.
	 * @return UBounds
	 */
	public UBounds getText()
	{
		return text;
	}

	/**
	 * Sets the displayOnlyIcon.
	 * @param displayOnlyIcon The displayOnlyIcon to set
	 */
	public void setDisplayOnlyIcon(boolean displayOnlyIcon)
	{
		this.displayOnlyIcon = displayOnlyIcon;
	}

	public void setIcon(UBounds icon)
	{
		this.icon = icon;
	}

	/**
	 * Sets the outer.
	 * @param outer The outer to set
	 */
	public void setOuter(UBounds outer)
	{
		this.outer = outer;
	}

	/**
	 * Sets the tab.
	 * @param tab The tab to set
	 */
	public void setTab(UBounds tab)
	{
		this.tab = tab;
	}

	/**
	 * Sets the text.
	 * @param text The text to set
	 */
	public void setText(UBounds text)
	{
		this.text = text;
	}

	/**
	 * Returns the body.
	 * @return UBounds
	 */
	public UBounds getBody()
	{
		return body;
	}

	/**
	 * Sets the body.
	 * @param body The body to set
	 */
	public void setBody(UBounds body)
	{
		this.body = body;
	}

	/**
	 * Returns the contents.
	 * @return UBounds
	 */
	public UBounds getContents()
	{
		return contents;
	}

	/**
	 * Sets the contents.
	 * @param contents The contents to set
	 */
	public void setContents(UBounds contents)
	{
		this.contents = contents;
	}

	/**
	 * Returns the zName.
	 * @return ZTransformGroup
	 */
	public ZTransformGroup getZName()
	{
		return zName;
	}

	/**
	 * Returns the zOwningName.
	 * @return ZTransformGroup
	 */
	public ZTransformGroup getZOwningName()
	{
		return zOwningName;
	}

	/**
	 * Sets the zName.
	 * @param zName The zName to set
	 */
	public void setZName(ZTransformGroup zName)
	{
		this.zName = zName;
	}

	/**
	 * Sets the zOwningName.
	 * @param zOwningName The zOwningName to set
	 */
	public void setZOwningName(ZTransformGroup zOwningName)
	{
		this.zOwningName = zOwningName;
	}

	/**
	 * Returns the owner.
	 * @return UBounds
	 */
	public UBounds getOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 * @param owner The owner to set
	 */
	public void setOwner(UBounds owner)
	{
		this.owner = owner;
	}

	/**
	 * Returns the full.
	 * @return UBounds
	 */
	public UBounds getFull()
	{
		return full;
	}

	/**
	 * Sets the full.
	 * @param full The full to set
	 */
	public void setFull(UBounds full)
	{
		this.full = full;
	}

}
