// Copyright 2001, FreeHEP.
package org.freehep.util.io;

/**
 * Keeps the tagID and Length of a specific tag. To be used in the InputStream
 * to return the tagID and Length, and in the OutputStream to write them.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: TagHeader.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public class TagHeader
{

  int tagID;
  long length;

  public TagHeader(int tagID, long length)
  {
    this.tagID = tagID;
    this.length = length;
  }

  public void setTag(int tagID)
  {
    this.tagID = tagID;
  }

  public int getTag()
  {
    return tagID;
  }

  public void setLength(long length)
  {
    this.length = length;
  }

  public long getLength()
  {
    return length;
  }
}
