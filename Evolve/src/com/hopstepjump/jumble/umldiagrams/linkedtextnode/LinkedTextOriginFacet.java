package com.hopstepjump.jumble.umldiagrams.linkedtextnode;

import com.hopstepjump.gem.*;
import com.hopstepjump.geometry.*;

/**
 * @author andrew
 */
public interface LinkedTextOriginFacet extends Facet
{
  UPoint getMajorPoint(int majorPointType);
  String textChanged(String newText, int majorPointType);
}
