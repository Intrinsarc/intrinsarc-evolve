// Copyright 2001-2003, FreeHEP.
package org.freehep.util.io;

import java.io.*;
import java.util.zip.*;

/**
 * The FlateOutputStream uses the Deflate mechanism to compress data. The exact
 * definition of Deflate encoding can be found in the PostScript Language
 * Reference (3rd ed.) chapter 3.13.3.
 * 
 * @author Mark Donszelmann
 * @version $Id: FlateOutputStream.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class FlateOutputStream extends DeflaterOutputStream implements FinishableOutputStream
{

  public FlateOutputStream(OutputStream out)
  {
    super(out);
  }

  public void finish() throws IOException
  {
    super.finish();
    if (out instanceof FinishableOutputStream)
    {
      ((FinishableOutputStream) out).finish();
    }
  }
}
