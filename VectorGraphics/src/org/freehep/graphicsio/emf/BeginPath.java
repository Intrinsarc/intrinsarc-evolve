// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * BeginPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: BeginPath.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class BeginPath extends EMFTag
{

  public BeginPath()
  {
    super(59, 1);
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    return this;
  }
}
