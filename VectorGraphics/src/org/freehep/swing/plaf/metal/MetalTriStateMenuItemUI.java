// Copyright 2000, CERN, Geneva, Switzerland.
package org.freehep.swing.plaf.metal;

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;



/**
 * 
 * @author Mark Donszelmann
 * @version $Id: MetalTriStateMenuItemUI.java,v 1.1 2006-07-03 13:33:36 amcveigh
 *          Exp $
 */
public class MetalTriStateMenuItemUI extends BasicCheckBoxMenuItemUI
{

  public static ComponentUI createUI(JComponent b)
  {
    return new MetalTriStateMenuItemUI();
  }

  public String getPropertyPrefix()
  {
    return "CheckBoxMenuItem";
  }

  public void installDefaults()
  {
    super.installDefaults();
    checkIcon = new CheckBoxMenuItemIcon();
  }
}
