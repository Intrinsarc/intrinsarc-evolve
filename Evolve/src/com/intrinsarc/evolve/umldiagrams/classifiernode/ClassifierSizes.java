/*
 * (c) Copyright 2001 MyCorporation.
 * All Rights Reserved.
 */
package com.intrinsarc.evolve.umldiagrams.classifiernode;

import com.intrinsarc.geometry.*;
import com.intrinsarc.idraw.figures.simplecontainernode.*;

import edu.umd.cs.jazz.component.*;

/**
 * @version 	1.0
 * @author
 */
public final class ClassifierSizes implements ContainerCalculatedSizes
{
	private ZText ztext;
	private ZText zOwningText;
	private boolean contentsEmpty;

	private UBounds outer;
	private UBounds insideOuter;
	private UBounds name;
	private UBounds owner;
	private UBounds icon;
	private UBounds attributes;
	private UBounds operations;
	private UBounds contents;
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

	/**
	 * Returns the attributes.
	 * @return UBounds
	 */
	public UBounds getAttributes()
	{
		return attributes;
	}

	/**
	 * Sets the attributes.
	 * @param attributes The attributes to set
	 */
	public void setAttributes(UBounds attributes)
	{
		this.attributes = attributes;
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
	 * Returns the contentsEmpty.
	 * @return boolean
	 */
	public boolean isContentsEmpty()
	{
		return contentsEmpty;
	}

	/**
	 * Sets the contentsEmpty.
	 * @param contentsEmpty The contentsEmpty to set
	 */
	public void setContentsEmpty(boolean contentsEmpty)
	{
		this.contentsEmpty = contentsEmpty;
	}

	/**
	 * Returns the operations.
	 * @return UBounds
	 */
	public UBounds getOperations()
	{
		return operations;
	}

	/**
	 * Sets the operations.
	 * @param operations The operations to set
	 */
	public void setOperations(UBounds operations)
	{
		this.operations = operations;
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
