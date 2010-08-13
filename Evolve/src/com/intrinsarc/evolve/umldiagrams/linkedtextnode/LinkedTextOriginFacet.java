package com.intrinsarc.evolve.umldiagrams.linkedtextnode;

import com.intrinsarc.gem.*;
import com.intrinsarc.geometry.*;

/**
 * @author andrew
 */
public interface LinkedTextOriginFacet extends Facet
{
  UPoint getMajorPoint(int majorPointType);
  String textChanged(String newText, int majorPointType);
}
