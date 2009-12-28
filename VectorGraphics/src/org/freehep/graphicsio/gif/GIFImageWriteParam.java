// Copyright 2003, FreeHEP
package org.freehep.graphicsio.gif;

import java.util.*;

import javax.imageio.*;

import org.freehep.graphicsio.*;
import org.freehep.util.*;

/**
 * 
 * @version $Id: GIFImageWriteParam.java,v 1.1 2009-03-04 22:46:57 andrew Exp $
 */
public class GIFImageWriteParam extends ImageWriteParam implements ImageParamConverter
{

  private boolean quantizeColors;
  private String quantizeMode;

  public GIFImageWriteParam(Locale locale)
  {
    super(locale);
    canWriteProgressive = true;
    progressiveMode = MODE_DEFAULT;

    UserProperties def = new UserProperties(GIFGraphics2D.getDefaultProperties());
    quantizeColors = def.isProperty(GIFGraphics2D.QUANTIZE_COLORS);
    quantizeMode = def.getProperty(GIFGraphics2D.QUANTIZE_MODE);
  }

  public ImageWriteParam getWriteParam(Properties properties)
  {
    UserProperties p = new UserProperties(properties);
    setQuantizeColors(p.isProperty(GIFGraphics2D.QUANTIZE_COLORS));
    setQuantizeMode(p.getProperty(GIFGraphics2D.QUANTIZE_COLORS));
    return this;
  }

  public boolean getQuantizeColors()
  {
    return quantizeColors;
  }

  public void setQuantizeColors(boolean state)
  {
    quantizeColors = state;
  }

  public String[] getQuantizeModes()
  {
    return GIFExportFileType.quantizeModes;
  }

  public String getQuantizeMode()
  {
    return quantizeMode;
  }

  public void setQuantizeMode(String mode)
  {
    quantizeMode = mode;
  }
}
