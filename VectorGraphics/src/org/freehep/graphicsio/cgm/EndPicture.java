// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * EndPicture TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: EndPicture.java,v 1.1 2009-03-04 22:46:48 andrew Exp $
 */
public class EndPicture extends CGMTag
{

  public EndPicture()
  {
    super(0, 5, 1);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.outdent();
    cgm.print("ENDPIC");
  }
}
