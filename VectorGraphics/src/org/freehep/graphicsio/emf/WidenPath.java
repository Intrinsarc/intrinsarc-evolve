// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * WidenPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: WidenPath.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public class WidenPath extends EMFTag
{

  public WidenPath()
  {
    super(66, 1);
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    return this;
  }
}
