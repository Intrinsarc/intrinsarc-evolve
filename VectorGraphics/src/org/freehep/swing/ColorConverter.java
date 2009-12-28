package org.freehep.swing;

import java.awt.*;
import java.util.*;

/**
 * A utility class for converting strings to color's.
 * 
 * @author Tony Johnson
 * @author M.Donszelmann
 * @version $Id: ColorConverter.java,v 1.1 2009-03-04 22:46:59 andrew Exp $
 */
public class ColorConverter
{
  private static ColorConverter defaultInstance = new ColorConverter(true);
  private Map colorToString;
  private Map stringToColor;

  /**
   * Creates an instance of ColorConverter with the default color conversions
   */
  public ColorConverter()
  {
    this(false);
  }
  private ColorConverter(boolean init)
  {
    if (init)
    {
      colorToString = new HashMap();
      stringToColor = new HashMap();
      init();
    }
    else
    {
      colorToString = new HashMap(defaultInstance.colorToString);
      stringToColor = new HashMap(defaultInstance.stringToColor);
    }
  }
  private void init()
  {
    // first we look for the special named Java colors
    addEntry(Color.black, "Black");
    addEntry(Color.blue, "Blue");
    addEntry(Color.cyan, "Cyan");
    addEntry(Color.darkGray, "Dark Gray");
    addEntry(Color.gray, "Gray");
    addEntry(Color.green, "Green");
    addEntry(Color.lightGray, "Light Gray");
    addEntry(Color.magenta, "Magenta");
    addEntry(Color.orange, "Orange");
    addEntry(Color.pink, "Pink");
    addEntry(Color.red, "Red");
    addEntry(Color.white, "White");
    addEntry(Color.yellow, "Yellow");

    // now we look for the HTML3.2 colors (we look for all of them since
    // we don't want to depend on the RGB values of Java and HTML3.2 colors
    // being the same)
    addEntry(new Color(0, 0, 0), "Black");
    addEntry(new Color(192, 192, 192), "Silver");
    addEntry(new Color(128, 128, 128), "Gray");
    addEntry(new Color(255, 255, 255), "White");
    addEntry(new Color(128, 0, 0), "Maroon");
    addEntry(new Color(255, 0, 0), "Red");
    addEntry(new Color(128, 0, 128), "Purple");
    addEntry(new Color(255, 0, 255), "Fuchsia");
    addEntry(new Color(0, 128, 0), "Green");
    addEntry(new Color(0, 255, 0), "Lime");
    addEntry(new Color(128, 128, 0), "Olive");
    addEntry(new Color(255, 255, 0), "Yellow");
    addEntry(new Color(0, 0, 128), "Navy");
    addEntry(new Color(0, 0, 255), "Blue");
    addEntry(new Color(0, 128, 128), "Teal");
    addEntry(new Color(0, 255, 255), "Aqua");

    // now we look for the "all hail Crayola" colors :)
    // (we don't look for those which are also Java named colors)
    addEntry(new Color(0.1f, 0.1f, 0.1f), "Gray 10%");
    addEntry(new Color(0.2f, 0.2f, 0.2f), "Gray 20%");
    addEntry(new Color(0.3f, 0.3f, 0.3f), "Gray 30%");
    addEntry(new Color(0.4f, 0.4f, 0.4f), "Gray 40%");
    addEntry(new Color(0.5f, 0.5f, 0.5f), "Gray 50%");
    addEntry(new Color(0.6f, 0.6f, 0.6f), "Gray 60%");
    addEntry(new Color(0.7f, 0.7f, 0.7f), "Gray 70%");
    addEntry(new Color(0.8f, 0.8f, 0.8f), "Gray 80%");
    addEntry(new Color(0.9f, 0.9f, 0.9f), "Gray 90%");
    addEntry(new Color(255, 136, 28), "Orange");
    addEntry(new Color(120, 62, 27), "Brown");
    addEntry(new Color(0, 125, 32), "Forest Green");
    addEntry(new Color(11, 157, 150), "Turquoise");
    addEntry(new Color(109, 0, 168), "Purple");
    addEntry(new Color(168, 0, 126), "Magenta");
    addEntry(new Color(164, 207, 255), "Sky Blue");
    addEntry(new Color(225, 170, 255), "Violet");
    addEntry(new Color(255, 170, 210), "Light Magenta");
  }

