// Copyright 2002, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

/**
 * EMF Gradient
 * 
 * @author Mark Donszelmann
 * @version $Id: Gradient.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public abstract class Gradient
{

  public Gradient()
  {
  }

  public abstract void write(EMFOutputStream emf) throws IOException;
}
