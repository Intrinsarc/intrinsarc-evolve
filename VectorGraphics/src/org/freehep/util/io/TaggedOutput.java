// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * @author Mark Donszelmann
 * @version $Id: TaggedOutput.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public interface TaggedOutput
{

  /**
   * Write a tag.
   */
  public void writeTag(Tag tag) throws IOException;

  public void close() throws IOException;
}
