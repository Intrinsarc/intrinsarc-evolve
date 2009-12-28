package com.hopstepjump.idraw.foundation.persistence;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.hopstepjump.geometry.*;

/**
 *
 * (c) Andrew McVeigh 30-Aug-02
 *
 */
public class PersistentProperty implements Serializable
{
	private String name;
	private String value;
	private boolean isDefaultValue;  // defaults to false
	
	/**
	 * for anonymous values
	 * @param value
	 */
	public PersistentProperty(String value)
	{
		this.value = value;
	}
	
	/**
	 * string value constructor
	 **/
	public PersistentProperty(String name, String value)
	{
    this.name = name.intern();
		this.value = value;
	}

   /**
    *  string value constructor
    **/
  public PersistentProperty(String name, String value, String defaultValue)
  {
    setNameAndValue(name, value, defaultValue);
  }
  
  public void setStringValue(String value)
  {
    this.value = value; 
  }
  
  /**
   * point value constructor
   **/
  public PersistentProperty(String name, UPoint point)
  {
    this.name = name.intern();
    setUPointValue(point);
  }

  public void setUPointValue(UPoint value)
  {
    this.value = "" + truncatedDouble(value.getX()) + " " + truncatedDouble(value.getY());
  }
  
  /**
   * font value constructor
   **/
  public PersistentProperty(Font font)
  {
  	this(null, font, font);
  }
  public PersistentProperty(String name, Font font, Font defaultFont)
  {
    setNameAndValue(name, translateFont(font), "" + translateFont(defaultFont));
  }
  
  /**
   * keystroke constructor
   */
  public PersistentProperty(String name, KeyStroke stroke)
  {
    setNameAndValue(name, stroke.toString(), "");
  }
  
  public void setFontValue(Font value)
  {
    this.value = translateFont(value);
  }
  
  /**
   * dimension value constructor
   **/
  public PersistentProperty(String name, UDimension extent, UDimension defaultValue)
  {
    this.name = name.intern();
    setUDimensionValue(extent);  
    isDefaultValue = extent.equals(defaultValue);
  }

  public void setUDimensionValue(UDimension extent)
  {
    this.value = "" + truncatedDouble(extent.getWidth()) + " " + truncatedDouble(extent.getHeight());
  }
  
  /**
   * boolean value constructor
   **/
  public PersistentProperty(boolean value)
  {
  	this(null, value, false);
  }
  
  public PersistentProperty(String name, boolean value, boolean defaultValue)
  {
  	if (name != null)
  		this.name = name.intern();
    setBooleanValue(value);
    isDefaultValue = value == defaultValue;
  }

  public void setBooleanValue(boolean value)
  {
    this.value = new Boolean(value).toString();
  }
  

	public void setTriStateValue(Boolean state)
	{
		if (state == null)
			value = "";
		else
			setBooleanValue(state);
	}
  /**
   * int value constructor
   **/
	public PersistentProperty(int value)
	{
		this(null, value, 0);
	}
	public PersistentProperty(String name, int value, int defaultValue)
  {
		if (name != null)
			this.name = name.intern();
    setIntegerValue(value);
    isDefaultValue = value == defaultValue;
  }

  public void setIntegerValue(int value)
  {
    this.value = new Integer(value).toString();
  }
  
  public PersistentProperty(Color value)
  {
  	this(null, value, value);
  }

  public PersistentProperty(String name, Color value, Color defaultValue)
  {
  	if (name != null)
  		this.name = name.intern();
    setColorValue(value);
    isDefaultValue = value.equals(defaultValue);
  }

  public void setColorValue(Color value)
  {
  	if (value.getAlpha() != 255)
  		this.value = "" + value.getRed() + " " + value.getGreen() + " " + value.getBlue() + " " + value.getAlpha();
  	else
      this.value = "" + value.getRed() + " " + value.getGreen() + " " + value.getBlue();
  }

  
  /**
   * point value array constructor
   **/
  public PersistentProperty(String name, UPoint[] point)
  {
    this.name = name.intern();
    setUPointArrayValue(point);
  }
  
  private void setUPointArrayValue(UPoint[] point)
  {
    StringBuffer value = new StringBuffer("" + point.length);
    for (int lp = 0; lp < point.length; lp++)
    {
      value.append(" " + truncatedDouble(point[lp].getX()));
      value.append(" " + truncatedDouble(point[lp].getY()));
    }
    this.value = value.toString();
  }
  
