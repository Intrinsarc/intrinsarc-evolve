// Charles A. Loomis, Jr., and University of California, Santa Cruz,
// Copyright (c) 2000
package org.freehep.swing.graphics;

import java.awt.*;
import java.awt.geom.*;

/**
 * Instances of this class are created when the user graphically selects a
 * region on the screen. This event gives the object which generated this event,
 * as well as a polygon describing the outline of the region and an
 * AffineTransform which can be used to zoom into the selected region.
 * 
 * @author Charles Loomis
 * @version $Id: RegionSelectionEvent.java,v 1.1 2006-07-03 13:33:26 amcveigh
 *          Exp $
 */
public class RegionSelectionEvent extends GraphicalSelectionEvent
{

  /**
   * The constructor requires the source object, the selection type, and the
   * actual selection.
   * 
   * @param source
   *          the Object which generates this event
   * @param selection
   *          a Polygon describing the outline of the region
   * @param transform
   *          an AffineTransform appropriate for zooming into the selected
   *          region
   */
  public RegionSelectionEvent(Object source, int actionCode, Polygon selection, AffineTransform transform)
  {
    super(source, actionCode, selection, transform);
  }
}
