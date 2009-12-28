// Copyright 2001-2003, FreeHEP.
package org.freehep.util.io;

import java.awt.*;
import java.io.*;
import java.util.zip.*;

/**
 * The FlateInputStream uses the Deflate mechanism to compress data. The exact
 * definition of Deflate encoding can be found in the PostScript Language
 * Reference (3rd ed.) chapter 3.13.3.
 * 
 * @author Mark Donszelmann
 * @version $Id: FlateInputStream.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class FlateInputStream extends InflaterInputStream
{

  public FlateInputStream(InputStream in)
  {
    super(in);
  }

  public Image readImage() throws IOException
  {
    // FIXME
    return null;
  }
}
