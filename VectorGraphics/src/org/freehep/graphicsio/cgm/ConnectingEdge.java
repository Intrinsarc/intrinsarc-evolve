// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.cgm;

import java.io.*;

/**
 * ConnectingEdge TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ConnectingEdge.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class ConnectingEdge extends CGMTag
{

  public ConnectingEdge()
  {
    super(5, 21, 1);
  }

  public void write(int tagID, CGMWriter cgm) throws IOException
  {
    cgm.print("CONNEDGE");
  }
}
