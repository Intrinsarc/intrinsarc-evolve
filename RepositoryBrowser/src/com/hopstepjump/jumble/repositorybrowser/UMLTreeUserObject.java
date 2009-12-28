package com.hopstepjump.jumble.repositorybrowser;

import org.eclipse.emf.ecore.*;
import org.eclipse.uml2.*;

public class UMLTreeUserObject
{
  private Element element;
  private ShortCutType shortCutType;
  private EStructuralFeature feature;
  private String text;
  private boolean disabled;
  private int type;
  private int stereoHash;
  
  public UMLTreeUserObject(Element element, ShortCutType shortCutType, EStructuralFeature feature, String text, boolean disabled, int type, int stereoHash)
  {
    this.element = element;
    this.shortCutType = shortCutType;
    this.feature = feature;
    this.text = text;
    this.disabled = disabled;
    this.type = type;
    this.stereoHash = stereoHash;
  }

  public Element getElement()
  {
    return element;
  }

  public ShortCutType getShortCutType()
  {
    return shortCutType;
  }

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public EStructuralFeature getFeature()
	{
		return feature;
	}

  public boolean isDisabled()
  {
    return disabled;
  }

	public int getType()
	{
		return type;
	}

	public int getStereoHash()
	{
		return stereoHash;
	}

	public void setStereoHash(int stereoHash)
	{
		this.stereoHash = stereoHash;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}
