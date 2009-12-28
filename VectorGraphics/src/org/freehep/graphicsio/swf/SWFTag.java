// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.*;

import org.freehep.util.io.*;

/**
 * SWF Tag, superclass of all SWF Tags.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SWFTag.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public abstract class SWFTag extends Tag implements SWFConstants
{

  protected SWFTag(int tagID, int version)
  {
    super(tagID, version);
  }

  public Tag read(int tagID, TaggedInputStream input, int len) throws IOException
  {

    return read(tagID, (SWFInputStream) input, len);
  }

  public abstract SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException;

  public void write(int tagID, TaggedOutputStream output) throws IOException
  {

    write(tagID, (SWFOutputStream) output);
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {

    // empty
  }
}
