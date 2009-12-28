// Copyright 2001, FreeHEP.
package org.freehep.graphicsio.swf;

import java.awt.geom.*;
import java.io.*;

/**
 * DefineShape3 TAG.
 * 
 * @author Mark Donszelmann
 * @author Charles Loomis
 * @version $Id: DefineShape3.java,v 1.1 2009-03-04 22:46:51 andrew Exp $
 */
public class DefineShape3 extends DefineShape
{

  public DefineShape3(int id, Rectangle2D bounds, FillStyleArray fillStyles, LineStyleArray lineStyles, SWFShape shape)
  {
    this();
    character = id;
    this.bounds = bounds;
    this.fillStyles = fillStyles;
    this.lineStyles = lineStyles;
    this.shape = shape;
  }

  public DefineShape3()
  {
    super(32, 3);
  }

  public SWFTag read(int tagID, SWFInputStream swf, int len) throws IOException
  {
    DefineShape3 tag = new DefineShape3();
    tag.read(tagID, swf, true);
    return tag;
  }

  public void write(int tagID, SWFOutputStream swf) throws IOException
  {
    write(swf, true);
  }
}
