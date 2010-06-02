/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.hopstepjump.jumble.umldiagrams.requirementsfeaturenode;

import com.hopstepjump.geometry.*;
import com.hopstepjump.idraw.figures.simplecontainernode.*;

import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class RequirementsFeatureSizes implements ContainerCalculatedSizes
{
	private ZText ztext;
	private ZText zOwningText;

	private UBounds outer;
	private UBounds insideOuter;
	private UBounds name;
	private UBounds owner;
	private UBounds icon;
	private UBounds full;

	/**
	 * Returns the outer.
	 * @return UBounds
	 */
	public UBounds getOuter()
	{
		return outer;
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
	 * Returns the outer.
	 * @return UBounds
	 */
	public UBounds getInsideOuter()
	{
		return insideOuter;
	}

	/**
	 * Sets the outer.
	 * @param outer The outer to set
	 */
	public void setInsideOuter(UBounds insideOuter)
	{
		this.insideOuter = insideOuter;
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
	 * Sets the icon.
	 * @param icon The icon to set
	 */
	public void setIcon(UBounds icon)
	{
		this.icon = icon;
	}

	public UBounds getContents()
	{
		return outer;
	}

	/**
	 * Returns the text.
	 * @return UBounds
	 */
	public UBounds getName()
	{
		return name;
	}

	/**
	 * Sets the text.
	 * @param text The text to set
	 */
	public void setName(UBounds name)
	{
		this.name = name;
	}

	/**
	 * Returns the ztext.
	 * @return ZText
	 */
	public ZText getZtext()
	{
		return ztext;
	}

	/**
	 * Sets the ztext.
	 * @param ztext The ztext to set
	 */
	public void setZtext(ZText ztext)
	{
		this.ztext = ztext;
	}

	/**
	 * Returns the zOwningText.
	 * @return ZText
	 */
	public ZText getZOwningText()
	{
		return zOwningText;
	}

	/**
	 * Sets the zOwningText.
	 * @param zOwningText The zOwningText to set
	 */
	public void setZOwningText(ZText zOwningText)
	{
		this.zOwningText = zOwningText;
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
