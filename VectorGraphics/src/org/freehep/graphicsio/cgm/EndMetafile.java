// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * EndMetafile TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndMetafile.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class EndMetafile extends CGMTag
{

  public EndMetafile()
  {
    super(0, 2, 1);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.outdent();
    cgm.print("ENDMF");
  }
}