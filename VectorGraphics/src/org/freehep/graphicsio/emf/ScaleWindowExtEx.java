// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * ScaleWindowExtEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ScaleWindowExtEx.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class ScaleWindowExtEx extends EMFTag
{

  private int xNum, xDenom, yNum, yDenom;

  ScaleWindowExtEx()
  {
    super(32, 1);
  }

  public ScaleWindowExtEx(int xNum, int xDenom, int yNum, int yDenom)
  {
    this();
    this.xNum = xNum;
    this.xDenom = xDenom;
    this.yNum = yNum;
    this.yDenom = yDenom;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    ScaleWindowExtEx tag = new ScaleWindowExtEx(emf.readLONG(), emf.readLONG(), emf.readLONG(), emf.readLONG());
    return tag;
  }

  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    emf.writeLONG(xNum);
    emf.writeLONG(xDenom);
    emf.writeLONG(yNum);
    emf.writeLONG(yDenom);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  xNum: " + xNum + "\n" + "  xDenom: " + xDenom + "\n" + "  yNum: " + yNum + "\n"
        + "  yDenom: " + yDenom;
  }
}
