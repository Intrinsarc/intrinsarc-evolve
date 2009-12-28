// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * ScaleViewportExtEx TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: ScaleViewportExtEx.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class ScaleViewportExtEx extends EMFTag
{

  private int xNum, xDenom, yNum, yDenom;

  ScaleViewportExtEx()
  {
    super(31, 1);
  }

  public ScaleViewportExtEx(int xNum, int xDenom, int yNum, int yDenom)
  {
    this();
    this.xNum = xNum;
    this.xDenom = xDenom;
    this.yNum = yNum;
    this.yDenom = yDenom;
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    int[] bytes = emf.readUnsignedByte(len);
    ScaleViewportExtEx tag = new ScaleViewportExtEx(emf.readLONG(), emf.readLONG(), emf.readLONG(), emf.readLONG());
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
