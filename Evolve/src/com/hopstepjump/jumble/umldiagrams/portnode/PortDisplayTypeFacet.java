package com.hopstepjump.jumble.umldiagrams.portnode;

import com.hopstepjump.gem.*;

public interface PortDisplayTypeFacet extends Facet
{
  public static final int NORMAL_TYPE = 0;
  public static final int ELIDED_TYPE = 1;
  public static final int SHORTCUT_TYPE = 2;
  
  public Object setDisplayType(int type);
  public void unSetDisplayType(Object memento);
}
