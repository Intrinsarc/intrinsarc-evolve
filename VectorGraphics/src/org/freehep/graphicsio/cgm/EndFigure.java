// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * EndFigure TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndFigure.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class EndFigure extends CGMTag
{

  public EndFigure()
  {
    super(0, 9, 2);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.outdent();
    cgm.print("ENDFIGURE");
  }
}
