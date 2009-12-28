// Copyright 2003, FreeHEP.
package org.freehep.graphicsio.ps;


/**
 * 
 * @author Charles Loomis, Simon Fischer
 * @version $Id: PSExportFileType.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class PSExportFileType extends AbstractPSExportFileType
{

  public String getDescription()
  {
    return "PostScript";
  }

  public String[] getExtensions()
  {
    return new String[]{"ps"};
  }

  public boolean isMultipageCapable()
  {
    return true;
  }

}
