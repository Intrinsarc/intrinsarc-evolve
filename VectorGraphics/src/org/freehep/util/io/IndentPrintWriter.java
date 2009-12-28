package org.freehep.util.io;

import java.io.*;

/**
 * A PrintWriter that keeps track of an indentation level and indents the output
 * appropriately.
 * 
 * @author Tony Johnson
 * @author Mark Donszelmann
 * @version $Id: IndentPrintWriter.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class IndentPrintWriter extends PrintWriter
{
  public IndentPrintWriter(Writer w, int level)
  {
    super(w);
    setIndent(level);
  }
  public IndentPrintWriter(Writer w)
  {
    this(w, 0);
  }
  public void print(boolean s)
  {
    doIndent();
    super.print(s);
  }
  public void print(char s)
  {
    doIndent();
    super.print(s);
  }
  public void print(char[] s)
  {
    doIndent();
    super.print(s);
  }
  public void print(double s)
  {
    doIndent();
    super.print(s);
  }
  public void print(float s)
  {
    doIndent();
    super.print(s);
  }
  public void print(int s)
  {
    doIndent();
    super.print(s);
  }
  public void print(long s)
  {
    doIndent();
    super.print(s);
  }
  public void print(Object s)
  {
    doIndent();
    super.print(s);
  }
  public void print(String s)
  {
    doIndent();
    super.print(s);
  }
  public void println()
  {
    indented = false;
    super.println();
  }

  // all other println's are implemented by the superclass in terms of print's

  private void doIndent()
  {
    if (indented)
      return;
    indented = true;
    for (int i = 0; i < indent; i++)
      super.print(indentString);
  }
  /**
   * Increase the indentation
   */
  public void indent()
  {
    indent++;
  }
  /**
   * Decrease the indentation
   */
  public void outdent()
  {
    indent--;
  }
  /**
   * Return the current indent count
   */
  public int getIndent()
  {
    return indent;
  }
  /**
   * Set the current indent count
   */
  public void setIndent(int level)
  {
    indent = level;
  }
  /**
   * Return the current indentString
   * 
   * @see #setIndentString(String)
   */
  public String getIndentString()
  {
    return indentString;
  }
  /**
   * Set the current indentString. Default is a single tab per indent level.
   * 
   * @param indentString
   *          The characters to prefix each line with (repeated for each indent
   *          level)
   */
  public void setIndentString(String indentString)
  {
    this.indentString = indentString;
  }

  private int indent = 0;
  private boolean indented = false;
  private String indentString = "  ";

}
