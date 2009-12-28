package org.freehep.graphicsio.pdf;

import java.io.*;
import java.util.*;

/**
 * Implements the Page Tree Node (see Table 3.16).
 * <p>
 * 
 * @author Mark Donszelmann
 * @version $Id: PDFPageTree.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class PDFPageTree extends PDFPageBase
{

  Vector pages = new Vector();

  PDFPageTree(PDF pdf, PDFByteWriter writer, PDFObject object, PDFRef parent) throws IOException
  {
    super(pdf, writer, object, parent);
    entry("Type", pdf.name("Pages"));
  }

  public void addPage(String name)
  {
    pages.add(pdf.ref(name));
  }

  void close() throws IOException
  {
    Object[] kids = new Object[pages.size()];
    pages.copyInto(kids);
    entry("Kids", kids);
    entry("Count", kids.length);
    super.close();
  }
}
