// Copyright 2001, FreeHEP.
package org.freehep.util.io;

import java.io.*;

/**
 * Generic Action, to be used with the TagIn/OutputStreams. An action can have
 * an ActionCode, a length as well as parameters.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: Action.java,v 1.1 2009-03-04 22:46:49 andrew Exp $
 */
public abstract class Action
{

  private int code;
  private String name;

  protected Action(int code)
  {
    this.code = code;

    name = getClass().getName();
    int dot = name.lastIndexOf(".");
    name = (dot >= 0) ? name.substring(dot + 1) : name;
  }

  public abstract Action read(int actionCode, TaggedInputStream input, int length) throws IOException;

  public abstract void write(int actionCode, TaggedOutputStream input) throws IOException;

  public int getCode()
  {
    return code;
  }

  public String getName()
  {
    return name;
  }

  public String toString()
  {
    return "Action " + getName() + " (" + getCode() + ")";
  }

  /**
   * Used for not recognized actions.
   */
  public static class Unknown extends Action
  {
    private int[] data;

    public Unknown()
    {
      super(0x00);
    }

    public Unknown(int actionCode)
    {
      super(actionCode);
    }

    public Action read(int actionCode, TaggedInputStream input, int length) throws IOException
    {

      Unknown action = new Unknown(actionCode);
      action.data = input.readUnsignedByte(length);
      return action;
    }

    public void write(int actionCode, TaggedOutputStream output) throws IOException
    {

      output.writeUnsignedByte(data);
    }

    public String toString()
    {
      return super.toString() + " UNKNOWN!, length " + data.length;
    }
  }

}
