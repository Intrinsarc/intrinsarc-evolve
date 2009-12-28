// Copyright 2000, CERN, Geneva, Switzerland and University of Santa Cruz, California, U.S.A.
package org.freehep.graphicsio.ppm;

import javax.imageio.spi.*;

import org.freehep.graphicsio.exportchooser.*;

/**
 * 
 * @author Charles Loomis
 * @version $Id: PPMExportFileType.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public class PPMExportFileType extends ImageExportFileType
{

  static
  {
    try
    {
      Class clazz = Class.forName("org.freehep.graphicsio.ppm.PPMImageWriterSpi");
      IIORegistry.getDefaultInstance().registerServiceProvider((ImageWriterSpi) clazz.newInstance(),
          ImageWriterSpi.class);
    }
    catch (Exception e)
    {
      System.out.println(e);
    }
  }

  public PPMExportFileType()
  {
    super("ppm");
  }

}
