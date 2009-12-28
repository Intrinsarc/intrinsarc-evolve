// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * CloseFigure TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: CloseFigure.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class CloseFigure extends EMFTag
{

  public CloseFigure()
  {
    super(61, 1);
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    return this;
  }

}
