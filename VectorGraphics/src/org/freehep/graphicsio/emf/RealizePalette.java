// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * RealizePalette TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: RealizePalette.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class RealizePalette extends EMFTag
{

  public RealizePalette()
  {
    super(52, 1);
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    return this;
  }

}
