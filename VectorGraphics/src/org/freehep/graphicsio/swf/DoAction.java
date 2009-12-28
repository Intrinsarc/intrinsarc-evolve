// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.io.*;
import java.util.*;

import org.freehep.util.io.*;

/**
 * DoAction TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DoAction.java,v 1.1 2009-03-04 22:46:52 andrew Exp $
 */
public class DoAction extends ControlTag
{

  private Vector actions;

  public DoAction(Vector actions)
  {
    this();
    this.actions = actions;
  }

  public DoAction()
  {
    super(12, 1);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {

    DoAction tag = new DoAction();
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
    for (int i = 0; i < actions.size(); i++)
    {
      s.append("  " + actions.get(i) + "\n");
    }
    return s.toString();
  }
}
