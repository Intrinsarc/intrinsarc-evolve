// Copyright 2000-2003 FreeHEP
package org.freehep.graphicsio.java;

import java.awt.*;
import java.io.*;

import org.freehep.graphics2d.*;
import org.freehep.graphicsio.exportchooser.*;

/**
 * // FIXME, check all options
 * 
 * @author Mark Donszelmann
 * @version $Id: JAVAExportFileType.java,v 1.1 2009-03-04 22:47:00 andrew Exp $
 */
public class JAVAExportFileType extends AbstractExportFileType
{

  public String getDescription()
  {
    return "Java Source File (for Testing)";
  }

  public String[] getExtensions()
  {
    return new String[]{"java"};
  }

  public String[] getMIMETypes()
  {
    return new String[]{"application/java"};
  }

  public boolean hasOptionPanel()
  {
    return false;
  }

  public VectorGraphics getGraphics(File file, Component target) throws IOException
  {

    return new JAVAGraphics2D(file, target);
  }

  public VectorGraphics getGraphics(OutputStream os, Component target) throws IOException
  {

    return new JAVAGraphics2D(os, target);
  }

}
