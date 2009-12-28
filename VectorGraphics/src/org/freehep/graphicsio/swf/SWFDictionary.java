// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.util.*;

/**
 * SWF Definition Dictionary, which stores definitions being read from a stream.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: SWFDictionary.java,v 1.1 2009-03-04 22:46:52 andrew Exp $
 */
public class SWFDictionary extends Hashtable
{

  public void put(int id, DefinitionTag tag)
  {
    put(new Integer(id), tag);
  }

  public DefinitionTag get(int id)
  {
    return (DefinitionTag) get(new Integer(id));
  }

  public void remove(int id)
  {
    remove(new Integer(id));
  }

  public String toString()
  {
    StringBuffer s = new StringBuffer("SWF Dictionary\n");
    for (Enumeration e = keys(); e.hasMoreElements();)
    {
      Integer key = (Integer) e.nextElement();
      s.append("  [" + key.intValue() + "] ");
      s.append(get(key));
      s.append("\n");
    }
    return s.toString();
  }
}
