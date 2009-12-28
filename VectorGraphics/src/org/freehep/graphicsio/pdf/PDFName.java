package org.freehep.graphicsio.pdf;

/**
 * Specifies a PDFName object.
 * <p>
 * 
 * @author Mark Donszelmann
 * @version $Id: PDFName.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class PDFName implements PDFConstants
{

  private String name;

  PDFName(String name)
  {
    this.name = name;
  }

  public String toString()
  {
    return "/" + name;
  }
}