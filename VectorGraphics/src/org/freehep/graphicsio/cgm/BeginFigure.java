// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * BeginFigure TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: BeginFigure.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class BeginFigure extends CGMTag
{

  public BeginFigure()
  {
    super(0, 8, 2);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("BEGFIGURE");
    cgm.indent();
  }
}
