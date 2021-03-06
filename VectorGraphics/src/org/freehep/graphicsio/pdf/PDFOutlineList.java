package org.freehep.graphicsio.pdf;

import java.io.*;

/**
 * Implements the Outline Dictionary (see Table 7.3).
 * <p>
 * 
 * @author Mark Donszelmann
 * @version $Id: PDFOutlineList.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class PDFOutlineList extends PDFDictionary
{

  PDFOutlineList(PDF pdf, PDFByteWriter writer, PDFObject object, PDFRef first, PDFRef last) throws IOException
  {
    super(pdf, writer, object);
    entry("Type", pdf.name("Outlines"));
    entry("First", first);
    entry("Last", last);
  }

  public void setCount(int count) throws IOException
  {
    entry("Count", count);
  }
}