  /** only works if strings have no whitespace in them -- e.g. uuids */
  public PersistentProperty(String name, Collection<String> uuids)
  {
    this.name = name.intern();
    setStringCollectionValue(uuids);
  }

  private void setStringCollectionValue(Collection<String> uuids)
  {
    StringBuffer value = new StringBuffer();
    for (String str : uuids)
      value.append(" " + str);
    this.value = value.toString();
  }

  private void setNameAndValue(String name, String value, String defaultValue)
  {
    this.name = name;
    this.value = value;
    this.isDefaultValue = (value == null && defaultValue == null) || (value.equals(defaultValue));
  }
  

	/**
	 * get the value as an object -- only used for passing around properties as above
	 * @return
	 */
	public Object asObject()
	{
		return value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String asString()
	{
		return value;
	}
	
	public boolean isDefaultValue()
	{
		return isDefaultValue;
	}

	private static String translateFont(Font font)
  {
    String style = "PLAIN";
    switch (font.getStyle())
    {
      case Font.BOLD:
        style = "BOLD";
        break;
      case (Font.BOLD + Font.ITALIC):
        style = "BOLDITALIC";
        break;
      case Font.ITALIC:
        style = "ITALIC";
        break;
    }
    return font.getFamily() + "-" + style + "-" + font.getSize();
  }

  public UPoint asUPoint()
	{
		// parse the string as 2 doubles
		StringTokenizer tok = new StringTokenizer(value);
		String num1 = (String) tok.nextElement();
		String num2 = (String) tok.nextElement();
		return new UPoint(new Double(num1).doubleValue(), new Double(num2).doubleValue());
	}
	
	/**
	 * returns the double to an accuracy of 1 decimal place, converted to a string
	 */
	private static String truncatedDouble(double x)
	{
		double value = Math.round(x * 10) / 10.0;
		if (value == (int) value)
			return "" + (int) value;
		return "" + value;
	}
		
	public UPoint[] asUPointArray()
	{
		// parse the string as 2 doubles
		StringTokenizer tok = new StringTokenizer(value);
		String numberString = (String) tok.nextElement();
		int number = new Integer(numberString).intValue();
		
		UPoint[] points = new UPoint[number];
		for (int lp = 0; lp < number; lp++)
		{
			String xString = (String) tok.nextElement();
			String yString = (String) tok.nextElement();
			points[lp] = new UPoint(new Double(xString).doubleValue(), new Double(yString).doubleValue());
		}
		return points;	
	}
	
  public List<String> asStringCollection()
  {
    // parse the string as a set of strings with whitespace separating them
    StringTokenizer tok = new StringTokenizer(value);
    List<String> strings = new ArrayList<String>();
    while (tok.hasMoreElements())
      strings.add((String) tok.nextElement());
    return strings;
  }
  
	public UDimension asUDimension()
	{
		// parse the string as 2 doubles
		StringTokenizer tok = new StringTokenizer(value);
		String num1 = (String) tok.nextElement();
		String num2 = (String) tok.nextElement();
		return new UDimension(new Double(num1).doubleValue(), new Double(num2).doubleValue());
	}
	
	public boolean asBoolean()
	{
		return new Boolean(value).booleanValue();
	}
	
	public Boolean asTriState()
	{
		if (value == null || value.length() == 0)
			return null;
		return asBoolean();
	}
	
  public Font asFont()
  {
    return Font.decode(value);
  }
  
  public KeyStroke asKeyStroke()
  {
  	return KeyStroke.getKeyStroke(value);
  }
  
  public Color asColor()
  {
  	if (value == null)
  		return Color.WHITE;
  	
    // parse the string as 3 doubles
    StringTokenizer tok = new StringTokenizer(value);
    try
    {
      String num1 = (String) tok.nextElement();
      String num2 = (String) tok.nextElement();
      String num3 = (String) tok.nextElement();
      String num4 = "255";
      if (tok.hasMoreElements())
      	num4 = (String) tok.nextElement();
      return new Color(
          new Integer(num1).intValue(),
          new Integer(num2).intValue(),
          new Integer(num3).intValue(),
          new Integer(num4).intValue());
    }
    catch (NoSuchElementException ex)
    {
      return Color.WHITE;
    }
    catch (NumberFormatException ex)
    {
    	System.out.println("$$ number has become white by format exception");
      return Color.WHITE;
    }
  }

  public int asInteger()
	{
    try
    {
      return new Integer(value).intValue();
    }
    catch (NumberFormatException ex)
    {
      return 0;
    }
	}
  
  public String toString()
  {
  	return name + " = " + value;
  }
}
