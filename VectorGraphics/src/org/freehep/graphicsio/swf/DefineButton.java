// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.*;
import java.util.*;

import org.freehep.util.io.*;

/**
 * DefineButton TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineButton.java,v 1.1 2009-03-04 22:46:52 andrew Exp $
 */
public class DefineButton extends DefinitionTag
{

  private int character;
  private Vector buttons;
  private Vector actions;

  public DefineButton(int id, Vector buttons, Vector actions)
  {
    this();
    character = id;
    this.buttons = buttons;
    this.actions = actions;
  }

  public DefineButton()
  {
    super(7, 1);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {

    DefineButton tag = new DefineButton();
    tag.character = swf.readUnsignedShort();
    swf.getDictionary().put(tag.character, tag);

    tag.buttons = new Vector();
    ButtonRecord record = new ButtonRecord(swf, false);
    while (!record.isEndRecord())
    {
      tag.buttons.add(record);
      record = new ButtonRecord(swf, false);
    }

    tag.actions = new Vector();
    Action action = swf.readAction();
    while (action != null)
    {
      tag.actions.add(action);
      action = swf.readAction();
    }
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {

    swf.writeUnsignedShort(character);
    for (int i = 0; i < buttons.size(); i++)
    {
      ButtonRecord b = (ButtonRecord) buttons.get(i);
      b.write(swf);
    }
    swf.writeUnsignedByte(0);

    for (int i = 0; i < actions.size(); i++)
    {
      Action a = (Action) actions.get(i);
      swf.writeAction(a);
    }
    swf.writeAction(null);
  }

  public String toString()
  {
    StringBuffer s = new StringBuffer();
    s.append(super.toString() + "\n");
    s.append("  character:  " + character + "\n");
    for (int i = 0; i < buttons.size(); i++)
    {
      s.append("  ");
      s.append(buttons.get(i));
      s.append("\n");
    }
    for (int i = 0; i < actions.size(); i++)
    {
      s.append("  ");
      s.append(actions.get(i));
      s.append("\n");
    }
    return s.toString();
  }
}
