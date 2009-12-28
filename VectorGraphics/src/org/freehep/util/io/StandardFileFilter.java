// Copyright 2003, SLAC, Stanford, U.S.A
package org.freehep.util.io;

import java.io.*;
import java.util.regex.*;

/**
 * Implements a standard file filter as is normally used in Unix and DOS. The
 * template characters are treated literally and are case sensitive with the
 * following exceptions:
 * <UL>
 * <LI>* matches zero or more consecutive characters
 * <LI>? matches exactly one character
 * <LI>\ causes the next character of the template to be treated literally. Use \\
 * for \, \* for * and \? for *.
 * </UL>
 * 
 * The template should specify slashes as a separator character (e.g. Unix
 * style).
 * 
 * @author Mark Donszelmann
 * @version $Id: StandardFileFilter.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class StandardFileFilter implements FileFilter
{

  private Pattern pattern;

  public StandardFileFilter(String template)
  {
    if (template.indexOf("/") < 0)
      template = "./" + template;

    // convert pattern
    StringBuffer s = new StringBuffer();
    for (int i = 0; i < template.length(); i++)
    {
      char ch = template.charAt(i);
      switch (ch)
      {
        case '?' :
          s.append(".");
          break;
        case '*' :
          s.append(".*");
          break;
        case '\\' :
          s.append("\\");
          i++;
          s.append(template.charAt(i));
          break;
        case '.' :
          s.append("\\.");
          break;
        case '^' :
          s.append("\\^");
          break;
        case '+' :
          s.append("\\+");
          break;
        case '$' :
          s.append("\\$");
          break;
        case '{' :
          s.append("\\{");
          break;
        case '(' :
          s.append("\\(");
          break;
        case '[' :
          s.append("\\[");
          break;
        case '|' :
          s.append("\\|");
          break;
        default :
          s.append(ch);
          break;
      }
    }
    pattern = Pattern.compile(s.toString());
  }

  public boolean accept(File pathname)
  {
    String name = pathname.getPath().replaceAll("\\" + File.separator, "/");
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }
}
