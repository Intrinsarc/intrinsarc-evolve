package org.freehep.graphicsio.font;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import org.freehep.graphicsio.font.encoding.*;

public class FontUtilities
{

  private FontUtilities()
  {
  }

  public static List getAllAvailableFonts()
  {
    return Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
  }

  private static final Properties windowsFonts = new Properties();
  static
  {
    // Looks like Unicode MS makes thinner characters
    // List fontNames = getAllAvailableFonts();
    // String arial = fontNames.contains("Arial Unicode MS") ? "Arial Unicode
    // MS" : "Arial";
    String arial = "Arial";

    // logical fonts
    windowsFonts.setProperty("Dialog", arial);
    windowsFonts.setProperty("DialogInput", "Courier New");
    windowsFonts.setProperty("Serif", "Times New Roman");
    windowsFonts.setProperty("SansSerif", arial);
    windowsFonts.setProperty("Monospaced", "Courier New");

    // pdf fonts
    windowsFonts.setProperty("Courier", "Courier New");
    windowsFonts.setProperty("Helvetica", arial);
    windowsFonts.setProperty("Times-Roman", "Times New Roman");
    windowsFonts.setProperty("TimesRoman", "Times New Roman");
    windowsFonts.setProperty("Times", "Times New Roman");
    windowsFonts.setProperty("Symbol", "Symbol");
    windowsFonts.setProperty("ZapfDingbats", "Wingdings");
  }

  public static String getWindowsFontName(String fontName)
  {
    return windowsFonts.getProperty(fontName, fontName);
  }

  public static String getEncodedString(String string, String tableName)
  {
    CharTable charTable = Lookup.getInstance().getTable(tableName);
    return getEncodedString(string, charTable);
  }

  /**
   * Returns an unicode encoded string from an ascii encoded string, using the
   * supplied table.
   */
  public static String getEncodedString(String string, CharTable charTable)
  {
    if (charTable == null)
      return string;

    StringBuffer s = new StringBuffer();
    for (int i = 0; i < string.length(); i++)
    {
      int enc = string.charAt(i);
      String name = charTable.toName(enc);
      s.append((name != null) ? charTable.toUnicode(name) : (char) enc);
    }
    return s.toString();
  }

  public interface ShowString
  {
    public void showString(Font font, String string) throws IOException;
  }

  private static final CharTable STANDARD_CHAR_TABLES[] = {null, Lookup.getInstance().getTable("Symbol"),
      Lookup.getInstance().getTable("Zapfdingbats")};

  private static final Font STANDARD_FONT[] = {null, new Font("Symbol", Font.PLAIN, 10),
      new Font("ZapfDingbats", Font.PLAIN, 10),};

  /**
   * Shows a String and switches the encoding (and font) everytime the unicode
   * characters leave the range of the curent encoding. Outside the range of the
   * given latinTable, Symbol and ZapfDingbats are checked. If none of these
   * three encodings contain the unicode character, an undefined character is
   * used.
   */
  public static void showString(Font font, String string, CharTable latinTable, ShowString device) throws IOException
  {

    STANDARD_FONT[0] = font;
    STANDARD_FONT[1] = new Font("Symbol", Font.PLAIN, font.getSize());
    STANDARD_FONT[2] = new Font("ZapfDingbats", Font.PLAIN, font.getSize());
    STANDARD_CHAR_TABLES[0] = latinTable;

    char[] chars = string.toCharArray();
    String out = "";
    int lastTable = 0;

    for (int i = 0; i < chars.length; i++)
    {

      // find out suitable table and encoding of this character
      // try last table first
      int table = lastTable;
      char encoding = (char) STANDARD_CHAR_TABLES[table].toEncoding(chars[i]);
      // no success -> try all other tables
      if (encoding == 0)
      {
        table = -1;
        do
        {
          table++;
          if (table != lastTable)
          { // we already checked that
            encoding = (char) STANDARD_CHAR_TABLES[table].toEncoding(chars[i]);
          }
        }
        while ((encoding == 0) && (table < STANDARD_CHAR_TABLES.length - 1));
      }
      if (encoding == 0)
        table = lastTable;

      if ((table != lastTable) && (!out.equals("")))
      {
        // if font changes, write the old font and string so far
        device.showString(STANDARD_FONT[lastTable], out);
        out = "";
      }
      // append character to out
      out += encoding;
      lastTable = table;
    }

    device.showString(STANDARD_FONT[lastTable], out);
  }
}