  /**
   * Convert a color to a string, using the default ColorConverter
   * 
   * @param color
   *          The color to convert
   * @return The resulting String
   */
  public static String get(Color color)
  {
    return defaultInstance.colorToString(color);
  }

  /**
   * Converts a string to a color, using the default ColorConverter
   * 
   * @param name
   *          The string to convert
   * @return The resulting color
   * @throws ColorConversionException
   *           Thrown if the given string cannot be converted to a color
   */
  public static Color get(String name) throws ColorConversionException
  {
    return defaultInstance.stringToColor(name);
  }

  /**
   * Add an entry to the color converter map
   * 
   * @param c
   *          The color to add
   * @param name
   *          The name corresponding to the color
   */
  public void addEntry(Color c, String name)
  {
    stringToColor.put(name.toLowerCase(), c);
    colorToString.put(c, name);
  }
  /** Clear all the mappings from the color converter */
  public void clear()
  {
    stringToColor.clear();
    colorToString.clear();
  }

  /**
   * Convert the given color to a string.
   * 
   * @param color
   *          The color to be converted
   * @return Teh resulting string
   */
  public String colorToString(Color color)
  {
    String name = (String) colorToString.get(color);
    return (name != null) ? name : getRGBName(color);
  }

  /**
   * this method returns a Color. Colors are supposedly immutable and are
   * returned from the same table. The supported formats are:
   * 
   * <pre>
   *      by name:          &quot;yellow&quot;                      , where alpha is always 1.0
   *      by int r,g,b,a:   &quot;128, 255, 64, 255&quot;           , where alpha (a) is optional
   *      by float r,g,b,a: &quot;0.5, 1.0, 0.25, 1.0&quot;         , where alpha (a) is optional
   *      by single number: &quot;64637&quot; or &quot;0x0FFF08&quot;         , where alpha is always 1.0
   * </pre>
   * 
   * @param name
   *          name/number of the color
   * @return requested Color or defaulting to white in case of a invalid name
   *         (message is printed).
   * @throws ColorConversionException
   *           Thrown if the given string cannot be converted to a color
   */
  public Color stringToColor(String name) throws ColorConversionException
  {
    name = name.toLowerCase();

    // first look up if its name exists in the table
    Color c = (Color) stringToColor.get(name);

    if (c == null)
    {
      try
      {
        // check if the format is r,g,b,a
        if (name.indexOf(',') > 0)
        {
          StringTokenizer st = new StringTokenizer(name, ",");
          boolean isInteger = false;
          int[] i = new int[4];
          float[] f = new float[4];
          String red = st.nextToken().trim();
          try
          {
            i[0] = Integer.parseInt(red);
            isInteger = true;
          }
          catch (NumberFormatException nfe1)
          {
            f[0] = Float.parseFloat(red);
          }

          if (isInteger)
          {
            i[1] = Integer.parseInt(st.nextToken().trim());
            i[2] = Integer.parseInt(st.nextToken().trim());
            i[3] = (st.hasMoreTokens()) ? Integer.parseInt(st.nextToken().trim()) : 255;
            c = new Color(i[0], i[1], i[2], i[3]);
          }
          else
          {
            f[1] = Float.parseFloat(st.nextToken().trim());
            f[2] = Float.parseFloat(st.nextToken().trim());
            f[3] = (st.hasMoreTokens()) ? Float.parseFloat(st.nextToken().trim()) : 1.0f;
            c = new Color(f[0], f[1], f[2], f[3]);
          }
        }
        else
        {
          // the format should be rgb in a single number
          c = Color.decode(name);
        }
      }
      catch (Throwable t)
      {
        throw new ColorConversionException(name, t);
      }
    }
    return c;
  }

  private static String getRGBName(Color color)
  {
    return color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ", " + color.getAlpha();
  }

  /** An exception thrown if a given string cannot be converted to a Color */
  public static class ColorConversionException extends Exception
  {
    ColorConversionException(String value, Throwable cause)
    {
      super("Cannot convert " + value + " to Color");
      initCause(cause);
    }
  }
}
