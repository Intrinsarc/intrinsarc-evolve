// Copyright 2000, CERN, Geneva, Switzerland and University of Santa Cruz, California, U.S.A.
package org.freehep.graphicsio.ps;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.freehep.graphics2d.*;
import org.freehep.util.*;

/**
 * 
 * @author Charles Loomis
 * @version $Id: EPSExportFileType.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class EPSExportFileType extends AbstractPSExportFileType
{

  public String getDescription()
  {
    return "Encapsulated PostScript";
  }

  public String[] getExtensions()
  {
    return new String[]{"eps", "epi", "epsi", "epsf"};
  }

  public JPanel createOptionPanel(Properties user)
  {
    UserProperties options = new UserProperties(user, PSGraphics2D.getDefaultProperties());
    JPanel panel = super.createOptionPanel(options);
    preview.setVisible(true);
    return panel;
  }

  public VectorGraphics getGraphics(OutputStream os, Component target) throws IOException
  {

    return new PSGraphics2D(os, target);
  }
}
