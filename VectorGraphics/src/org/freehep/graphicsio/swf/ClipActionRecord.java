// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.*;
import java.util.*;

/**
 * SWF Clip Action Record
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ClipActionRecord.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class ClipActionRecord
{

  private ClipEventFlags eventFlags;
  private long actionRecordSize;
  private int keyCode;
  private Vector actions;

  /**
   * Read a ClipActionRecord from the stream.
   */
  public ClipActionRecord(SWFInputStream swf) throws IOException
  {

    eventFlags = new ClipEventFlags(swf);
    if (eventFlags.isEndFlag())
      return;

    actionRecordSize = swf.readUnsignedInt();
    // FIXME is actually a long (unsigned int)
    swf.pushBuffer((int) (actionRecordSize - 4));
    if (eventFlags.isKeyPress())
      keyCode = swf.readUnsignedByte();

    while (swf.getLength() > 0)
    {
      actions.add(swf.readAction());
    }
    byte[] rest = swf.popBuffer();
    if (rest != null)
    {
      System.err.println("Corrupted ClipActionRecord, " + rest.length + " bytes leftoever.");
    }
  }

  public void write(SWFOutputStream swf) throws IOException
  {
    eventFlags.write(swf);
    // FIXME, should do PushBuffer and calculate this size...
    swf.writeUnsignedInt(actionRecordSize);
    if (eventFlags.isKeyPress())
      swf.writeUnsignedByte(keyCode);

    for (int i = 0; i < actions.size(); i++)
    {
      SWFAction a = (SWFAction) actions.get(i);
      swf.writeAction(a);
    }
  }

  public boolean isEndRecord()
  {
    return eventFlags.isEndFlag();
  }

  public String toString()
  {
    return "ClipActionRecord " + actions.size();
  }
}
