// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * EMF BitmapInfo
 * 
 * @author Mark Donszelmann
 * @version $Id: BitmapInfo.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class BitmapInfo
{

  private BitmapInfoHeader header;

  public BitmapInfo(BitmapInfoHeader header)
  {
    this.header = header;
  }

  public BitmapInfo(EMFInputStream emf) throws IOException
  {
    header = new BitmapInfoHeader(emf);
    // colormap not necessary for true color image
  }

  public void write(EMFOutputStream emf) throws IOException
  {
    header.write(emf);
    // colormap not necessary for true color image
  }

  public String toString()
  {
    return "  BitmapInfo\n" + header.toString();
  }
}
