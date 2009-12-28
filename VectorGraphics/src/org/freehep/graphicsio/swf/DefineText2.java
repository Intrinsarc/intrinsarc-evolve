// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.geom.*;
import java.io.*;
import java.util.*;

/**
 * DefineText2 TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineText2.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class DefineText2 extends DefineText
{

  public DefineText2(int id, Rectangle2D bounds, AffineTransform matrix, Vector text)
  {
    this();
    character = id;
    this.bounds = bounds;
    this.matrix = matrix;
    this.text = text;
  }

  public DefineText2()
  {
    super(33, 3);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {
    DefineText2 tag = new DefineText2();
    tag.read(tagID, swf, len, true);
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {

    write(swf, true);
  }
}
