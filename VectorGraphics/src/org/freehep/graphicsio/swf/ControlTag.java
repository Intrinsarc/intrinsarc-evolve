// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

/**
 * Abstract Control TAG. All control tags derive from this tag.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: ControlTag.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public abstract class ControlTag extends SWFTag
{

  protected ControlTag(int tagID, int version)
  {
    super(tagID, version);
  }

  public int getTagType()
  {
    return CONTROL;
  }

  public String toString()
  {
    return "Control: " + getName() + " (" + getTag() + ")";
  }

}
