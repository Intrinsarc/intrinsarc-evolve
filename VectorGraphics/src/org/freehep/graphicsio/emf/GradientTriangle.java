// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * EMF GradientTriangle
 * 
 * @author Mark Donszelmann
 * @version $Id: GradientTriangle.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class GradientTriangle extends Gradient
{

  private int vertex1, vertex2, vertex3;

  public GradientTriangle(int vertex1, int vertex2, int vertex3)
  {
    this.vertex1 = vertex1;
    this.vertex2 = vertex2;
    this.vertex3 = vertex3;
  }

  GradientTriangle(EMFInputStream emf) throws IOException
  {
    vertex1 = emf.readULONG();
    vertex2 = emf.readULONG();
    vertex3 = emf.readULONG();
  }

  public void write(EMFOutputStream emf) throws IOException
  {
    emf.writeULONG(vertex1);
    emf.writeULONG(vertex2);
    emf.writeULONG(vertex3);
  }

  public String toString()
  {
    return "  GradientTriangle: " + vertex1 + ", " + vertex2 + ", " + vertex3;
  }
}
