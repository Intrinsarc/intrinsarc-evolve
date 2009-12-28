// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * FlattenPath TAG.
 * 
 * @author Mark Donszelmann
 * @version $Id: FlattenPath.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class FlattenPath extends EMFTag
{

  public FlattenPath()
  {
    super(65, 1);
  }

  public EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException
  {

    return this;
  }

}
