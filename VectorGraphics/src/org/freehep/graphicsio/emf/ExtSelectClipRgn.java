// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * ExtSelectClipRgn TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ExtSelectClipRgn.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class ExtSelectClipRgn extends EMFTag implements EMFConstants
{

  private int mode;
  private Region rgn;

  ExtSelectClipRgn()
  {
    super(75, 1);
  }

  public ExtSelectClipRgn(int mode, Region rgn)
  {
    this();
    this.mode = mode;
    this.rgn = rgn;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    int length = emf.readDWORD();
    int mode = emf.readDWORD();
    ExtSelectClipRgn tag = new ExtSelectClipRgn(mode, length > 8 ? new Region(emf) : null);
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeDWORD(rgn.length());
    emf.writeDWORD(mode);
    rgn.write(emf);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  mode: " + mode + "\n" + rgn;
  }
}
