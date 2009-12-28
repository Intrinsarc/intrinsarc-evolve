// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.*;

/**
 * DefineMovie TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineMovie.java,v 1.1 2009-03-04 22:46:52 andrew Exp $
 */
public class DefineMovie extends DefinitionTag
{

  private int character;
  private String name;

  public DefineMovie(int id, String name)
  {
    this();
    character = id;
    this.name = name;
  }

  public DefineMovie()
  {
    super(38, 4);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {

    DefineMovie tag = new DefineMovie();
    tag.character = swf.readUnsignedShort();
    swf.getDictionary().put(tag.character, tag);

    tag.name = swf.readString();
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {
    swf.writeUnsignedShort(character);
    swf.writeString(name);
  }

  public String toString()
  {
    StringBuffer s = new StringBuffer();
    s.append(super.toString() + "\n");
    s.append("  character:  " + character + "\n");
    s.append("  name:       " + name);
    return s.toString();
  }
}
