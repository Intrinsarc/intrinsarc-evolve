// Copyright 2003, FreeHEP
package org.freehep.graphicsio.exportchooser;

import java.util.*;

/**
 * 
 * @author Mark Donszelmann
 * @version $Id: Options.java,v 1.1 2009-03-04 22:46:55 andrew Exp $
 */
public interface Options
{

  /**
   * Sets all the changed options in the properties object.
   * 
   * @return true if any options were set
   */
  public boolean applyChangedOptions(Properties options);
}
