// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.swing.plaf.metal;

import java.awt.*;

import javax.swing.plaf.metal.*;

import org.freehep.swing.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: MetalTriStateBoxIcon.java,v 1.1 2006-07-03 13:33:36 amcveigh
 *          Exp $
 */
public class MetalTriStateBoxIcon extends MetalCheckBoxIcon
{

  protected void drawCheck(Component c, Graphics g, int x, int y)
  {
    int controlSize = getControlSize();
    JTriStateBox b = (JTriStateBox) c;
    switch (b.getTriState())
    {
      case -1 : // half
        Color color = g.getColor();
        g.setColor(MetalLookAndFeel.getControlShadow());
        g.fillRect(x + 2, y + 2, controlSize - 3, controlSize - 3);
        g.setColor(color);
        super.drawCheck(c, g, x, y);
        break;
      case 0 : // false
        break;
      case 1 : // true
        super.drawCheck(c, g, x, y);
        break;
    }
  }
}
