// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.*;

/**
 * RemoveObject TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: RemoveObject.java,v 1.1 2009-03-04 22:46:52 andrew Exp $
 */
public class RemoveObject extends ControlTag
{

  private int depth;
  private int character;

  public RemoveObject(int depth, int id)
  {
    this();
    this.depth = depth;
    character = id;
  }

  public RemoveObject()
  {
    super(5, 1);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {
    RemoveObject tag = new RemoveObject();
    tag.character = swf.readUnsignedShort();
    tag.depth = swf.readUnsignedShort();
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {
    swf.writeUnsignedShort(character);
    swf.writeUnsignedShort(depth);
  }

  public String toString()
  {
    return super.toString() + "\n" + "  depth: " + depth + "\n" + "  character: " + character;
  }

}
