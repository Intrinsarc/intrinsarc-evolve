// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.emf;

import java.io.*;

import org.freehep.util.io.*;

/**
 * EMF specific tag, from which all other EMF Tags inherit.
 * 
 * @author Mark Donszelmann
 * @version $Id: EMFTag.java,v 1.1 2009-03-04 22:46:50 andrew Exp $
 */
public abstract class EMFTag extends Tag
{

  /**
   * Constructs a EMFTag.
   * 
   * @param id
   *          id of the element
   * @param version
   *          emf version in which this element was first supported
   */
  protected EMFTag(int id, int version)
  {
    super(id, version);
  }

  public Tag read(int tagID, TaggedInputStream input, int len) throws IOException
  {

    return read(tagID, (EMFInputStream) input, len);
  }

  public abstract EMFTag read(int tagID, EMFInputStream emf, int len) throws IOException;

  public void write(int tagID, TaggedOutputStream output) throws IOException
  {
    write(tagID, (EMFOutputStream) output);
  }

  /**
   * Writes the extra tag information to the outputstream in binary format. This
   * implementation writes nothing, but concrete tags may override this method.
   * This method is called just after the TagHeader is written.
   * 
   * @param tagID
   *          id of the tag
   * @param emf
   *          Binary CGM output stream
   */
  public void write(int tagID, EMFOutputStream emf) throws IOException
  {
    // empty
  }

  /**
   * @return a description of the tagName and tagID
   */
  public String toString()
  {
    return "EMFTag " + getName() + " (" + getTag() + ")";
  }
}
